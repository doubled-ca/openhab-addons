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
 *
 *
 *
 */
package org.openhab.binding.audioauthority.internal;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.State;

/**
 *
 * @author Dennis Drapeau -- Initial Contribution
 *
 **/
public class AudioauthorityState {
    // Zones
    private State zone1Power;
    private State zone1Volume;
    private State zone1VolumeDB;
    private State zone1Mute;
    private State zone1Input;
    private State zone1Stereo;
    private State zone1Name;

    private State zone2Power;
    private State zone2Volume;
    private State zone2VolumeDB;
    private State zone2Mute;
    private State zone2Input;
    private State zone2Stereo;
    private State zone2Name;

    private State zone3Power;
    private State zone3Volume;
    private State zone3VolumeDB;
    private State zone3Mute;
    private State zone3Input;
    private State zone3Stereo;
    private State zone3Name;

    private State zone4Power;
    private State zone4Volume;
    private State zone4VolumeDB;
    private State zone4Mute;
    private State zone4Input;
    private State zone4Stereo;
    private State zone4Name;

    private State zone5Power;
    private State zone5Volume;
    private State zone5VolumeDB;
    private State zone5Mute;
    private State zone5Input;
    private State zone5Stereo;
    private State zone5Name;

    private State zone6Power;
    private State zone6Volume;
    private State zone6VolumeDB;
    private State zone6Mute;
    private State zone6Input;
    private State zone6Stereo;
    private State zone6Name;

    private State zone7Power;
    private State zone7Volume;
    private State zone7VolumeDB;
    private State zone7Mute;
    private State zone7Input;
    private State zone7Stereo;
    private State zone7Name;

    private State zone8Power;
    private State zone8Volume;
    private State zone8VolumeDB;
    private State zone8Mute;
    private State zone8Input;
    private State zone8Stereo;
    private State zone8Name;

    private State zone9Power;
    private State zone9Volume;
    private State zone9VolumeDB;
    private State zone9Mute;
    private State zone9Input;
    private State zone9Stereo;
    private State zone9Name;

    private State zone10Power;
    private State zone10Volume;
    private State zone10VolumeDB;
    private State zone10Mute;
    private State zone10Input;
    private State zone10Stereo;
    private State zone10Name;

    private State zone11Power;
    private State zone11Volume;
    private State zone11VolumeDB;
    private State zone11Mute;
    private State zone11Input;
    private State zone11Stereo;
    private State zone11Name;

    private State zone12Power;
    private State zone12Volume;
    private State zone12VolumeDB;
    private State zone12Mute;
    private State zone12Input;
    private State zone12Stereo;
    private State zone12Name;

    private State zone13Power;
    private State zone13Volume;
    private State zone13VolumeDB;
    private State zone13Mute;
    private State zone13Input;
    private State zone13Stereo;
    private State zone13Name;

    private State zone14Power;
    private State zone14Volume;
    private State zone14VolumeDB;
    private State zone14Mute;
    private State zone14Input;
    private State zone14Stereo;
    private State zone14Name;

    private State zone15Power;
    private State zone15Volume;
    private State zone15VolumeDB;
    private State zone15Mute;
    private State zone15Input;
    private State zone15Stereo;
    private State zone15Name;

    private State zone16Power;
    private State zone16Volume;
    private State zone16VolumeDB;
    private State zone16Mute;
    private State zone16Input;
    private State zone16Stereo;
    private State zone16Name;

    private AudioauthorityStateChangedListener handler;

    public AudioauthorityState(AudioauthorityStateChangedListener handler) {
        this.handler = handler;
    }

    public void connectionError(String errorMessage) {
        handler.connectionError(errorMessage);
    }

