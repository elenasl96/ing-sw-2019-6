package network.socket;
import network.exceptions.WrongDeserializationException;
import controller.ClientController;
import network.RemoteController;
import view.ViewClient;
import network.commands.Request;
import network.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * CLIENT SIDE
 * Socket Connection
 */
public class SocketClient implements RemoteController {
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Initiates the connection
     * @throws IOException if something goes wrong in the Socket connection
     */
    @Override
    public void init() throws IOException {
        connection = new Socket(host, port);
        out = new ObjectOutputStream(connection.getOutputStream());
        in = new ObjectInputStream(connection.getInputStream());
    }

    @Override
    public void bound() throws RemoteException {
        //RMI method
    }

    /**
     * Sets this to the poisonPill socket to shutdown the server locally
     * @throws IOException if something goes wrong in the Socket connection
     */
    public void setPoisonous() throws IOException {
        connection = new Socket(host, port,null, 6000);
        System.out.println(connection.getInetAddress());
        out = new ObjectOutputStream(connection.getOutputStream());
        out.writeBoolean(true);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        connection.close();
    }

    /**
     * @return the next Response or null if some IOException happens
     */
    @Override
    public synchronized Response nextResponse() throws IOException {
        try {
            return ((Response) in.readObject());
        } catch (ClassNotFoundException e) {
            throw new WrongDeserializationException("Wrong deserialization: " + e.getMessage());
        }
    }

    /**
     * @see RemoteController#request(Request)
     */
    @Override
    public void request(Request request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            System.err.println("Exception on network.socket: " + e.getMessage());
        }
    }

    @Override
    public void received(){
        //RMI method
    }
}
