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
package org.openhab.binding.audioauthority.internal.connector.telnet;

import java.math.BigDecimal;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.binding.audioauthority.internal.AudioAuthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioAuthorityState;
import org.openhab.binding.audioauthority.internal.connector.AudioAuthorityConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dennis Drapeau - Initial contribution
 *
 */
public class AudioAuthorityTelnetConnector extends AudioAuthorityConnector implements AudioAuthorityTelnetListener {

    private final Logger logger = LoggerFactory.getLogger(AudioAuthorityTelnetConnector.class);

    protected boolean disposing = false;

    // All regular commands. Example: PW, SICD, SITV, Z2MU
    // private static final Pattern COMMAND_PATTERN = Pattern.compile("^([A-Z0-9]{2})(.+)$");

    public static final String RESPONSE_START_PATTERN = "[\\(\\[]";

    public static final String RESPONSE_END_PATTERN = "[\\)\\]]";

    public static final String COMMAND_PREFIX = "[";

    public static final String COMMAND_SUFFIX = "]";

    // ------------------------------------------------------------------------------------------------
    // The following are the various command formats specified by the Sonaflex SF16M protocol

    // common pattern elements to build patterns from
    private static final String U_PATTERN_STRING = "U([1-4])";
    private static final String I_PATTERN_STRING = "I([1-9]|1[0-9]|21|34|56|78|910|1112|1314|1516)";
    private static final String O_PATTERN_STRING = "O([1-9]|1[0-6])";
    private static final String PATTERN_PREFIX_STRING = "^[\\(\\[]";
    private static final String PATTERN_SUFFIX_STRING = "[\\)\\]]$";

    /**
     * makePattern - helper function to generate patterns for validating responses received and commands sent
     *
     * @param unit - unit to apply command to (1-4, empty string for global ALL commands)
     * @param group - group to apply command to (eg input,output) or empty string for global commands
     * @param cmd - protocol command and value to pass for command
     * @return Pattern
     */
    private static Pattern makePattern(String unit, String section, String cmd) {
        return Pattern.compile(PATTERN_PREFIX_STRING + unit + section + cmd + PATTERN_SUFFIX_STRING);
    }

    /*
     * Input Group Commands
     *
     * Gain [U#I##G##] U1-4, I1-20, G-10-10 (dB) Set the level of digital input gain on a stereo pair or mono input
     * channel. This feature is designed to equalize the input volumes of sources that do not have volume control.
     *
     * Gain All [U#XG##] U1-4, G-10-10 (dB) Set the level of digital input gain on all inputs.
     *
     * Name [U#I#N"@"] U1-4, I1-20, N (16 characters a-z, A-Z, 0-9, -.,/!?\) Names the specified input stereo pair or
     * mono source for front panel display.
     *
     * Stereo [U#I##STEREO#] U1-4, I1-20, STEREO0-2 (0 off, 1 on, 2 toggle) Sets an input pair as stereo or mono, which
     * defines how stereo or mono outputs will connect to the input. For example, a stereo input of (1,2) will be
     * selected by a stereo output as (1,2), but a mono output as (1+2). A mono input of 1 will be selected by a mono
     * output as 1, but a stereo output as (1,1).
     */

