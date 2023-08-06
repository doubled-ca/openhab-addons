/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.gcweather.internal.handler;

import static org.openhab.core.library.unit.MetricPrefix.MILLI;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.openhab.binding.gcweather.internal.GCWeatherBindingConstants;
import org.openhab.binding.gcweather.internal.GCWeatherConfiguration;
import org.openhab.binding.gcweather.internal.model.GCWeatherDTO;
import org.openhab.binding.gcweather.internal.model.GCWeatherDTO.GCWeather;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.i18n.UnitProvider;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.PointType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.library.unit.ImperialUnits;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * The {@link GCWeatherHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Dennis Drapeau - Initial contribution
 */
@NonNullByDefault
public class GCWeatherHandler extends BaseThingHandler {

    private static final int REFRESH_JOB_INITIAL_DELAY_SECONDS = 5;

    private final Logger logger = LoggerFactory.getLogger(GCWeatherHandler.class);

    // private @Nullable GCWeatherConfiguration config;

    private int refreshIntervalSeconds;

    private @Nullable Future<?> refreshObservationsJob;

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshGCWeatherObservations();
        }
    };

    protected final Gson gson = new GsonBuilder().serializeNulls().create();

    protected final Map<String, State> weatherDataCache = Collections.synchronizedMap(new HashMap<>());

    // Provided by handler factory
    private final TimeZoneProvider timeZoneProvider;
    private final HttpClient httpClient;
    private final SystemOfUnits systemOfUnits;

    public GCWeatherHandler(Thing thing, HttpClient httpClient, TimeZoneProvider timeZoneProvider,
            UnitProvider unitProvider) {
        super(thing);
        this.timeZoneProvider = timeZoneProvider;
        this.httpClient = httpClient;
        this.systemOfUnits = unitProvider.getMeasurementSystem();
    }

    @Override
    public void initialize() {

        logger.debug("Initializing observations handler with configuration: {}",
                getConfigAs(GCWeatherConfiguration.class).toString());

        refreshIntervalSeconds = getConfigAs(GCWeatherConfiguration.class).refreshInterval * 60;
        weatherDataCache.clear();
        scheduleRefreshJob();
    }

    /*
     * Determine the units configured in the system
     */
    protected boolean isImperial() {
        return systemOfUnits instanceof ImperialUnits ? true : false;
    }

    /*
     * Set the state to the passed value. If value is null, set the state to UNDEF
     */
    protected State undefOrString(@Nullable String value) {
        return value == null ? UnDefType.UNDEF : new StringType(value);
    }

    protected State undefOrDate(@Nullable Integer value) {
        return value == null ? UnDefType.UNDEF : getLocalDateTimeType(value);
    }

    protected State undefOrDate(@Nullable String value) {
        return value == null ? UnDefType.UNDEF : getLocalDateTimeType(value);
    }

    protected State undefOrDecimal(@Nullable Number value) {
        return value == null ? UnDefType.UNDEF : new DecimalType(value.doubleValue());
    }

    protected State undefOrQuantity(@Nullable Number value, Unit<?> unit) {
        return value == null ? UnDefType.UNDEF : new QuantityType<>(value, unit);
    }

    protected State undefOrPoint(@Nullable Number lat, @Nullable Number lon) {
        return lat != null && lon != null
                ? new PointType(new DecimalType(lat.doubleValue()), new DecimalType(lon.doubleValue()))
                : UnDefType.UNDEF;
    }

    /*
     * The API will request units based on openHAB's SystemOfUnits setting. Therefore,
     * when setting the QuantityType state, make sure we use the proper unit.
     */
    protected Unit<?> getTempUnit() {
        return isImperial() ? ImperialUnits.FAHRENHEIT : SIUnits.CELSIUS;
    }

    protected Unit<?> getSpeedUnit() {
        return isImperial() ? ImperialUnits.MILES_PER_HOUR : Units.METRE_PER_SECOND;
    }

    protected Unit<?> getLengthUnit() {
        return isImperial() ? ImperialUnits.INCH : MILLI(SIUnits.METRE);
    }

    /*
     * Execute the The Weather Channel API request
     */
    protected @Nullable String executeApiRequest(@Nullable String url) {
        if (url == null) {
            logger.debug("Handler: Can't execute request because url is null");
            return null;
        }
        Request request = httpClient.newRequest(url);
        request.timeout(10, TimeUnit.SECONDS);
        request.method(HttpMethod.GET);
        request.header(HttpHeader.ACCEPT, "application/json");
        request.header(HttpHeader.ACCEPT_ENCODING, "gzip");

        String errorMsg;
        try {
            ContentResponse contentResponse = request.send();
            switch (contentResponse.getStatus()) {
                case HttpStatus.OK_200:
                    String response = contentResponse.getContentAsString();
                    String cacheControl = contentResponse.getHeaders().get(HttpHeader.CACHE_CONTROL);
                    logger.debug("Cache-Control header is {}", cacheControl);
                    return response;
                case HttpStatus.NO_CONTENT_204:
                    errorMsg = "HTTP response 400: No content. Check configuration";
                    break;
                case HttpStatus.BAD_REQUEST_400:
                    errorMsg = "HTTP response 400: Bad request";
                    break;
                case HttpStatus.UNAUTHORIZED_401:
                    errorMsg = "HTTP response 401: Unauthorized";
                    break;
                case HttpStatus.FORBIDDEN_403:
                    errorMsg = "HTTP response 403: Invalid API key";
                    break;
                case HttpStatus.NOT_FOUND_404:
                    errorMsg = "HTTP response 404: Endpoint not found";
                    break;
                case HttpStatus.METHOD_NOT_ALLOWED_405:
                    errorMsg = "HTTP response 405: Method not allowed";
                    break;
                case HttpStatus.NOT_ACCEPTABLE_406:
                    errorMsg = "HTTP response 406: Not acceptable";
                    break;
                case HttpStatus.REQUEST_TIMEOUT_408:
                    errorMsg = "HTTP response 408: Request timeout";
                    break;
                case HttpStatus.INTERNAL_SERVER_ERROR_500:
                    errorMsg = "HTTP response 500: Internal server error";
                    break;
                case HttpStatus.BAD_GATEWAY_502:
                case HttpStatus.SERVICE_UNAVAILABLE_503:
                case HttpStatus.GATEWAY_TIMEOUT_504:
                    errorMsg = String.format("HTTP response %d: Service unavailable or gateway issue",
                            contentResponse.getStatus());
                    break;
                default:
                    errorMsg = String.format("HTTP GET failed: %d, %s", contentResponse.getStatus(),
                            contentResponse.getReason());
                    break;
            }
        } catch (TimeoutException e) {
            errorMsg = "@text/offline.comm-error-timeout";
        } catch (ExecutionException e) {
            errorMsg = String.format("ExecutionException: %s", e.getMessage());
        } catch (InterruptedException e) {
            errorMsg = String.format("InterruptedException: %s", e.getMessage());
        }
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, errorMsg);
        return null;
    }

    /*
     * Convert UTC Unix epoch seconds to local time
     */
    protected DateTimeType getLocalDateTimeType(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        ZonedDateTime localDateTime = instant.atZone(getZoneId());
        DateTimeType dateTimeType = new DateTimeType(localDateTime);
        return dateTimeType;
    }

    /*
     * Convert UTC time string to local time
     * Input string is of form 2018-12-02T10:47:00.000Z
     */
    protected State getLocalDateTimeType(String dateTimeString) {
        State dateTimeType;
        try {
            Instant instant = Instant.parse(dateTimeString);
            ZonedDateTime localDateTime = instant.atZone(getZoneId());
            dateTimeType = new DateTimeType(localDateTime);
        } catch (DateTimeParseException e) {
            logger.debug("Error parsing date/time string: {}", e.getMessage());
            dateTimeType = UnDefType.UNDEF;
        }
        return dateTimeType;
    }

    private ZoneId getZoneId() {
        return timeZoneProvider.getTimeZone();
    }

    /*
     * Build the URL for requesting the PWS current observations
     */
    private @Nullable String buildGCWeatherUrl() {

        GCWeatherConfiguration config = getConfigAs(GCWeatherConfiguration.class);

        String location = config.cgndbLocation;
        if (location == null || location.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder(GCWeatherBindingConstants.GC_WEATHER_URL_ROOT);
        sb.append(location);

        String url = sb.toString();
        logger.debug("GC Weather observations URL is {}", url);
        return url;
    }

    private synchronized void refreshGCWeatherObservations() {

        logger.debug("Handler: Requesting GC Weather observations from The Weather Company API");
        String response = executeApiRequest(buildGCWeatherUrl());
        if (response == null) {
            return;
        }
        try {
            logger.debug("Handler: Parsing GC Weather observations response: {}", response);
            GCWeatherDTO gc_weatherObservations = Objects.requireNonNull(gson.fromJson(response, GCWeatherDTO.class));
            logger.debug("Handler: Successfully parsed GC Weather observations response object");
            updateStatus(ThingStatus.ONLINE);
            updateGCWeatherObservations(gc_weatherObservations);
        } catch (JsonSyntaxException e) {
            logger.debug("Handler: Error parsing GC Weather observations response object: {}", e.getMessage(), e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "@text/offline.comm-error-parsing-pws-forecast");
            return;
        }
    }

    private void updateGCWeatherObservations(GCWeatherDTO gc_weatherObservations) {
        if (gc_weatherObservations.gc_weather.length == 0) {
            logger.debug("Handler: GC Weather observation object contains no observations!");
            return;
        }
        GCWeather obs = gc_weatherObservations.gc_weather[0];
        logger.debug("Handler: Processing observations from station {} at {}", obs.observation.observedAt,
                obs.observation.timeStampText);
        // updateChannel(CH_PWS_TEMP, undefOrQuantity(obs.imperial.temp, ImperialUnits.FAHRENHEIT));
        // updateChannel(CH_PWS_TEMP_HEAT_INDEX, undefOrQuantity(obs.imperial.heatIndex, ImperialUnits.FAHRENHEIT));
        // updateChannel(CH_PWS_TEMP_WIND_CHILL, undefOrQuantity(obs.imperial.windChill, ImperialUnits.FAHRENHEIT));
        // updateChannel(CH_PWS_TEMP_DEW_POINT, undefOrQuantity(obs.imperial.dewpt, ImperialUnits.FAHRENHEIT));
        // updateChannel(CH_PWS_HUMIDITY, undefOrQuantity(obs.humidity, Units.PERCENT));
        // updateChannel(CH_PWS_PRESSURE, undefOrQuantity(obs.imperial.pressure, ImperialUnits.INCH_OF_MERCURY));
        // updateChannel(CH_PWS_PRECIPTATION_RATE, undefOrQuantity(obs.imperial.precipRate, Units.INCHES_PER_HOUR));
        // updateChannel(CH_PWS_PRECIPITATION_TOTAL, undefOrQuantity(obs.imperial.precipTotal, ImperialUnits.INCH));
        // updateChannel(CH_PWS_WIND_SPEED, undefOrQuantity(obs.imperial.windSpeed, ImperialUnits.MILES_PER_HOUR));
        // updateChannel(CH_PWS_WIND_GUST, undefOrQuantity(obs.imperial.windGust, ImperialUnits.MILES_PER_HOUR));
        // updateChannel(CH_PWS_WIND_DIRECTION, undefOrQuantity(obs.winddir, Units.DEGREE_ANGLE));
        // updateChannel(CH_PWS_SOLAR_RADIATION, undefOrQuantity(obs.solarRadiation, Units.IRRADIANCE));
        // updateChannel(CH_PWS_UV, undefOrDecimal(obs.uv));
        // updateChannel(CH_PWS_OBSERVATION_TIME_LOCAL, undefOrDate(obs.obsTimeUtc));
        // updateChannel(CH_PWS_NEIGHBORHOOD, undefOrString(obs.neighborhood));
        // updateChannel(CH_PWS_STATION_ID, undefOrString(obs.stationID));
        // updateChannel(CH_PWS_COUNTRY, undefOrString(obs.country));
        // updateChannel(CH_PWS_LOCATION, undefOrPoint(obs.lat, obs.lon));
        // updateChannel(CH_PWS_ELEVATION, undefOrQuantity(obs.imperial.elev, ImperialUnits.FOOT));
        // updateChannel(CH_PWS_QC_STATUS, undefOrDecimal(obs.qcStatus));
        // updateChannel(CH_PWS_SOFTWARE_TYPE, undefOrString(obs.softwareType));
    }

    protected void updateChannel(String channelId, State state) {
        // Only update channel if it's linked
        if (isLinked(channelId)) {
            updateState(channelId, state);
            weatherDataCache.put(channelId, state);
        }
    }

    /*
     * The refresh job updates the PWS current observations
     * on the refresh interval set in the thing config
     */
    private void scheduleRefreshJob() {
        logger.debug("Handler: Scheduling observations refresh job in {} seconds", REFRESH_JOB_INITIAL_DELAY_SECONDS);
        cancelRefreshJob();
        refreshObservationsJob = scheduler.scheduleWithFixedDelay(refreshRunnable, REFRESH_JOB_INITIAL_DELAY_SECONDS,
                refreshIntervalSeconds, TimeUnit.SECONDS);
    }

    private void cancelRefreshJob() {
        if (refreshObservationsJob != null) {
            refreshObservationsJob.cancel(true);
            logger.debug("Handler: Canceling observations refresh job");
        }
    }

    @Override
    public void dispose() {
        cancelRefreshJob();
        updateStatus(ThingStatus.OFFLINE);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command.equals(RefreshType.REFRESH)) {
            State state = weatherDataCache.get(channelUID.getId());
            if (state != null) {
                updateChannel(channelUID.getId(), state);
            }
        }
    }
}
