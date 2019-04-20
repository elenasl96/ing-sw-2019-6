package network.socket.launch;


import network.socket.ClientController;

import java.io.IOException;

public class LaunchClient {
    public static void main(String[] args) throws IOException{
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

        Client client = new Client(host, port);
        client.init();
        ClientController controller = new ClientController(client);
        controller.run();

        client.close();
    }
}
