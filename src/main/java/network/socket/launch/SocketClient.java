package network.socket.launch;
import network.Client;
import network.exceptions.WrongDeserializationException;
import controller.ClientController;
import network.socket.commands.Request;
import network.socket.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements Client{
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException{
        //else -> Socket
        if (args.length == 0) {
            System.err.println("Provide host:port please");
            return;
        }

        String[] tokens = args[0].split(":");

        if (tokens.length < 2) {
            throw new IllegalArgumentException("Bad formatting: " + args[0]);
        }

        String host = tokens[0];
        System.out.println(host);
        int port = Integer.parseInt(tokens[1]);

        SocketClient socketClient = new SocketClient(host, port);
        socketClient.init();
        ClientController controller = new ClientController(socketClient);
        controller.run();

        socketClient.close();
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
    @Override
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

    @Override
    public void request(Request request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            System.err.println("Exception on network.socket: " + e.getMessage());
        }
    }
}
