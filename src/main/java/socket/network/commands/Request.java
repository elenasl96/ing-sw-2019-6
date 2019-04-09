package socket.network.commands;

import java.io.Serializable;

public interface Request extends Serializable {
    Response handle(RequestHandler handler);
}
