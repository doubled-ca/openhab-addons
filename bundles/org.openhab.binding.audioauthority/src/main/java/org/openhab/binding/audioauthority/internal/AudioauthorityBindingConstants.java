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

import java.math.BigDecimal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link AudioauthorityBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Dennis Drapeau - Initial contribution
 */
@NonNullByDefault
public class AudioauthorityBindingConstants {

    private static final String BINDING_ID = "audioauthority";

    // List of all Thing Type UIDs
    public static final ThingTypeUID SONAFLEX_THING_TYPE = new ThingTypeUID(BINDING_ID, "sonaflex");

    // List of thing parameters names
    public static final String PARAMETER_PROTOCOL = "protocol";
    public static final String PARAMETER_HOST = "ipAddress";
    public static final String PARAMETER_TCP_PORT = "tcpPort";
    public static final String PARAMETER_SERIAL_PORT = "serialPort";
    public static final Object PARAMETER_UNIT_NUMBER = "unit";
    public static final Object PARAMETER_ACTIVE_ZONES = "activeZones";

    public static final String PARAMETER_IP_PROTOCOL_NAME = "IP";
    public static final String PARAMETER_SERIAL_PROTOCOL_NAME = "serial";

    // List of all Channel ids
    public static final String CHANNEL_ZONE1_POWER = "zone1#power";
    public static final String CHANNEL_ZONE1_VOLUME = "zone1#volume";
    public static final String CHANNEL_ZONE1_VOLUME_DB = "zone1#volumeDB";
    public static final String CHANNEL_ZONE1_MUTE = "zone1#mute";
    public static final String CHANNEL_ZONE1_INPUT = "zone1#input";
    public static final String CHANNEL_ZONE1_STEREO = "zone1#stereo";
    public static final String CHANNEL_ZONE1_NAME = "zone1#name";

    public static final String CHANNEL_ZONE2_POWER = "zone2#power";
    public static final String CHANNEL_ZONE2_VOLUME = "zone2#volume";
    public static final String CHANNEL_ZONE2_VOLUME_DB = "zone2#volumeDB";
    public static final String CHANNEL_ZONE2_MUTE = "zone2#mute";
    public static final String CHANNEL_ZONE2_INPUT = "zone2#input";
    public static final String CHANNEL_ZONE2_STEREO = "zone2#stereo";
    public static final String CHANNEL_ZONE2_NAME = "zone2#name";

    public static final String CHANNEL_ZONE3_POWER = "zone3#power";
    public static final String CHANNEL_ZONE3_VOLUME = "zone3#volume";
    public static final String CHANNEL_ZONE3_VOLUME_DB = "zone3#volumeDB";
    public static final String CHANNEL_ZONE3_MUTE = "zone3#mute";
    public static final String CHANNEL_ZONE3_INPUT = "zone3#input";
    public static final String CHANNEL_ZONE3_STEREO = "zone3#stereo";
    public static final String CHANNEL_ZONE3_NAME = "zone3#name";

    public static final String CHANNEL_ZONE4_POWER = "zone4#power";
    public static final String CHANNEL_ZONE4_VOLUME = "zone4#volume";
    public static final String CHANNEL_ZONE4_VOLUME_DB = "zone4#volumeDB";
    public static final String CHANNEL_ZONE4_MUTE = "zone4#mute";
    public static final String CHANNEL_ZONE4_INPUT = "zone4#input";
    public static final String CHANNEL_ZONE4_STEREO = "zone4#stereo";
    public static final String CHANNEL_ZONE4_NAME = "zone4#name";

    public static final String CHANNEL_ZONE5_POWER = "zone5#power";
    public static final String CHANNEL_ZONE5_VOLUME = "zone5#volume";
    public static final String CHANNEL_ZONE5_VOLUME_DB = "zone5#volumeDB";
    public static final String CHANNEL_ZONE5_MUTE = "zone5#mute";
    public static final String CHANNEL_ZONE5_INPUT = "zone5#input";
    public static final String CHANNEL_ZONE5_STEREO = "zone5#stereo";
    public static final String CHANNEL_ZONE5_NAME = "zone5#name";

    public static final String CHANNEL_ZONE6_POWER = "zone6#power";
    public static final String CHANNEL_ZONE6_VOLUME = "zone6#volume";
    public static final String CHANNEL_ZONE6_VOLUME_DB = "zone6#volumeDB";
    public static final String CHANNEL_ZONE6_MUTE = "zone6#mute";
    public static final String CHANNEL_ZONE6_INPUT = "zone6#input";
    public static final String CHANNEL_ZONE6_STEREO = "zone6#stereo";
    public static final String CHANNEL_ZONE6_NAME = "zone6#name";

    public static final String CHANNEL_ZONE7_POWER = "zone7#power";
    public static final String CHANNEL_ZONE7_VOLUME = "zone7#volume";
    public static final String CHANNEL_ZONE7_VOLUME_DB = "zone7#volumeDB";
    public static final String CHANNEL_ZONE7_MUTE = "zone7#mute";
    public static final String CHANNEL_ZONE7_INPUT = "zone7#input";
    public static final String CHANNEL_ZONE7_STEREO = "zone7#stereo";
    public static final String CHANNEL_ZONE7_NAME = "zone7#name";

