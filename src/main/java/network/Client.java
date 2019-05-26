package network;

import network.socket.commands.Request;
import network.socket.commands.Response;

import java.rmi.Remote;

public interface Client extends Remote {
    void request(Request request);
    Response nextResponse();
}
