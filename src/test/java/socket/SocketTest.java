package socket;

import org.junit.jupiter.api.Test;
import socket.model.Group;
import socket.model.User;
import socket.network.ChatServer;
import socket.network.Client;
import socket.network.ClientHandler;
import socket.network.commands.FullGroupResponse;

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
            Socket socket = new Socket();
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.stop();
            clientHandler.run();
            ServerController serverController = new ServerController(clientHandler);
            Client client = new Client("", 8234);
            ClientController cc = new ClientController(client);
            cc.createUser("username");
            cc.chooseGroup(0);
            Client client2 = new Client("", 8234);
            ClientController cc2 = new ClientController(client);
            cc2.createUser("username2");
            cc2.createGroup();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
