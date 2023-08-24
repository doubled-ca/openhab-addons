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
package org.openhab.binding.audioauthority.internal.connector;

import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.audioauthority.internal.AudioauthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioauthorityState;
import org.openhab.binding.audioauthority.internal.connector.telnet.AudioauthorityTelnetConnector;

/**
 *
 * @author Dennis Drapeau - Initial contribution
 *
 */
@NonNullByDefault
public class AudioauthorityConnectorFactory {

    public AudioauthorityConnector getConnector(AudioauthorityConfiguration config, AudioauthorityState state,
            ScheduledExecutorService scheduler, String thingUID) {
        return new AudioauthorityTelnetConnector(config, state, scheduler, thingUID);
    }
}
