/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
package org.openhab.binding.gcweather.internal.model;

/**
 * The {@link GCWeatherDTO} contains the most recent weather condition
 * observations from the Personl Weather Station (PWS).
 *
 * @author Mark Hilbush - Initial contribution
 */
public class GCWeatherDTO {
    /*
     * An array of length 1 of observations that represent the
     * most recent PWS observations
     */
    public GCWeather[] gcWeather;

    public class GCWeather {

        public String cgndb;

        public String displayName;

        public String zonePoly;

        public long lastUpdated;

        public Observation observation;
    }

    public class Observation {
        public String observedAt;
        public String provinceCode;
        public int climateId;
        public String tcid;
        public String timeStamp;
        public String timeStampText;
        public int iconCode;
        public String condition;
        public Temp temperature;
        public Temp dewpoint;
        public Measure feelsLike;
        public Pressure pressure;
        public String tendency;
        public Measure visibility;
        public int humidity;
        public Measure windSpeed;
        public Measure windGust;
        public String windDirection;
        public Double windBearing;
    }

    public class Pressure {

        public Double imperial;
        public Double metric;
        public Double changeImperial;
        public Double changeMetric;
    }

    public class Measure {
        public int imperial;
        public int metric;
    }

    public class Temp {
        public int imperial;
        public Double imperialUnrounded;
        public int metric;
        public Double metricUnrounded;
    }
}
