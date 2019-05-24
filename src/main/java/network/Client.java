package network;

import model.room.ModelObserver;
import network.socket.commands.Request;
import network.socket.commands.Response;

public interface Client {
    void request(Request request);
    Response nextResponse();
}