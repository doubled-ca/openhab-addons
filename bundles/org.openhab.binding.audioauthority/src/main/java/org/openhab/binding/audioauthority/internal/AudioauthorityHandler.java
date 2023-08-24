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
package org.openhab.binding.audioauthority.internal;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.binding.audioauthority.internal.connector.AudioauthorityConnector;
import org.openhab.binding.audioauthority.internal.connector.AudioauthorityConnectorFactory;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link AudioauthorityHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Dennis Drapeau - Initial contribution
 *
 **/

public class AudioauthorityHandler extends BaseThingHandler implements AudioauthorityStateChangedListener {

    private final Logger logger = LoggerFactory.getLogger(AudioauthorityHandler.class);

    private static final int RETRY_TIME_SECONDS = 30;

    private AudioauthorityConnector connector;
    private AudioauthorityConnectorFactory connectorFactory = new AudioauthorityConnectorFactory();
    private AudioauthorityState audioAuthorityState;
    private AudioauthorityConfiguration config;

    private ScheduledFuture<?> retryJob;

    public AudioauthorityHandler(Thing thing) {
        super(thing);
    }

    private static final Pattern zonePattern = Pattern.compile(AudioauthorityBindingConstants.ZONE_PATTERN_STRING);
    private static final Pattern powerPattern = Pattern.compile(AudioauthorityBindingConstants.POWER_PATTERN_STRING);
    private static final Pattern volumePattern = Pattern.compile(AudioauthorityBindingConstants.VOLUME_PATTERN_STRING);
    private static final Pattern volumeDBPattern = Pattern
            .compile(AudioauthorityBindingConstants.VOLUMEDB_PATTERN_STRING);
    private static final Pattern switchInputPattern = Pattern
            .compile(AudioauthorityBindingConstants.SWITCH_INPUT_PATTERN_STRING);
    private static final Pattern mutePattern = Pattern.compile(AudioauthorityBindingConstants.MUTE_PATTERN_STRING);
    private static final Pattern stereoPattern = Pattern.compile(AudioauthorityBindingConstants.STEREO_PATTERN_STRING);
    private static final Pattern namePattern = Pattern.compile(AudioauthorityBindingConstants.NAME_PATTERN_STRING);

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (connector == null) {
            return;
        }

        int commandZone = 0;
        Matcher mz = zonePattern.matcher(channelUID.getGroupId());
        Matcher mp = powerPattern.matcher(channelUID.getIdWithoutGroup());

        if (mz.matches()) {
            commandZone = Integer.parseInt(mz.group(1));
        }

        String id = channelUID.getId();

        try {

            if (powerPattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendPowerCommand(command, commandZone);
                return;
            }
            if (volumePattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendVolumeCommand(command, commandZone);
                return;
            }
            if (volumeDBPattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendVolumeDbCommand(command, commandZone);
                return;
            }
            if (switchInputPattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendInputCommand(command, commandZone);
                return;
            }
            if (mutePattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendMuteCommand(command, commandZone);
                return;
            }
            if (namePattern.matcher(channelUID.getIdWithoutGroup()).matches() && commandZone > 0) {
                connector.sendNameCommand(command, commandZone);
                return;
            }
            if (stereoPattern.matcher(channelUID.getId()).matches() && commandZone > 0) {
                connector.sendStereoCommand(command, commandZone);
                return;
            }

            throw new UnsupportedCommandTypeException("Couldn't match command channel");

        } catch (UnsupportedCommandTypeException e) {
            if (e.getMessage() != null) {
                logger.debug("{}", e.getMessage());
            }
            logger.debug("Unsupported command {} for channel {}", command, channelUID.getId());
        }

        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information:
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
    }

    @Override
    public void initialize() {
        cancelRetry();
        config = getConfigAs(AudioauthorityConfiguration.class);

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly, i.e. any network access must be done in
        // the background initialization below.
        // Also, before leaving this method a thing status from one of ONLINE, OFFLINE or UNKNOWN must be set. This
        // might already be the real thing status in case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.

        audioAuthorityState = new AudioauthorityState(this);

        updateStatus(ThingStatus.UNKNOWN);

        createConnection();
        // Example for background initialization:
        /*
         * scheduler.execute(() -> {
         * boolean thingReachable = true; // <background task with long running initialization here>
         * // when done do:
         * if (thingReachable) {
         * updateStatus(ThingStatus.ONLINE);
         * } else {
         * updateStatus(ThingStatus.OFFLINE);
         * }
         * });
         */

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        // logger.warn("Example warn message");
        //
        // Logging to INFO should be avoided normally.
        // See https://www.openhab.org/docs/developer/guidelines.html#f-logging

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    private void createConnection() {
        if (connector != null) {
            connector.dispose();
        }
        connector = connectorFactory.getConnector(config, audioAuthorityState, scheduler,
                this.getThing().getUID().getAsString());
        connector.connect();
    }

    private void cancelRetry() {
        ScheduledFuture<?> localRetryJob = retryJob;
        if (localRetryJob != null && !localRetryJob.isDone()) {
            localRetryJob.cancel(false);
        }
    }

    @Override
    public void dispose() {
        if (connector != null) {
            connector.dispose();
            connector = null;
        }
        cancelRetry();
        super.dispose();
    }

    @Override
    public void stateChanged(String channelID, State state) {
        logger.debug("Received state {} for channelID {}", state, channelID);

        // Don't flood the log with thing 'updated: ONLINE' each time a single channel changed
        if (this.getThing().getStatus() != ThingStatus.ONLINE) {
            updateStatus(ThingStatus.ONLINE);
        }
        updateState(channelID, state);
    }

    @Override
    public void connectionError(String errorMessage) {
        if (this.getThing().getStatus() != ThingStatus.OFFLINE) {
            // Don't flood the log with thing 'updated: OFFLINE' when already offline
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, errorMessage);
        }
        connector.dispose();
        retryJob = scheduler.schedule(this::createConnection, RETRY_TIME_SECONDS, TimeUnit.SECONDS);
    }
}
