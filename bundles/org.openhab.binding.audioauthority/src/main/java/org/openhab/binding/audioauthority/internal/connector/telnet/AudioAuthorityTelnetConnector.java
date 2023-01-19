package org.openhab.binding.audioauthority.internal.connector.telnet;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

import org.openhab.binding.audioauthority.internal.AudioAuthorityConfiguration;
import org.openhab.binding.audioauthority.internal.AudioAuthorityState;
import org.openhab.binding.audioauthority.internal.connector.AudioAuthorityConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioAuthorityTelnetConnector extends AudioAuthorityConnector implements AudioAuthorityTelnetListener {

    private final Logger logger = LoggerFactory.getLogger(AudioAuthorityTelnetConnector.class);

    protected boolean disposing = false;

    // All regular commands. Example: PW, SICD, SITV, Z2MU
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^([A-Z0-9]{2})(.+)$");

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
        String cmd = "[" + command + "]";
        logger.debug("Sending command '{}'", cmd);
        telnetClientThread.sendCommand(cmd);
    }

    @Override
    public void receivedLine(String line) {
        // TODO Auto-generated method stub

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

            internalSendCommand("XQ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.trace("requestStateOverTelnet() - Interrupted while requesting state.");
                Thread.currentThread().interrupt();
            }

        });
    }

}