    /*
     * private static final String CMD_INPUT_GAIN_FORMAT = "U%dI%dG%d";
     * private static final String CMD_INPUT_GAIN_ALL_FORMAT = "U%dXG%d";
     * private static final String CMD_INPUT_NAME_FORMAT = "U%dI%dN\"%s\"";
     * private static final String CMD_INPUT_STEREO_FORMAT = "U%dI%dSTEREO%d";
     *
     * // patterns to validate CMDs sent and responses received from Sonaflex Amplifier
     * private static final Pattern INPUT_GAIN_PATTERN = makePattern(U_PATTERN_STRING, I_PATTERN_STRING,
     * "G(-10|-[1-9]|0|[1-9]|10)");
     * private static final Pattern INPUT_GAIN_ALL_PATTERN = makePattern("", U_PATTERN_STRING,
     * "XG(-10|-[1-9]|0|[1-9]|10)");
     * private static final Pattern INPUT_NAME_PATTERN = makePattern(U_PATTERN_STRING, I_PATTERN_STRING,
     * "N\"([a-zA-Z0-9- \\.,/!?\\\\]{0,16}?)\"");
     * private static final Pattern INPUT_STEREO_PATTERN = makePattern(U_PATTERN_STRING, I_PATTERN_STRING,
     * "STEREO[0-2]");
     */
    /*
     * Output Specific Commands
     *
     * Balance [U#O##BAL##] U1-4, O1-16, BAL-40-0 (dB) Set the balance level of an output. This setting is a negative
     * volume offset applied each time the volume level is set for the output.
     *
     * Balance Left [U#O##BALL] U1-4, O1-16 Adjust the balance level for the specified output pair to the left
     * (decreasing the output volume of the right output).
     *
     * Balance Right [U#O##BALR] U1-4, O1-16 Adjust the balance level for the specified output pair to the right
     * (decreasing the output volume of the left output).
     *
     * Mute [U#O##M#] U1-4, O1-16, M0-2 (0 off, 1 on, 2 toggle) Change the mute status of an output.
     *
     * Mute All Global [XM#] M0-2 (0 off, 1 on, 2 toggle) Change the mute status of all outputs.
     *
     * Mute All [U#XM#] U1-4, M0-2 (0 off, 1 on, 2 toggle) Change the mute status of all the outputs of the specified
     * unit.
     *
     * Name [U#O#N"@"] U1-4, O1-16, N (16 characters a-z, A-Z, 0-9, -.,/!?\) Name the specified single output or stereo
     * pair.
     *
     * Power [U#O#P#] U1-4, O1-16, P0-2 (0 off, 1 on, 2 toggle) Set the power status of a single output or stereo pair.
     * An output that is powered from off to on will be checked to confirm that the output volume level fits into
     * allowed limits and may be adjusted.
     *
     * Power All Global [XP#] P0-2 (0 off, 1 on, 2 toggle) Set the power status of all outputs. If power status is
     * toggled, each output will be toggled individually, where outputs that are off will be turned on and outputs that
     * are on will be turned off.
     *
     * Power All [U#XP#] U1-4, P0-2 (0 off, 1 on, 2 toggle) Set the power status of all outputs in the specified unit.
     * If power status is toggled, each output will be toggled individually, where outputs that are off will be turned
     * on and outputs that are on will be turned off.
     *
     * Query [U#O##Q] U1-4, O1-16 Query a specified output, which displays all settings related to that particular
     * output.
     *
     * Stereo [U#O##STEREO#] U1-4, O1-16, STEREO0-2 (0 off, 1 on, 2 toggle) Enables or disables the pairing of outputs.
     * Stereo mode causes two outputs to track each other when performing all functions, and also controls the manner in
     * which inputs are connected to the outputs. For example, consider a pair of stereo outputs (1,2), a mono output
     * (3), a pair of stereo inputs (5,6) and a mono input (7). Valid connections will be (1,2) = (5,6) or (7,7), and
     * (3) = (5+6) or (7).
     *
     * Switch [U#O##I##] U1-4, O1-16, I1-20 Switches the single output or stereo pair to the specified single input or
     * stereo pair.
     *
     * Switch All Global [XI##] I1-20 Switches all outputs to the specified input observing mono/stereo connection
     * rules.
     *
     * Switch All [U#XI##] U1-4, I1-20 Switches all outputs of a unit to the specified input observing mono/stereo
     * connection rules.
     *
     * Volume [U#O##V###] U1-4, O1-16, V-80-0(dB) Set the output volume of a single output or stereo pair.
     * Volume Down [U#O##VD] U1-4, O1-16 Increment the volume of a single output or stereo pair.
     * Volume Up [U#O##VU] U1-4, O1-16 Decrement the volume of a single output or stereo pair.
     * Volume All Global [XV###] V-80-0 (dB) Set the output volume level of all outputs.
     * Volume All Down Global [XVD] Increments the output volume level of all outputs from their current setting.
     * Volume All Up Global [XVU] Decrements the output volume level of all outputs from their current setting.
     * Volume All Unit [U#XV###] U1-4, V-80-0 (dB) Set the output volume level of all outputs of a unit as specified.
     * Volume All Unit Down [U#XVD] U1-4 Increments the output volume level of all outputs of a unit from their current
     * setting.
     *
     * Volume All Unit Up [U#XVU] U1-4 Decrements the output volume level of all outputs of a unit from their current
     * setting.
     *
     * Volume Max [U#O##MV###] U1-4, O1-16, MV-80-0 (dB) Sets the maximum volume level of an output. Note that this
     * setting also directly affects the nominal output volume, the current 'live' output volume, override levels, the
     * maximum turn on volume, the minimum turn on volume, and scene preset volumes. If any of these contain a level
     * that exceeds the maximum, they will be set to the maximum.
     *
     * Volume Max Turn On [U#O##MXTO###] U1-4, O1-16, MXTO-80-0 (dB) Sets the maximum turn on volume. After a power
     * cycle or return from standby, the desired output volume will be checked against this, and any levels exceeding
     * will be set to the maximum.
     *
     * Volume Min Turn On [U#O##MNTO###] U1-4, O1-16, MNTO-80-0 (dB) Sets the minimum turn on volume. After a power
     * cycle or return from standby, the desired output volume will be checked against this, and will be set to this
     * level if too low.
     */

