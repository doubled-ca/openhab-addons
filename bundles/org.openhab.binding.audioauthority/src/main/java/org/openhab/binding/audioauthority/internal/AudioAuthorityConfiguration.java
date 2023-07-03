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
package org.openhab.binding.audioauthority.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.audioauthority.internal.connector.AudioAuthorityConnector;

/**
 * The {@link AudioAuthorityConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Dennis Drapeau - Initial contribution
 */

public class AudioAuthorityConfiguration {

    /**
     * Sample configuration parameters. Replace with your own.
     */
    public String ipAddress = "";

    /*
     * Polling Interval to poll the actual state of the Matrix amplifier
     */
    public int pollingInterval = 600;

    /**
     * The telnet port
     */
    public Integer telnetPort = 23;

    /*
     * Number of active output zones for the amplifier
     */
    public Integer activeZones = 8;
    /*
     * Unit number for matrix amplifier (up to 4 units can be chained together)
     */

    // Default maximum volume
    public static final BigDecimal MAX_VOLUME = new BigDecimal("80");

    private AudioAuthorityConnector connector;

    private BigDecimal mainVolumeMax = MAX_VOLUME;

    /*
     * List of Input Options
     */
    public List<String> inputOptions = new ArrayList<String>();

    public boolean isTelnet;

    public String getHost() {
        return ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public AudioAuthorityConnector getConnector() {
        return connector;
    }

    public void setConnector(AudioAuthorityConnector connector) {
        this.connector = connector;
    }

    public BigDecimal getMainVolumeMax() {
        return mainVolumeMax;
    }

    public void setMainVolumeMax(BigDecimal mainVolumeMax) {
        this.mainVolumeMax = mainVolumeMax;
    }

    public static BigDecimal getMaxVolume() {
        return MAX_VOLUME;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public Integer getTelnetPort() {
        return telnetPort;
    }

    public void setTelnetPort(Integer telnetPort) {
        this.telnetPort = telnetPort;
    }

    public Integer getActiveZones() {
        return activeZones;
    }

    public void setActiveZones(Integer activeZones) {
        this.activeZones = activeZones;
    }

    public List<String> getInputOptions() {
        return inputOptions;
    }

    public void setInputOptions(List<String> inputOptions) {
        this.inputOptions = inputOptions;
    }

    public boolean isTelnet() {
        // TODO Auto-generated method stub
        return isTelnet;
    }
}
