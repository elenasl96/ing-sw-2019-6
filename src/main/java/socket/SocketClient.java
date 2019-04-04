package socket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient implements Closeable{
    /**
     * The host name
     */
    private final String host;

    /**
     * The port number
     */
    private final int port;

    /**
     * The Socket used for the connection
     */
    private Socket connection;

    /**
     * The communication streamers for input and output
     */
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructor
     * @param host  the host name
     * @param port  the port
     */
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Closes the connection
     * @throws IOException  if something goes wrong in the connection
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        connection.close();
    }

    /**
     *  Creates the connection
     * @throws IOException if connection problems
     */
    public void init() throws IOException {
        connection = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new PrintWriter(connection.getOutputStream(), true);
    }

    /**
     *  Receives the message
     * @return  the BufferedReader input
     * @throws IOException if connection problems
     */
    public String receive() throws IOException {
        return in.readLine();
    }

    /**
     * prints on server screen the message
     * @param message the message to print
     */
    public void send(String message) {
        out.println(message);
    }
    /**
     * main method
     * @param args  HAS TO BE THE HOST NAME
     * @throws IOException  from connection errors
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide host:port please");
            return;
        }
        String[] tokens = args[0].split(":");
        if (tokens.length < 2) {
            throw new IllegalArgumentException("Bad formatting: " + args[0]);
        }
        String host = tokens[0];
        int port = Integer.parseInt(tokens[1]);
        SocketClient socketClient = new SocketClient(host, port);
        Scanner fromKeyboard = new Scanner(System.in);

        try {
            socketClient.init();

            String received = null;

            do {
                System.out.println(">>> Provide command:");
                String toSend = fromKeyboard.nextLine();
                socketClient.send(toSend);
                received = socketClient.receive();
                System.out.println(received);
            } while (received != null);
        } finally {
            socketClient.close();
        }
    }
}
