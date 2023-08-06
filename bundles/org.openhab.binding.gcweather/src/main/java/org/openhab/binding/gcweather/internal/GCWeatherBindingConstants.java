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
package org.openhab.binding.gcweather.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link GCWeatherBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Dennis Drapeau - Initial contribution
 */
@NonNullByDefault
public class GCWeatherBindingConstants {

    private static final String BINDING_ID = "gcweather";

    public static final String GC_WEATHER_URL_ROOT = "https://app.weather.gc.ca/v2/en/Location/";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_WEATHER = new ThingTypeUID(BINDING_ID, "weather");

    public static final Integer GC_WEATHER_REQUEST_TIMEOUT = 10;
    // List of Observations
    public static final String CH_GC_OBSERVED_AT = "observedAt";
    public static final String CH_GC_PROVINCE_CODE = "provinceCode";
    public static final String CH_GC_CLIMATE_ID = "climateId";
    public static final String CH_GC_TC_ID = "tcid";
    public static final String CH_GC_TIMESTAMP = "timeStamp";
    public static final String CH_GC_TIMESTAMP_TEXT = "timeStampText";

    public static final String CH_GC_OBS_ICON_CODE = "iconCode";
    public static final String CH_GC_OBS_CONDITION = "condition";

    public static final String CH_GC_OBS_TEMP_IMPERIAL = "temperature.imperial";
    public static final String CH_GC_OBS_TEMP_IMPERIAL_UNROUNDED = "temperature.imperialUnrounded";
    public static final String CH_GC_OBS_TEMP_METRIC = "temperature.metric";
    public static final String CH_GC_OBS_TEMP_METRIC_UNROUNDED = "temperature.metricUnrounded";

    public static final String CH_GC_OBS_DEWPOINT_IMPERIAL = "dewpoint.imperial";
    public static final String CH_GC_OBS_DEWPOINT_IMPERIAL_UNROUNDED = "dewpoint.imperialUnrounded";
    public static final String CH_GC_OBS_DEWPOINT_METRIC = "dewpoint.metric";
    public static final String CH_GC_OBS_DEWPOINT_METRIC_UNROUNDED = "dewpoint.metricUnrounded";

    public static final String CH_GC_OBS_FEELSLIKE_IMPERIAL = "feelslike.imperial";
    public static final String CH_GC_OBS_FEELSLIKE_METRIC = "feelslike.metric";

    public static final String CH_GC_OBS_PRESSURE_IMPERIAL = "pressure.imperial";
    public static final String CH_GC_OBS_PRESSURE_METRIC = "pressure.metric";
    public static final String CH_GC_OBS_PRESSURE_CHANGE_IMPERIAL = "pressure.changeImperial";
    public static final String CH_GC_OBS_PRESSURE_CHANGE_METRIC = "pressure.changeMetric";
    public static final String CH_GC_OBS_PRESSURE_TENDENCY = "tendency";

    public static final String CH_GC_OBS_VISIBILITY_IMPERIAL = "visiblity.imperial";
    public static final String CH_GC_OBS_VISIBILITY_METRIC = "visiblity.metric";
    public static final String CH_GC_OBS_VISIBILITY_METRIC_UNROUNDED = "visUnround";

    public static final String CH_GC_OBS_HUMIDITY = "humidity";

    public static final String CH_GC_OBS_WINDSPEED_IMPERIAL = "windSpeed.imperial";
    public static final String CH_GC_OBS_WINDSPEED_METRIC = "windSpeed.metric";
    public static final String CH_GC_OBS_WINDGUST_IMPERIAL = "windGust.imperial";
    public static final String CH_GC_OBS_WINDGUST_METRIC = "windGust.metric";
    public static final String CH_GC_OBS_WIND_DIRECTION = "windDirection";
    public static final String CH_GC_OBS_WIND_BEARING = "windBearing";
}