    /*
     * private static final String CMD_OUTPUT_BALANCE_FORMAT = "U%dO%dBAL%d";
     * private static final String CMD_OUTPUT_BALANCE_LEFT_RIGHT_FORMAT = "U%dO%dBAL%s";
     * private static final String CMD_OUTPUT_MUTE_FORMAT = "U%dO%dM%d";
     * private static final String CMD_OUTPUT_MUTE_ALL_GLOBAL_FORMAT = "XM%d";
     * private static final String CMD_OUTPUT_MUTE_ALL_FORMAT = "U%dXM%d";
     * private static final String CMD_OUTPUT_NAME_FORMAT = "U%dO%dN\"%s\"";
     * private static final String CMD_OUTPUT_POWER_FORMAT = "U%dO%dP%d";
     * private static final String CMD_OUTPUT_POWER_ALL_GLOBAL_FORMAT = "XP%d";
     * private static final String CMD_OUTPUT_POWER_ALL_FORMAT = "U%dXP%d";
     * private static final String CMD_OUTPUT_QUERY_FORMAT = "U%dO%dQ";
     * private static final String CMD_OUTPUT_STEREO_FORMAT = "U%dO%dSTEREO%d";
     * private static final String CMD_OUTPUT_SWITCH_INPUT_FORMAT = "U%dO%dI%d";
     * private static final String CMD_OUTPUT_SWITCH_INPUT_ALL_GLOBAL_FORMAT = "XI%d";
     * private static final String CMD_OUTPUT_SWITCH_INPUT_ALL_FORMAT = "U%dI%d";
     * private static final String CMD_OUTPUT_VOLUME_FORMAT = "U%dO%dV%d";
     * private static final String CMD_OUTPUT_VOLUME_ALL_FORMAT = "U%dXV%d";
     * private static final String CMD_OUTPUT_VOLUME_ALL_GLOBAL_FORMAT = "XV%d";
     * private static final String CMD_OUTPUT_VOLUME_MAX_FORMAT = "U%dO%dMV%d";
     * private static final String CMD_OUTPUT_VOLUME_MAX_TURN_ON_FORMAT = "U%dO%dMXTO%d";
     * private static final String CMD_OUTPUT_VOLUME_MIN_TURN_ON_FORMAT = "U%dO%dMNTO%d";
     */
    // output patterns to validate command before sending and responses received from Sonaflex Amplifier