    public State getStateForChannelID(String channelID) {
        switch (channelID) {
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_INPUT:
                return zone1Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_POWER:
                return zone1Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_VOLUME:
                return zone1Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_VOLUME_DB:
                return zone1VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_MUTE:
                return zone1Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_STEREO:
                return zone1Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE1_NAME:
                return zone1Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_INPUT:
                return zone2Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_POWER:
                return zone2Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_VOLUME:
                return zone2Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_VOLUME_DB:
                return zone2VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_MUTE:
                return zone2Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_STEREO:
                return zone2Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE2_NAME:
                return zone2Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_INPUT:
                return zone3Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_POWER:
                return zone3Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_VOLUME:
                return zone3Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_VOLUME_DB:
                return zone3VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_MUTE:
                return zone3Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_STEREO:
                return zone3Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE3_NAME:
                return zone3Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_INPUT:
                return zone4Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_POWER:
                return zone4Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_VOLUME:
                return zone4Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_VOLUME_DB:
                return zone4VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_MUTE:
                return zone4Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_STEREO:
                return zone4Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE4_NAME:
                return zone4Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_INPUT:
                return zone5Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_POWER:
                return zone5Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_VOLUME:
                return zone5Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_VOLUME_DB:
                return zone5VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_MUTE:
                return zone5Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_STEREO:
                return zone5Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE5_NAME:
                return zone5Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_INPUT:
                return zone6Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_POWER:
                return zone6Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_VOLUME:
                return zone6Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_VOLUME_DB:
                return zone6VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_MUTE:
                return zone6Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_STEREO:
                return zone6Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE6_NAME:
                return zone6Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_INPUT:
                return zone7Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_POWER:
                return zone7Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_VOLUME:
                return zone7Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_VOLUME_DB:
                return zone7VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_MUTE:
                return zone7Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_STEREO:
                return zone7Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE7_NAME:
                return zone7Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_INPUT:
                return zone8Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_POWER:
                return zone8Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_VOLUME:
                return zone8Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_VOLUME_DB:
                return zone8VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_MUTE:
                return zone8Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_STEREO:
                return zone8Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE8_NAME:
                return zone8Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_INPUT:
                return zone9Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_POWER:
                return zone9Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_VOLUME:
                return zone9Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_VOLUME_DB:
                return zone9VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_MUTE:
                return zone9Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_STEREO:
                return zone9Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE9_NAME:
                return zone9Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_INPUT:
                return zone10Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_POWER:
                return zone10Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_VOLUME:
                return zone10Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_VOLUME_DB:
                return zone10VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_MUTE:
                return zone10Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_STEREO:
                return zone10Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE10_NAME:
                return zone10Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_INPUT:
                return zone11Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_POWER:
                return zone11Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_VOLUME:
                return zone11Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_VOLUME_DB:
                return zone11VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_MUTE:
                return zone11Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_STEREO:
                return zone11Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE11_NAME:
                return zone11Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_INPUT:
                return zone12Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_POWER:
                return zone12Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_VOLUME:
                return zone12Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_VOLUME_DB:
                return zone12VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_MUTE:
                return zone12Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_STEREO:
                return zone12Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE12_NAME:
                return zone12Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_INPUT:
                return zone13Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_POWER:
                return zone13Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_VOLUME:
                return zone13Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_VOLUME_DB:
                return zone13VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_MUTE:
                return zone13Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_STEREO:
                return zone13Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE13_NAME:
                return zone13Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_INPUT:
                return zone14Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_POWER:
                return zone14Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_VOLUME:
                return zone14Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_VOLUME_DB:
                return zone14VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_MUTE:
                return zone14Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_STEREO:
                return zone14Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE14_NAME:
                return zone14Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_INPUT:
                return zone15Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_POWER:
                return zone15Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_VOLUME:
                return zone15Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_VOLUME_DB:
                return zone15VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_MUTE:
                return zone15Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_STEREO:
                return zone15Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE15_NAME:
                return zone15Name;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_INPUT:
                return zone16Input;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_POWER:
                return zone16Power;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_VOLUME:
                return zone16Volume;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_VOLUME_DB:
                return zone16VolumeDB;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_MUTE:
                return zone16Mute;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_STEREO:
                return zone16Stereo;
            case AudioauthorityBindingConstants.CHANNEL_ZONE16_NAME:
                return zone16Name;
            default:
                return null;
        }
    }

