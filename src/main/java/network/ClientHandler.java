package network;

import model.room.ModelObserver;
import network.commands.Request;
import network.commands.Response;

public interface ClientHandler extends ModelObserver {
    //Implemented by RMI and Socket to be transparent with ClientController
}
