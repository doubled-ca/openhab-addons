package org.openhab.binding.audioauthority.internal.connector;

import java.util.concurrent.ScheduledExecutorService;

import org.openhab.binding.audioauthority.internal.AudioAuthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioAuthorityState;
import org.openhab.binding.audioauthority.internal.connector.telnet.AudioAuthorityTelnetConnector;

public class AudioAuthorityConnectorFactory {

    public AudioAuthorityConnector getConnector(AudioAuthorityConfiguration config, AudioAuthorityState state,
            ScheduledExecutorService scheduler, String thingUID) {

        /*
         * only telnet supported currently
         */
        return new AudioAuthorityTelnetConnector(config, state, scheduler, thingUID);

    }
}