    public void setZone1Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone1Power) {
            this.zone1Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_POWER, this.zone1Power);
        }
    }

    public void setZone1Volume(BigDecimal volume) {

        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone1Volume)) {
            this.zone1Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_VOLUME, this.zone1Volume);
            // update the volume in dB too
            this.zone1VolumeDB = DecimalType
                    .valueOf(volume.subtract(AudioauthorityBindingConstants.DB_OFFSET).toString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_VOLUME_DB, this.zone1VolumeDB);
        }
    }

    public void setZone1Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone1Mute) {
            this.zone1Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_MUTE, this.zone1Mute);
        }
    }

    public void setZone1Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone1Stereo) {
            this.zone1Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_STEREO, this.zone1Stereo);
        }
    }

    public void setZone1Input(BigDecimal zone1Input) {
        DecimalType newVal = DecimalType.valueOf(zone1Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone1Input)) {
            this.zone1Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_INPUT, this.zone1Input);
        }
    }

    public void setZone1Name(String zone1Name) {
        StringType newVal = StringType.valueOf(zone1Name);
        if (!newVal.equals(this.zone1Name)) {
            this.zone1Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE1_NAME, this.zone1Name);
        }
    }

    public void setZone2Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone2Power) {
            this.zone2Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_POWER, this.zone2Power);
        }
    }

    public void setZone2Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone2Volume)) {
            this.zone2Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_VOLUME, this.zone2Volume);
            // update the volume in dB too
            this.zone2VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_VOLUME_DB, this.zone2VolumeDB);
        }
    }

    public void setZone2Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone2Mute) {
            this.zone2Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_MUTE, this.zone2Mute);
        }
    }

    public void setZone2Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone2Stereo) {
            this.zone2Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_STEREO, this.zone2Stereo);
        }
    }

    public void setZone2Input(BigDecimal zone2Input) {
        DecimalType newVal = DecimalType.valueOf(zone2Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone2Input)) {
            this.zone2Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_INPUT, this.zone2Input);
        }
    }

    public void setZone2Name(String zone2Name) {
        StringType newVal = StringType.valueOf(zone2Name);
        if (!newVal.equals(this.zone2Name)) {
            this.zone2Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE2_NAME, this.zone2Name);
        }
    }

    public void setZone3Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone3Power) {
            this.zone3Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_POWER, this.zone3Power);
        }
    }

    public void setZone3Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone3Volume)) {
            this.zone3Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_VOLUME, this.zone3Volume);
            // update the volume in dB too
            this.zone3VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_VOLUME_DB, this.zone3VolumeDB);
        }
    }

    public void setZone3Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone3Mute) {
            this.zone3Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_MUTE, this.zone3Mute);
        }
    }

    public void setZone3Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone3Stereo) {
            this.zone3Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_STEREO, this.zone3Stereo);
        }
    }

    public void setZone3Input(BigDecimal zone3Input) {
        DecimalType newVal = DecimalType.valueOf(zone3Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone3Input)) {
            this.zone3Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_INPUT, this.zone3Input);
        }
    }

    public void setZone3Name(String zone3Name) {
        StringType newVal = StringType.valueOf(zone3Name);
        if (!newVal.equals(this.zone3Name)) {
            this.zone3Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE3_NAME, this.zone3Name);
        }
    }

    public void setZone4Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone4Power) {
            this.zone4Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_POWER, this.zone4Power);
        }
    }

    public void setZone4Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone4Volume)) {
            this.zone4Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_VOLUME, this.zone4Volume);
            // update the volume in dB too
            this.zone4VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_VOLUME_DB, this.zone4VolumeDB);
        }
    }

    public void setZone4Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone4Mute) {
            this.zone4Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_MUTE, this.zone4Mute);
        }
    }

    public void setZone4Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone4Stereo) {
            this.zone4Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_STEREO, this.zone4Stereo);
        }
    }

    public void setZone4Input(BigDecimal zone4Input) {
        DecimalType newVal = DecimalType.valueOf(zone4Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone4Input)) {
            this.zone4Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_INPUT, this.zone4Input);
        }
    }

    public void setZone4Name(String zone4Name) {
        StringType newVal = StringType.valueOf(zone4Name);
        if (!newVal.equals(this.zone4Name)) {
            this.zone4Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE4_NAME, this.zone4Name);
        }
    }

    public void setZone5Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone5Power) {
            this.zone5Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_POWER, this.zone5Power);
        }
    }

    public void setZone5Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone5Volume)) {
            this.zone5Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_VOLUME, this.zone5Volume);
            // update the volume in dB too
            this.zone5VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_VOLUME_DB, this.zone5VolumeDB);
        }
    }

    public void setZone5Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone5Mute) {
            this.zone5Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_MUTE, this.zone5Mute);
        }
    }

    public void setZone5Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone5Stereo) {
            this.zone5Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_STEREO, this.zone5Stereo);
        }
    }

    public void setZone5Input(BigDecimal zone5Input) {
        DecimalType newVal = DecimalType.valueOf(zone5Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone5Input)) {
            this.zone5Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_INPUT, this.zone5Input);
        }
    }

    public void setZone5Name(String zone5Name) {
        StringType newVal = StringType.valueOf(zone5Name);
        if (!newVal.equals(this.zone5Name)) {
            this.zone5Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE5_NAME, this.zone5Name);
        }
    }

    public void setZone6Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone6Power) {
            this.zone6Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_POWER, this.zone6Power);
        }
    }

    public void setZone6Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone6Volume)) {
            this.zone6Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_VOLUME, this.zone6Volume);
            // update the volume in dB too
            this.zone6VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_VOLUME_DB, this.zone6VolumeDB);
        }
    }

    public void setZone6Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone6Mute) {
            this.zone6Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_MUTE, this.zone6Mute);
        }
    }

    public void setZone6Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone6Stereo) {
            this.zone6Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_STEREO, this.zone6Stereo);
        }
    }

    public void setZone6Input(BigDecimal zone6Input) {
        DecimalType newVal = DecimalType.valueOf(zone6Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone6Input)) {
            this.zone6Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_INPUT, this.zone6Input);
        }
    }

    public void setZone6Name(String zone6Name) {
        StringType newVal = StringType.valueOf(zone6Name);
        if (!newVal.equals(this.zone6Name)) {
            this.zone6Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE6_NAME, this.zone6Name);
        }
    }

    public void setZone7Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone7Power) {
            this.zone7Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_POWER, this.zone7Power);
        }
    }

    public void setZone7Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone7Volume)) {
            this.zone7Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_VOLUME, this.zone7Volume);
            // update the volume in dB too
            this.zone7VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_VOLUME_DB, this.zone7VolumeDB);
        }
    }

    public void setZone7Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone7Mute) {
            this.zone7Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_MUTE, this.zone7Mute);
        }
    }

    public void setZone7Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone7Stereo) {
            this.zone7Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_STEREO, this.zone7Stereo);
        }
    }

    public void setZone7Input(BigDecimal zone7Input) {
        DecimalType newVal = DecimalType.valueOf(zone7Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone7Input)) {
            this.zone7Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_INPUT, this.zone7Input);
        }
    }

    public void setZone7Name(String zone7Name) {
        StringType newVal = StringType.valueOf(zone7Name);
        if (!newVal.equals(this.zone7Name)) {
            this.zone7Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE7_NAME, this.zone7Name);
        }
    }

    public void setZone8Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone8Power) {
            this.zone8Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_POWER, this.zone8Power);
        }
    }

    public void setZone8Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone8Volume)) {
            this.zone8Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_VOLUME, this.zone8Volume);
            // update the volume in dB too
            this.zone8VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_VOLUME_DB, this.zone8VolumeDB);
        }
    }

    public void setZone8Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone8Mute) {
            this.zone8Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_MUTE, this.zone8Mute);
        }
    }

    public void setZone8Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone8Stereo) {
            this.zone8Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_STEREO, this.zone8Stereo);
        }
    }

    public void setZone8Input(BigDecimal zone8Input) {
        DecimalType newVal = DecimalType.valueOf(zone8Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone8Input)) {
            this.zone8Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_INPUT, this.zone8Input);
        }
    }

    public void setZone8Name(String zone8Name) {
        StringType newVal = StringType.valueOf(zone8Name);
        if (!newVal.equals(this.zone8Name)) {
            this.zone8Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE8_NAME, this.zone8Name);
        }
    }

    public void setZone9Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone9Power) {
            this.zone9Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_POWER, this.zone9Power);
        }
    }

    public void setZone9Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone9Volume)) {
            this.zone9Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_VOLUME, this.zone9Volume);
            // update the volume in dB too
            this.zone9VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_VOLUME_DB, this.zone9VolumeDB);
        }
    }

    public void setZone9Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone9Mute) {
            this.zone9Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_MUTE, this.zone9Mute);
        }
    }

    public void setZone9Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone9Stereo) {
            this.zone9Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_STEREO, this.zone9Stereo);
        }
    }

    public void setZone9Input(BigDecimal zone9Input) {
        DecimalType newVal = DecimalType.valueOf(zone9Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone9Input)) {
            this.zone9Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_INPUT, this.zone9Input);
        }
    }

    public void setZone9Name(String zone9Name) {
        StringType newVal = StringType.valueOf(zone9Name);
        if (!newVal.equals(this.zone9Name)) {
            this.zone9Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE9_NAME, this.zone9Name);
        }
    }

    public void setZone10Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone10Power) {
            this.zone10Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_POWER, this.zone10Power);
        }
    }

    public void setZone10Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone10Volume)) {
            this.zone10Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_VOLUME, this.zone10Volume);
            // update the volume in dB too
            this.zone10VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_VOLUME_DB, this.zone10VolumeDB);
        }
    }

    public void setZone10Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone10Mute) {
            this.zone10Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_MUTE, this.zone10Mute);
        }
    }

    public void setZone10Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone10Stereo) {
            this.zone10Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_STEREO, this.zone10Stereo);
        }
    }

    public void setZone10Input(BigDecimal zone10Input) {
        DecimalType newVal = DecimalType.valueOf(zone10Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone10Input)) {
            this.zone10Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_INPUT, this.zone10Input);
        }
    }

    public void setZone10Name(String zone10Name) {
        StringType newVal = StringType.valueOf(zone10Name);
        if (!newVal.equals(this.zone10Name)) {
            this.zone10Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE10_NAME, this.zone10Name);
        }
    }

    public void setZone11Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone11Power) {
            this.zone11Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_POWER, this.zone11Power);
        }
    }

    public void setZone11Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone11Volume)) {
            this.zone11Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_VOLUME, this.zone11Volume);
            // update the volume in dB too
            this.zone11VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_VOLUME_DB, this.zone11VolumeDB);
        }
    }

    public void setZone11Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone11Mute) {
            this.zone11Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_MUTE, this.zone11Mute);
        }
    }

    public void setZone11Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone11Stereo) {
            this.zone11Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_STEREO, this.zone11Stereo);
        }
    }

    public void setZone11Input(BigDecimal zone11Input) {
        DecimalType newVal = DecimalType.valueOf(zone11Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone11Input)) {
            this.zone11Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_INPUT, this.zone11Input);
        }
    }

    public void setZone11Name(String zone11Name) {
        StringType newVal = StringType.valueOf(zone11Name);
        if (!newVal.equals(this.zone11Name)) {
            this.zone11Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE11_NAME, this.zone11Name);
        }
    }

    public void setZone12Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone12Power) {
            this.zone12Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_POWER, this.zone12Power);
        }
    }

    public void setZone12Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone12Volume)) {
            this.zone12Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_VOLUME, this.zone12Volume);
            // update the volume in dB too
            this.zone12VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_VOLUME_DB, this.zone12VolumeDB);
        }
    }

    public void setZone12Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone12Mute) {
            this.zone12Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_MUTE, this.zone12Mute);
        }
    }

    public void setZone12Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone12Stereo) {
            this.zone12Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_STEREO, this.zone12Stereo);
        }
    }

    public void setZone12Input(BigDecimal zone12Input) {
        DecimalType newVal = DecimalType.valueOf(zone12Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone12Input)) {
            this.zone12Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_INPUT, this.zone12Input);
        }
    }

    public void setZone12Name(String zone12Name) {
        StringType newVal = StringType.valueOf(zone12Name);
        if (!newVal.equals(this.zone12Name)) {
            this.zone12Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE12_NAME, this.zone12Name);
        }
    }

    public void setZone13Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone13Power) {
            this.zone13Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_POWER, this.zone13Power);
        }
    }

    public void setZone13Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone12Volume)) {
            this.zone12Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_VOLUME, this.zone12Volume);
            // update the volume in dB too
            this.zone12VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_VOLUME_DB, this.zone12VolumeDB);
        }
    }

    public void setZone13Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone13Mute) {
            this.zone13Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_MUTE, this.zone13Mute);
        }
    }

    public void setZone13Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone13Stereo) {
            this.zone13Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_STEREO, this.zone13Stereo);
        }
    }

    public void setZone13Input(BigDecimal zone13Input) {
        DecimalType newVal = DecimalType.valueOf(zone13Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone13Input)) {
            this.zone13Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_INPUT, this.zone13Input);
        }
    }

    public void setZone13Name(String zone13Name) {
        StringType newVal = StringType.valueOf(zone13Name);
        if (!newVal.equals(this.zone13Name)) {
            this.zone13Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE13_NAME, this.zone13Name);
        }
    }

    public void setZone14Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone14Power) {
            this.zone14Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_POWER, this.zone14Power);
        }
    }

    public void setZone14Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone14Volume)) {
            this.zone14Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_VOLUME, this.zone14Volume);
            // update the volume in dB too
            this.zone14VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_VOLUME_DB, this.zone14VolumeDB);
        }
    }

    public void setZone14Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone14Mute) {
            this.zone14Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_MUTE, this.zone14Mute);
        }
    }

    public void setZone14Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone14Stereo) {
            this.zone14Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_STEREO, this.zone14Stereo);
        }
    }

    public void setZone14Input(BigDecimal zone14Input) {
        DecimalType newVal = DecimalType.valueOf(zone14Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone14Input)) {
            this.zone14Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_INPUT, this.zone14Input);
        }
    }

    public void setZone14Name(String zone14Name) {
        StringType newVal = StringType.valueOf(zone14Name);
        if (!newVal.equals(this.zone14Name)) {
            this.zone14Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE14_NAME, this.zone14Name);
        }
    }

    public void setZone15Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone15Power) {
            this.zone15Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_POWER, this.zone15Power);
        }
    }

    public void setZone15Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone15Volume)) {
            this.zone15Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_VOLUME, this.zone15Volume);
            // update the volume in dB too
            this.zone15VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_VOLUME_DB, this.zone15VolumeDB);
        }
    }

    public void setZone15Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone15Mute) {
            this.zone15Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_MUTE, this.zone15Mute);
        }
    }

    public void setZone15Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone15Stereo) {
            this.zone15Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_STEREO, this.zone15Stereo);
        }
    }

    public void setZone15Input(BigDecimal zone15Input) {
        DecimalType newVal = DecimalType.valueOf(zone15Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone15Input)) {
            this.zone15Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_INPUT, this.zone15Input);
        }
    }

    public void setZone15Name(String zone15Name) {
        StringType newVal = StringType.valueOf(zone15Name);
        if (!newVal.equals(this.zone15Name)) {
            this.zone15Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE15_NAME, this.zone15Name);
        }
    }

    public void setZone16Power(boolean power) {
        OnOffType newPower = power ? OnOffType.ON : OnOffType.OFF;
        if (newPower != this.zone16Power) {
            this.zone16Power = newPower;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_POWER, this.zone16Power);
        }
    }

    public void setZone16Volume(BigDecimal volume) {
        PercentType newVal = new PercentType(volume.add(AudioauthorityBindingConstants.DB_OFFSET)
                .divide(AudioauthorityBindingConstants.DB_OFFSET, 1, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)));
        if (!newVal.equals(this.zone16Volume)) {
            this.zone16Volume = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_VOLUME, this.zone16Volume);
            // update the volume in dB too
            this.zone16VolumeDB = DecimalType.valueOf(volume.toPlainString());
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_VOLUME_DB, this.zone16VolumeDB);
        }
    }

    public void setZone16Mute(boolean mute) {
        OnOffType newMute = mute ? OnOffType.ON : OnOffType.OFF;
        if (newMute != this.zone16Mute) {
            this.zone16Mute = newMute;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_MUTE, this.zone16Mute);
        }
    }

    public void setZone16Stereo(boolean stereo) {
        OnOffType newStereo = stereo ? OnOffType.ON : OnOffType.OFF;
        if (newStereo != this.zone16Stereo) {
            this.zone16Stereo = newStereo;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_STEREO, this.zone16Stereo);
        }
    }

    public void setZone16Input(BigDecimal zone16Input) {
        DecimalType newVal = DecimalType.valueOf(zone16Input.toString());
        int newInt = newVal.intValue();
        if (newInt > 20) {
            switch (newInt) {
                case 21:
                    newVal = new DecimalType(1);
                    break;
                case 34:
                    newVal = new DecimalType(3);
                    break;
                case 56:
                    newVal = new DecimalType(5);
                    break;
                case 78:
                    newVal = new DecimalType(7);
                    break;
                case 910:
                    newVal = new DecimalType(9);
                    break;
                case 1112:
                    newVal = new DecimalType(11);
                    break;
                case 1314:
                    newVal = new DecimalType(13);
                    break;
                case 1516:
                    newVal = new DecimalType(15);
                    break;
            }

        }

        if (!newVal.equals(this.zone16Input)) {
            this.zone16Input = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_INPUT, this.zone16Input);
        }
    }

    public void setZone16Name(String zone16Name) {
        StringType newVal = StringType.valueOf(zone16Name);
        if (!newVal.equals(this.zone16Name)) {
            this.zone16Name = newVal;
            handler.stateChanged(AudioauthorityBindingConstants.CHANNEL_ZONE16_NAME, this.zone16Name);
        }
    }
}
