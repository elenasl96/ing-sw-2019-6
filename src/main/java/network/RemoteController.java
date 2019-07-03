package network;

import network.commands.Request;
import network.commands.Response;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Handles the Request/Response pattern: either Socket or RMI connection send the request and ask for the
 * nextResponse
 */
public interface RemoteController extends Remote {
    /**
     * @param request   the Request object represents the
     * @throws RemoteException if an error in RMI connection occurs
     */
    void request(Request request) throws RemoteException;

    /**
     * @return  the result of the response handling
     * @throws RemoteException if an error in RMI connection occurs
     */
    Response nextResponse() throws IOException;

    /**
     *
     * @throws RemoteException if an error in RMI connection occurs
     */
    void received()throws RemoteException;

    /**
     * initiates the connection
     * @throws IOException if an error in Socket connection occurs
     */
    void init()  throws IOException;

    /**
     * bounds with the server
     * @throws RemoteException if an error in RMI connection occurs
     */
    void bound() throws RemoteException;
}
