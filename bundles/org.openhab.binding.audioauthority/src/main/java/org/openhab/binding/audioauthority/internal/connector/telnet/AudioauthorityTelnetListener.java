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
package org.openhab.binding.audioauthority.internal.connector.telnet;

import org.openhab.binding.audioauthority.internal.connector.AudioauthorityConnector;

/**
 * Listener interface used to notify the {@link AudioauthorityConnector}
 * about received messages over Telnet
 *
 *
 * @author Dennis Drapeau - Initial contribution
 */
public interface AudioauthorityTelnetListener {
    /**
     * The telnet client has received a line.
     *
     * @param line the received line
     */
    void receivedLine(String line);

    /**
     * The telnet client has successfully connected to the receiver.
     *
     * @param connected whether or not the connection was successful
     */
    void telnetClientConnected(boolean connected);
}
