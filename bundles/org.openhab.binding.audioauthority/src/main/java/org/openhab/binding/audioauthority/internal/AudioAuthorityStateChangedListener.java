package org.openhab.binding.audioauthority.internal;

import org.openhab.core.types.State;

public interface AudioAuthorityStateChangedListener {
    /**
     * Update was received.
     *
     * @param channelID the channel for which its state changed
     * @param state the new state of the channel
     */
    void stateChanged(String channelID, State state);

    /**
     * A connection error occurred
     *
     * @param errorMessage the error message
     */
    void connectionError(String errorMessage);
}
