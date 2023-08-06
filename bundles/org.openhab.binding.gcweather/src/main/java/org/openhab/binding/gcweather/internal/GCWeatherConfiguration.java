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
package org.openhab.binding.gcweather.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link GCWeatherConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Dennis Drapeau - Initial contribution
 */
@NonNullByDefault
public class GCWeatherConfiguration {

    /**
     * Sample configuration parameters. Replace with your own.
     */
    // public String hostname = "";
    // public String password = "";

    public int refreshInterval = 30;
    public String cgndbLocation = "";
}
