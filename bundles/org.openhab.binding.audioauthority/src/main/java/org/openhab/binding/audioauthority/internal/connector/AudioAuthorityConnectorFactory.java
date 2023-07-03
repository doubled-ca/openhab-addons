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
 *
 *
 *
 */
package org.openhab.binding.audioauthority.internal.connector;

import java.util.concurrent.ScheduledExecutorService;

import org.openhab.binding.audioauthority.internal.AudioAuthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioAuthorityState;
import org.openhab.binding.audioauthority.internal.connector.telnet.AudioAuthorityTelnetConnector;

/**
 *
 * @author Dennis Drapeau - Initial contribution
 *
 */
public class AudioAuthorityConnectorFactory {

    public AudioAuthorityConnector getConnector(AudioAuthorityConfiguration config, AudioAuthorityState state,
            ScheduledExecutorService scheduler, String thingUID) {
        return new AudioAuthorityTelnetConnector(config, state, scheduler, thingUID);
    }
}
