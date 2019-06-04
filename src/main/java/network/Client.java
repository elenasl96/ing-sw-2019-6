package network;

import network.commands.Request;
import network.commands.Response;

public interface Client {
    void request(Request request);
    Response nextResponse();
}
