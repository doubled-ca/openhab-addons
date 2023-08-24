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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

import org.openhab.binding.audioauthority.internal.AudioauthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioauthorityState;
import org.openhab.binding.audioauthority.internal.UnsupportedCommandTypeException;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.IncreaseDecreaseType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;

/**
 *
 * @author Dennis Drapeau
 *
 */
public abstract class AudioauthorityConnector {

    // private static final BigDecimal POINTFIVE = new BigDecimal("0.5");
    protected ScheduledExecutorService scheduler;
    protected AudioauthorityState state;
    protected AudioauthorityConfiguration config;

    public abstract void connect();

    public abstract void dispose();

    protected abstract void internalSendCommand(String command);

    public void sendCustomCommand(Command command) throws UnsupportedCommandTypeException {
        String cmd;
        if (command instanceof StringType) {
            cmd = command.toString();
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendStereoCommand(Command command, int zone) throws UnsupportedCommandTypeException {

        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "STEREO";
        if (command == OnOffType.ON) {
            cmd += "1";
        } else if (command == OnOffType.OFF) {
            cmd += ("0");
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendNameCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "N";

        if (command instanceof StringType) {
            if (Pattern.matches("N\"([a-zA-Z0-9- \\.,/!?\\\\]{0,16}?)\"", ((StringType) command).toString())) {
                cmd += "\"" + command.toString() + "\"";
            } else {
                throw new UnsupportedCommandTypeException("Name did not match expected form :  " + cmd);
            }

        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendInputCommand(Command command, int zone) throws UnsupportedCommandTypeException {

        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "I";

        if (command instanceof DecimalType) {
            int val = ((DecimalType) command).intValue();
            if (val < 1 || val > 20) {
                new UnsupportedCommandTypeException("Expected a volume in the range [1 - 20], volume: " + val);
            } else {

                cmd += val;
            }
        } else if (command instanceof StringType) {
            Integer val = Integer.parseInt(((StringType) command).toFullString());
            if (val < 1 || val > 20) {
                new UnsupportedCommandTypeException("Expected a input in the range [1 - 20], input: " + val);
            } else {

                cmd += val.toString();
            }
        } else {
            throw new UnsupportedCommandTypeException(
                    "Could not find value type to parse into Input. Type = " + command.getClass().toString());
        }

        internalSendCommand(cmd);
    }

    public void sendMuteCommand(Command command, int zone) throws UnsupportedCommandTypeException {

        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "M";
        if (command == OnOffType.ON) {
            cmd += "1";
        } else if (command == OnOffType.OFF) {
            cmd += ("0");
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendPowerCommand(Command command, int zone) throws UnsupportedCommandTypeException {

        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "P";
        if (command == OnOffType.ON) {
            cmd += "1";
        } else if (command == OnOffType.OFF) {
            cmd += ("0");
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendVolumeCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }
        BigDecimal range = new BigDecimal(80);
        String cmd = "U1" + "O" + zone + "V";
        if (command == IncreaseDecreaseType.INCREASE) {
            cmd += "U";
        } else if (command == IncreaseDecreaseType.DECREASE) {
            cmd += "D";
        } else if (command instanceof DecimalType) {
            int val = ((DecimalType) command).intValue();
            if (val < 0 || val > 100) {
                new UnsupportedCommandTypeException("Expected a volume in the range [0 - 100], volume: " + val);
            } else {
                BigDecimal db = ((DecimalType) command).toBigDecimal().scaleByPowerOfTen(-2).multiply(range)
                        .subtract(range).setScale(0, RoundingMode.HALF_UP);
                cmd += db.toPlainString();
            }
        } else if (command instanceof PercentType) {

            BigDecimal db = ((PercentType) command).toBigDecimal().scaleByPowerOfTen(-2).multiply(range).subtract(range)
                    .setScale(0, RoundingMode.HALF_UP);
            cmd += db.toPlainString();
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendVolumeDbCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        if (zone < 1 || zone > 16) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-16], zone: " + zone);
        }

        String cmd = "U1" + "O" + zone + "V";

        if (command instanceof PercentType) {
            throw new UnsupportedCommandTypeException();
        } else if (command instanceof DecimalType) {
            int val = ((DecimalType) command).intValue();
            if (val > 0 || val < -80) {
                new UnsupportedCommandTypeException("Volume must be in range [-80-0], volume: " + val);
            } else {
                cmd += val;
            }

            // convert dB to 'normal' volume by adding the offset of 80
            // dbCommand = new DecimalType(((DecimalType) command).toBigDecimal().add(DB_OFFSET));
        }
        internalSendCommand(cmd);
    }
}