    public static final String CHANNEL_ZONE8_POWER = "zone8#power";
    public static final String CHANNEL_ZONE8_VOLUME = "zone8#volume";
    public static final String CHANNEL_ZONE8_VOLUME_DB = "zone8#volumeDB";
    public static final String CHANNEL_ZONE8_MUTE = "zone8#mute";
    public static final String CHANNEL_ZONE8_INPUT = "zone8#input";
    public static final String CHANNEL_ZONE8_STEREO = "zone8#stereo";
    public static final String CHANNEL_ZONE8_NAME = "zone8#name";

    public static final String CHANNEL_ZONE9_POWER = "zone9#power";
    public static final String CHANNEL_ZONE9_VOLUME = "zone9#volume";
    public static final String CHANNEL_ZONE9_VOLUME_DB = "zone9#volumeDB";
    public static final String CHANNEL_ZONE9_MUTE = "zone9#mute";
    public static final String CHANNEL_ZONE9_INPUT = "zone9#input";
    public static final String CHANNEL_ZONE9_STEREO = "zone9#stereo";
    public static final String CHANNEL_ZONE9_NAME = "zone9#name";

    public static final String CHANNEL_ZONE10_POWER = "zone10#power";
    public static final String CHANNEL_ZONE10_VOLUME = "zone10#volume";
    public static final String CHANNEL_ZONE10_VOLUME_DB = "zone10#volumeDB";
    public static final String CHANNEL_ZONE10_MUTE = "zone10#mute";
    public static final String CHANNEL_ZONE10_INPUT = "zone10#input";
    public static final String CHANNEL_ZONE10_STEREO = "zone10#stereo";
    public static final String CHANNEL_ZONE10_NAME = "zone10#name";

    public static final String CHANNEL_ZONE11_POWER = "zone11#power";
    public static final String CHANNEL_ZONE11_VOLUME = "zone11#volume";
    public static final String CHANNEL_ZONE11_VOLUME_DB = "zone11#volumeDB";
    public static final String CHANNEL_ZONE11_MUTE = "zone11#mute";
    public static final String CHANNEL_ZONE11_INPUT = "zone11#input";
    public static final String CHANNEL_ZONE11_STEREO = "zone11#stereo";
    public static final String CHANNEL_ZONE11_NAME = "zone11#name";

    public static final String CHANNEL_ZONE12_POWER = "zone12#power";
    public static final String CHANNEL_ZONE12_VOLUME = "zone12#volume";
    public static final String CHANNEL_ZONE12_VOLUME_DB = "zone12#volumeDB";
    public static final String CHANNEL_ZONE12_MUTE = "zone12#mute";
    public static final String CHANNEL_ZONE12_INPUT = "zone12#input";
    public static final String CHANNEL_ZONE12_STEREO = "zone12#stereo";
    public static final String CHANNEL_ZONE12_NAME = "zone12#name";

    public static final String CHANNEL_ZONE13_POWER = "zone13#power";
    public static final String CHANNEL_ZONE13_VOLUME = "zone13#volume";
    public static final String CHANNEL_ZONE13_VOLUME_DB = "zone13#volumeDB";
    public static final String CHANNEL_ZONE13_MUTE = "zone13#mute";
    public static final String CHANNEL_ZONE13_INPUT = "zone13#input";
    public static final String CHANNEL_ZONE13_STEREO = "zone13#stereo";
    public static final String CHANNEL_ZONE13_NAME = "zone13#name";

    public static final String CHANNEL_ZONE14_POWER = "zone14#power";
    public static final String CHANNEL_ZONE14_VOLUME = "zone14#volume";
    public static final String CHANNEL_ZONE14_VOLUME_DB = "zone14#volumeDB";
    public static final String CHANNEL_ZONE14_MUTE = "zone14#mute";
    public static final String CHANNEL_ZONE14_INPUT = "zone14#input";
    public static final String CHANNEL_ZONE14_STEREO = "zone14#stereo";
    public static final String CHANNEL_ZONE14_NAME = "zone14#name";

    public static final String CHANNEL_ZONE15_POWER = "zone15#power";
    public static final String CHANNEL_ZONE15_VOLUME = "zone15#volume";
    public static final String CHANNEL_ZONE15_VOLUME_DB = "zone15#volumeDB";
    public static final String CHANNEL_ZONE15_MUTE = "zone15#mute";
    public static final String CHANNEL_ZONE15_INPUT = "zone15#input";
    public static final String CHANNEL_ZONE15_STEREO = "zone15#stereo";
    public static final String CHANNEL_ZONE15_NAME = "zone15#name";

    public static final String CHANNEL_ZONE16_POWER = "zone16#power";
    public static final String CHANNEL_ZONE16_VOLUME = "zone16#volume";
    public static final String CHANNEL_ZONE16_VOLUME_DB = "zone16#volumeDB";
    public static final String CHANNEL_ZONE16_MUTE = "zone16#mute";
    public static final String CHANNEL_ZONE16_INPUT = "zone16#input";
    public static final String CHANNEL_ZONE16_STEREO = "zone16#stereo";
    public static final String CHANNEL_ZONE16_NAME = "zone16#name";

    public static final String ZONE_PATTERN_STRING = "^zone([1-9]|1[0-6])";
    public static final String POWER_PATTERN_STRING = "power$";
    public static final String VOLUME_PATTERN_STRING = "volume$";
    public static final String VOLUMEDB_PATTERN_STRING = "volumeDB$";
    public static final String MUTE_PATTERN_STRING = "mute$";
    public static final String SWITCH_INPUT_PATTERN_STRING = "input$";
    public static final String STEREO_PATTERN_STRING = "stereo$";
    public static final String NAME_PATTERN_STRING = "name$";

    public static final BigDecimal DB_OFFSET = new BigDecimal(80);
}
