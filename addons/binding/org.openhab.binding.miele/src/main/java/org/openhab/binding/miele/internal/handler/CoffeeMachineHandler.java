/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.miele.internal.handler;

import static org.openhab.binding.miele.internal.MieleBindingConstants.APPLIANCE_ID;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;

/**
 * The {@link CoffeeMachineHandler} is responsible for handling commands,
 * which are sent to one of the channels
 *
 * @author Stephan Esch - Initial contribution
 */
public class CoffeeMachineHandler extends MieleApplianceHandler<CoffeeMachineChannelSelector> {

    private final Logger logger = LoggerFactory.getLogger(CoffeeMachineHandler.class);

    public CoffeeMachineHandler(Thing thing) {
        super(thing, CoffeeMachineChannelSelector.class, "CoffeeSystem");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        super.handleCommand(channelUID, command);

        String channelID = channelUID.getId();
        String uid = (String) getThing().getConfiguration().getProperties().get(APPLIANCE_ID);

        CoffeeMachineChannelSelector selector = (CoffeeMachineChannelSelector) getValueSelectorFromChannelID(channelID);
        JsonElement result = null;

        try {
            if (selector != null) {
                switch (selector) {
                    case SWITCH: {
                        if (command.equals(OnOffType.ON)) {
                            result = bridgeHandler.invokeOperation(uid, modelID, "switchOn");
                        } else if (command.equals(OnOffType.OFF)) {
                            result = bridgeHandler.invokeOperation(uid, modelID, "switchOff");
                        }
                        break;
                    }
                    default: {
                        if (!(command instanceof RefreshType)) {
                            logger.debug("{} is a read-only channel that does not accept commands",
                                    selector.getChannelID());
                        }
                    }
                }
            }
            // process result
            if (result != null) {
                logger.debug("Result of operation is {}", result.getAsString());
            }
        } catch (IllegalArgumentException e) {
            logger.warn(
                    "An error occurred while trying to set the read-only variable associated with channel '{}' to '{}'",
                    channelID, command.toString());
        }
    }

}
