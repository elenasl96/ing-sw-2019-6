package network.socket.launch;
import network.exceptions.WrongDeserializationException;
import network.socket.commands.Request;
import network.socket.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() throws IOException {
        connection = new Socket(host, port);
        out = new ObjectOutputStream(connection.getOutputStream());
        in = new ObjectInputStream(connection.getInputStream());
    }

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
     *
     * @return the next Response or null if some IOException happens
     */
    public Response nextResponse() {
        try {
            return ((Response) in.readObject());
        } catch (IOException e) {
            System.err.println("Exception on network.socket: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new WrongDeserializationException("Wrong deserialization: " + e.getMessage());
        }
        return null;
    }

    public void request(Request request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            System.err.println("Exception on network.socket: " + e.getMessage());
        }
    }
}
