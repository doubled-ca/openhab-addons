
package org.openhab.binding.audioauthority.internal.connector;

import java.math.BigDecimal;
import java.util.concurrent.ScheduledExecutorService;

import org.openhab.binding.audioauthority.internal.AudioAuthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioAuthorityState;
import org.openhab.binding.audioauthority.internal.UnsupportedCommandTypeException;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.IncreaseDecreaseType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;

/*
 * Abstract class containing common connection to SOnaflex
 */
public abstract class AudioAuthorityConnector {

    private static final BigDecimal POINTFIVE = new BigDecimal("0.5");
    protected ScheduledExecutorService scheduler;
    protected AudioAuthorityState state;
    protected AudioAuthorityConfiguration config;

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

    public void sendInputCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        String zonePrefix;
        switch (zone) {
            case 1:
                zonePrefix = "SI";
                break;
            case 2:
            case 3:
            case 4:
                zonePrefix = "Z" + zone;
                break;
            default:
                throw new UnsupportedCommandTypeException("Zone must be in range [1-4], zone: " + zone);
        }
        String cmd = zonePrefix;
        if (command instanceof StringType) {
            cmd += command.toString();
        } else if (command instanceof RefreshType) {
            cmd += "?";
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendMuteCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        if (zone < 1 || zone > 4) {
            throw new UnsupportedCommandTypeException("Zone must be in range [1-4], zone: " + zone);
        }
        StringBuilder sb = new StringBuilder();
        if (zone != 1) {
            sb.append("Z").append(zone);
        }
        sb.append("MU");
        String cmd = sb.toString();
        if (command == OnOffType.ON) {
            cmd += "ON";
        } else if (command == OnOffType.OFF) {
            cmd += "OFF";
        } else if (command instanceof RefreshType) {
            cmd += "?";
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendPowerCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        String zonePrefix;
        switch (zone) {
            case 0:
                zonePrefix = "PW";
                break;
            case 1:
                zonePrefix = "ZM";
                break;
            case 2:
            case 3:
            case 4:
                zonePrefix = "Z" + zone;
                break;
            default:
                throw new UnsupportedCommandTypeException("Zone must be in range [0-4], zone: " + zone);
        }
        String cmd = zonePrefix;
        if (command == OnOffType.ON) {
            cmd += "ON";
        } else if (command == OnOffType.OFF) {
            cmd += (zone == 0) ? "STANDBY" : "OFF";
        } else if (command instanceof RefreshType) {
            cmd += "?";
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendVolumeCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        String zonePrefix;
        switch (zone) {
            case 1:
                zonePrefix = "MV";
                break;
            case 2:
            case 3:
            case 4:
                zonePrefix = "Z" + zone;
                break;
            default:
                throw new UnsupportedCommandTypeException("Zone must be in range [1-4], zone: " + zone);
        }
        String cmd = zonePrefix;
        if (command instanceof RefreshType) {
            cmd += "?";
        } else if (command == IncreaseDecreaseType.INCREASE) {
            cmd += "UP";
        } else if (command == IncreaseDecreaseType.DECREASE) {
            cmd += "DOWN";
        } else if (command instanceof DecimalType) {
            cmd += toDenonValue(((DecimalType) command));
        } else if (command instanceof PercentType) {
            // cmd += percentToDenonValue(((PercentType) command).toBigDecimal());
        } else {
            throw new UnsupportedCommandTypeException();
        }
        internalSendCommand(cmd);
    }

    public void sendVolumeDbCommand(Command command, int zone) throws UnsupportedCommandTypeException {
        Command dbCommand = command;
        if (dbCommand instanceof PercentType) {
            throw new UnsupportedCommandTypeException();
        } else if (dbCommand instanceof DecimalType) {
            // convert dB to 'normal' volume by adding the offset of 80
            // dbCommand = new DecimalType(((DecimalType) command).toBigDecimal().add(DB_OFFSET));
        }
        sendVolumeCommand(dbCommand, zone);
    }

    protected String toDenonValue(DecimalType number) {
        String dbString = String.valueOf(number.intValue());
        BigDecimal num = number.toBigDecimal();
        if (num.compareTo(BigDecimal.TEN) == -1) {
            dbString = "0" + dbString;
        }
        if (num.remainder(BigDecimal.ONE).equals(POINTFIVE)) {
            dbString = dbString + "5";
        }
        return dbString;
    }

    /*
     * protected String percentToDenonValue(BigDecimal pct) {
     * Round to nearest number divisible by 0.5
     * BigDecimal percent = pct.divide(POINTFIVE).setScale(0, RoundingMode.UP).multiply(POINTFIVE)
     * .min(config.getMainVolumeMax()).max(BigDecimal.ZERO);
     *
     * return toDenonValue(new DecimalType(percent));
     * }
     */
}
