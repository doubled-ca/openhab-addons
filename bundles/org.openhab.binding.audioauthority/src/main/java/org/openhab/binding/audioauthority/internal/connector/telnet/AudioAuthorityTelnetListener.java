package org.openhab.binding.audioauthority.internal.connector.telnet;

/*
 * Listener interface used to notify the {@link AudioAuthorityConnector}
 * about received messages over Telnet
 */
public interface AudioAuthorityTelnetListener {
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
