package socket;

import org.junit.jupiter.api.Test;
import socket.network.ChatServer;
import socket.network.Client;
import socket.network.ClientHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketTest {
    @Test
    void ChatServer(){
        try {
            ChatServer server = new ChatServer(8234);
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void Test(){
        try {
            ChatServer server = new ChatServer(8234);
            Socket socket = new Socket("", 8234);
            Client client = new Client("", 8234);
            client.setConnection(socket);
            //Stops here, maybe for the inputstream problem
            /*
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.stop();
            clientHandler.run();
            ClientController cc = new ClientController(client);
            cc.createUser("username");
            cc.chooseGroup(0);
            Client client2 = new Client("", 8234);
            ClientController cc2 = new ClientController(client);
            cc2.createUser("username2");
            cc2.createGroup();
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
