package network;

import network.commands.Request;
import network.commands.Response;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void request(Request request) throws RemoteException;
    Response nextResponse() throws RemoteException;

    void init()  throws IOException;
}
