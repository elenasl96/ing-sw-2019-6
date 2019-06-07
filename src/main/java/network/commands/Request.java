package network.commands;

import java.io.Serializable;

/**
 * Requests are sent by Client to Server and handled by the ServerController
 * @see controller.ServerController
 * @see controller.ClientController
 * @see RequestHandler
 */
public interface Request extends Serializable {
    Response handle(RequestHandler handler);
}
