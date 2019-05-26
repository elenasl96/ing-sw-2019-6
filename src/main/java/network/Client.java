package network;

import network.socket.commands.Request;
import network.socket.commands.Response;

import java.rmi.Remote;

public interface Client {
    void request(Request request);
    Response nextResponse();
}
