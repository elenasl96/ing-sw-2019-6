package socket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import socket.network.ChatServer;
import socket.network.Client;

import java.io.*;
import java.net.Socket;


class MockTest {
    //@Test Not working
    void ClientServerTest() {
        boolean done = false;
        try{
            ChatServer server = new ChatServer(8234);
            while(!done) {
                server.run();
                Client client = new Client("", 8234);
                client.init();
                ClientController controller = new ClientController(client);
                controller.run();
                ByteArrayInputStream symName = new ByteArrayInputStream("username".getBytes());
                System.setIn(symName);
                ByteArrayInputStream yes = new ByteArrayInputStream("yes".getBytes());
                System.setIn(yes);
                ByteArrayInputStream quit = new ByteArrayInputStream(":q".getBytes());
                System.setIn(quit);
                client.close();
                done = true;
            }

        }catch(IOException ioe){
            ioe.getMessage();
        }
    }
}