    /*
     * private static final Pattern OUTPUT_BALANCE_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
     * "BAL(-[1-9]|-[123][0-9]|-40|[LR])");
     */
    private static final Pattern OUTPUT_MUTE_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING, "M([0-2])");
    // private static final Pattern OUTPUT_MUTE_ALL_GLOBAL_PATTERN = makePattern("", "X", "M[0-2]");
    // private static final Pattern OUTPUT_MUTE_ALL_PATTERN = makePattern(U_PATTERN_STRING, "X", "M[0-2]");
    private static final Pattern OUTPUT_NAME_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
            "N\"([a-zA-Z0-9- \\.,/!?\\\\]{0,16}?)\"");
    private static final Pattern OUTPUT_POWER_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING, "P([0-2])");
    /*
     * private static final Pattern OUTPUT_POWER_ALL_PATTERN = makePattern(U_PATTERN_STRING, "X", "P([0-2])");
     * private static final Pattern OUTPUT_POWER_ALL_GLOBAL_PATTERN = makePattern("", "X", "P([0-2])");
     * private static final Pattern OUTPUT_QUERY_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING, "Q");
     */
    private static final Pattern OUTPUT_STEREO_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
            "STEREO([0-2])");
    private static final Pattern OUTPUT_SWITCH_INPUT_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
            I_PATTERN_STRING);
    /*
     * private static final Pattern OUTPUT_SWITCH_INPUT_ALL_GLOBAL_PATTERN = makePattern("", "X", "I([1-9]|1[0-9]|20)");
     * private static final Pattern OUTPUT_SWITCH_INPUT_ALL_PATTERN = makePattern(U_PATTERN_STRING, "X",
     * "I([1-9]|1[0-9]|20)");
     */
    private static final Pattern OUTPUT_VOLUME_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
            "V(-[1-7][0-9]|-[1-9]|-80|0|[UD])");
    /*
     * private static final Pattern OUTPUT_VOLUME_ALL_PATTERN = makePattern(U_PATTERN_STRING, "X",
     * "V(-[1-7][0-9]|-[1-9]|-80|0|[UD])");
     * private static final Pattern OUTPUT_VOLUME_ALL_GLOBAL_PATTERN = makePattern("", "X",
     * "V(-[1-7][0-9]|-[1-9]|-80|0|[UD])");
     * private static final Pattern OUTPUT_VOLUME_MAX_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
     * "MV(-[1-7][0-9]|-[1-9]|-80|0)");
     */
    /*
     * private static final Pattern OUTPUT_VOLUME_MAX_TURN_ON_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
     * "MXTO(-[1-7][0-9]|-[1-9]|-80|0)");
     * private static final Pattern OUTPUT_VOLUME_MIN_TURN_ON_PATTERN = makePattern(U_PATTERN_STRING, O_PATTERN_STRING,
     * "MNTO(-[1-7][0-9]|-[1-9]|-80|0)");
     */
    private static final String CMD_PING = "ping";

    private AudioAuthorityTelnetClientThread telnetClientThread;

    private Future<?> telnetStateRequest;

    private String thingUID;

    public AudioAuthorityTelnetConnector(AudioAuthorityConfiguration config, AudioAuthorityState state,
            ScheduledExecutorService scheduler, String thingUID) {
        this.config = config;
        this.state = state;
        this.scheduler = scheduler;
        this.thingUID = thingUID;
    }

    @Override
    public void connect() {
        telnetClientThread = new AudioAuthorityTelnetClientThread(config, this);
        telnetClientThread.setName("OH-binding-" + thingUID);
        telnetClientThread.start();
    }

    @Override
    public void dispose() {
        logger.debug("disposing connector");
        disposing = true;

        if (telnetStateRequest != null) {
            telnetStateRequest.cancel(true);
            telnetStateRequest = null;
        }

        if (telnetClientThread != null) {
            telnetClientThread.interrupt();
            // Invoke a shutdown after interrupting the thread to close the socket immediately,
            // otherwise the client keeps running until a line was received from the telnet connection
            telnetClientThread.shutdown();
            telnetClientThread = null;
        }
    }

    @Override
    protected void internalSendCommand(String command) {

        if (command == null || command.isBlank()) {
            logger.warn("Tried to send empty command");
            return;
        }
        String cmd = "[" + command + "]\r\n";
        logger.debug("Sending command '{}'", cmd);
        telnetClientThread.sendCommand(cmd);
    }

    @Override
    public void receivedLine(String line) {
        if (line == "") {
            return;
        }

        logger.debug("Response Received: " + line);

        Matcher m;

        m = OUTPUT_POWER_PATTERN.matcher(line);
        if (m.matches()) {
            receivedPowerLine(m);
            return;
        }

        m = OUTPUT_MUTE_PATTERN.matcher(line);
        if (m.matches()) {
            receivedMuteLine(m);
            return;
        }

        m = OUTPUT_VOLUME_PATTERN.matcher(line);
        if (m.matches()) {
            receivedVolumeLine(m);
            return;
        }

        m = OUTPUT_STEREO_PATTERN.matcher(line);
        if (m.matches()) {
            receivedStereoLine(m);
            return;
        }

        m = OUTPUT_SWITCH_INPUT_PATTERN.matcher(line);
        if (m.matches()) {
            receivedSwitchInputLine(m);
            return;
        }

        m = OUTPUT_NAME_PATTERN.matcher(line);
        if (m.matches()) {
            receivedNameLine(m);
            return;
        }

        logger.warn("Received Line not handled: {}", line);
    }

    private void receivedNameLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        String newName = m.group(3).toString();

        switch (zoneNum) {
            case 1:
                state.setZone1Name(newName);
                break;
            case 2:
                state.setZone2Name(newName);
                break;
            case 3:
                state.setZone3Name(newName);
                break;
            case 4:
                state.setZone4Name(newName);
                break;
            case 5:
                state.setZone5Name(newName);
                break;
            case 6:
                state.setZone6Name(newName);
                break;
            case 7:
                state.setZone7Name(newName);
                break;
            case 8:
                state.setZone8Name(newName);
                break;
            case 9:
                state.setZone9Name(newName);
                break;
            case 10:
                state.setZone10Name(newName);
                break;
            case 11:
                state.setZone11Name(newName);
                break;
            case 12:
                state.setZone12Name(newName);
                break;
            case 13:
                state.setZone13Name(newName);
                break;
            case 14:
                state.setZone14Name(newName);
                break;
            case 15:
                state.setZone15Name(newName);
                break;
            case 16:
                state.setZone16Name(newName);
                break;
        }
    }

    private void receivedSwitchInputLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        int switchInputNum = Integer.parseInt(m.group(3));

        BigDecimal newSwitchInput = new BigDecimal(switchInputNum);

        switch (zoneNum) {
            case 1:
                state.setZone1Input(newSwitchInput);
                break;
            case 2:
                state.setZone2Input(newSwitchInput);
                break;
            case 3:
                state.setZone3Input(newSwitchInput);
                break;
            case 4:
                state.setZone4Input(newSwitchInput);
                break;
            case 5:
                state.setZone5Input(newSwitchInput);
                break;
            case 6:
                state.setZone6Input(newSwitchInput);
                break;
            case 7:
                state.setZone7Input(newSwitchInput);
                break;
            case 8:
                state.setZone8Input(newSwitchInput);
                break;
            case 9:
                state.setZone9Input(newSwitchInput);
                break;
            case 10:
                state.setZone10Input(newSwitchInput);
                break;
            case 11:
                state.setZone11Input(newSwitchInput);
                break;
            case 12:
                state.setZone12Input(newSwitchInput);
                break;
            case 13:
                state.setZone13Input(newSwitchInput);
                break;
            case 14:
                state.setZone14Input(newSwitchInput);
                break;
            case 15:
                state.setZone15Input(newSwitchInput);
                break;
            case 16:
                state.setZone16Input(newSwitchInput);
                break;
        }
    }

    private void receivedStereoLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        int stereoNum = Integer.parseInt(m.group(3));

        boolean newStereo = false;

        switch (stereoNum) {
            case 0:
            case 1:
                newStereo = stereoNum == 1;
                break;
            // don't need to catch P2 as the received line will only be 0 or 1
        }

        switch (zoneNum) {
            case 1:
                state.setZone1Stereo(newStereo);
                break;
            case 2:
                state.setZone2Stereo(newStereo);
                break;
            case 3:
                state.setZone3Stereo(newStereo);
                break;
            case 4:
                state.setZone4Stereo(newStereo);
                break;
            case 5:
                state.setZone5Stereo(newStereo);
                break;
            case 6:
                state.setZone6Stereo(newStereo);
                break;
            case 7:
                state.setZone7Stereo(newStereo);
                break;
            case 8:
                state.setZone8Stereo(newStereo);
                break;
            case 9:
                state.setZone9Stereo(newStereo);
                break;
            case 10:
                state.setZone10Stereo(newStereo);
                break;
            case 11:
                state.setZone11Stereo(newStereo);
                break;
            case 12:
                state.setZone12Stereo(newStereo);
                break;
            case 13:
                state.setZone13Stereo(newStereo);
                break;
            case 14:
                state.setZone14Stereo(newStereo);
                break;
            case 15:
                state.setZone15Stereo(newStereo);
                break;
            case 16:
                state.setZone16Stereo(newStereo);
                break;
        }
    }

    private void receivedVolumeLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        int volumeNum = Integer.parseInt(m.group(3));

        BigDecimal newVolume = BigDecimal.valueOf(volumeNum);

        switch (zoneNum) {
            case 1:
                state.setZone1Volume(newVolume);
                break;
            case 2:
                state.setZone2Volume(newVolume);
                break;
            case 3:
                state.setZone3Volume(newVolume);
                break;
            case 4:
                state.setZone4Volume(newVolume);
                break;
            case 5:
                state.setZone5Volume(newVolume);
                break;
            case 6:
                state.setZone6Volume(newVolume);
                break;
            case 7:
                state.setZone7Volume(newVolume);
                break;
            case 8:
                state.setZone8Volume(newVolume);
                break;
            case 9:
                state.setZone9Volume(newVolume);
                break;
            case 10:
                state.setZone10Volume(newVolume);
                break;
            case 11:
                state.setZone11Volume(newVolume);
                break;
            case 12:
                state.setZone12Volume(newVolume);
                break;
            case 13:
                state.setZone13Volume(newVolume);
                break;
            case 14:
                state.setZone14Volume(newVolume);
                break;
            case 15:
                state.setZone15Volume(newVolume);
                break;
            case 16:
                state.setZone16Volume(newVolume);
                break;
        }
    }

    private void receivedPowerLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        int powerNum = Integer.parseInt(m.group(3));

        boolean newPower = false;

        switch (powerNum) {
            case 0:
            case 1:
                newPower = powerNum == 1;
                break;
            // don't need to catch P2 as the received line will only be 0 or 1
        }

        switch (zoneNum) {
            case 1:
                state.setZone1Power(newPower);
                break;
            case 2:
                state.setZone2Power(newPower);
                break;
            case 3:
                state.setZone3Power(newPower);
                break;
            case 4:
                state.setZone4Power(newPower);
                break;
            case 5:
                state.setZone5Power(newPower);
                break;
            case 6:
                state.setZone6Power(newPower);
                break;
            case 7:
                state.setZone7Power(newPower);
                break;
            case 8:
                state.setZone8Power(newPower);
                break;
            case 9:
                state.setZone9Power(newPower);
                break;
            case 10:
                state.setZone10Power(newPower);
                break;
            case 11:
                state.setZone11Power(newPower);
                break;
            case 12:
                state.setZone12Power(newPower);
                break;
            case 13:
                state.setZone13Power(newPower);
                break;
            case 14:
                state.setZone14Power(newPower);
                break;
            case 15:
                state.setZone15Power(newPower);
                break;
            case 16:
                state.setZone16Power(newPower);
                break;
        }
    }

    private void receivedMuteLine(Matcher m) {
        // int unitNum = Integer.parseInt(m.group(1));
        int zoneNum = Integer.parseInt(m.group(2));
        int muteNum = Integer.parseInt(m.group(3));

        boolean newMute = false;

        switch (muteNum) {
            case 0:
            case 1:
                newMute = muteNum == 1;
                break;
            // don't need to catch P2 as the received line will only be 0 or 1
        }

        switch (zoneNum) {
            case 1:
                state.setZone1Mute(newMute);
                break;
            case 2:
                state.setZone2Mute(newMute);
                break;
            case 3:
                state.setZone3Mute(newMute);
                break;
            case 4:
                state.setZone4Mute(newMute);
                break;
            case 5:
                state.setZone5Mute(newMute);
                break;
            case 6:
                state.setZone6Mute(newMute);
                break;
            case 7:
                state.setZone7Mute(newMute);
                break;
            case 8:
                state.setZone8Mute(newMute);
                break;
            case 9:
                state.setZone9Mute(newMute);
                break;
            case 10:
                state.setZone10Mute(newMute);
                break;
            case 11:
                state.setZone11Mute(newMute);
                break;
            case 12:
                state.setZone12Mute(newMute);
                break;
            case 13:
                state.setZone13Mute(newMute);
                break;
            case 14:
                state.setZone14Mute(newMute);
                break;
            case 15:
                state.setZone15Mute(newMute);
                break;
            case 16:
                state.setZone16Mute(newMute);
                break;
        }
    }

    @Override
    public void telnetClientConnected(boolean connected) {
        if (!connected) {
            if (config.isTelnet() && !disposing) {
                logger.debug("Telnet client disconnected.");
                state.connectionError(
                        "Error connecting to the telnet port. Consider disabling telnet in this Thing's configuration to use HTTP polling instead.");
            }
        } else {
            refreshState();
        }
    }

    private void refreshState() {
        // Sends a series of state query commands over the telnet connection
        telnetStateRequest = scheduler.submit(() -> {

            internalSendCommand("U1O8Q");
            internalSendCommand("U1O9Q");
            internalSendCommand("U1O10Q");
            internalSendCommand("U1O11Q");
            internalSendCommand("U1O12Q");
            internalSendCommand("U1O13Q");
            internalSendCommand("U1O14Q");
            internalSendCommand("U1O15Q");
            internalSendCommand("U1O16Q");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.trace("requestStateOverTelnet() - Interrupted while requesting state.");
                Thread.currentThread().interrupt();
            }

        });
    }
}
