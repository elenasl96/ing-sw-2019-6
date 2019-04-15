package socket;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import socket.network.ChatServer;
import socket.network.Client;
import socket.network.ClientHandler;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class MockTest {

    @Mock
    Client mockClient;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void MockitoTest() {
        boolean done = false;
        try {
            Socket socket = new Socket("", 8234);
            ChatServer server = new ChatServer(8234);
            mockClient.setConnection(socket);
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.stop();
            clientHandler.run();
            ClientController cc = new ClientController(mockClient);
            cc.createUser("username");
           // cc.chooseGroup(0);
            ClientController cc2 = new ClientController(mockClient);
            cc2.createUser("username2");
            cc2.createGroup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
