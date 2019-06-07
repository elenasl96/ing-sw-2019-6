package network.commands;

import java.io.Serializable;

/**
 * Responses are sent by Server to Client and handled by the ClientController
 * @see controller.ClientController
 * @see ResponseHandler
 * @see controller.ServerController
 */
public interface Response extends Serializable {
    void handle(ResponseHandler handler);
}
