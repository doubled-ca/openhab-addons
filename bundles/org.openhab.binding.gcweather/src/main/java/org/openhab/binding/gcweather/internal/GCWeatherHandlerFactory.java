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

import static org.openhab.binding.gcweather.internal.GCWeatherBindingConstants.THING_TYPE_WEATHER;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.gcweather.internal.handler.GCWeatherHandler;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.i18n.UnitProvider;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link GCWeatherHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Dennis Drapeau - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.gcweather", service = ThingHandlerFactory.class)
public class GCWeatherHandlerFactory extends BaseThingHandlerFactory {

    private TimeZoneProvider timeZoneProvider;
    private UnitProvider unitProvider;
    private HttpClient httpClient;
    // private LocationProvider locationProvider;
    // private LocaleProvider localeProvider;
    // private TranslationProvider i18nProvider;

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_WEATHER);

    @Activate
    public GCWeatherHandlerFactory(@Reference HttpClientFactory httpClientFactory,
            @Reference TimeZoneProvider timeZoneProvider, @Reference UnitProvider unitProvider) {
        // ,
        // @Reference LocationProvider locationProvider, @Reference LocaleProvider localeProvider,
        // @Reference TranslationProvider i18nProvider) {
        this.timeZoneProvider = timeZoneProvider;
        this.unitProvider = unitProvider;
        this.httpClient = httpClientFactory.getCommonHttpClient();
        // this.locationProvider = locationProvider;
        // this.localeProvider = localeProvider;
        // this.i18nProvider = i18nProvider;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_WEATHER.equals(thingTypeUID)) {
            return new GCWeatherHandler(thing, httpClient, timeZoneProvider, unitProvider);
        }

        return null;
    }
}
