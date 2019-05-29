package network.rmi;

import network.socket.commands.Request;
import network.socket.commands.Response;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void request(Request request) throws RemoteException;
    Response nextResponse() throws RemoteException;

    void init()  throws IOException;
}
