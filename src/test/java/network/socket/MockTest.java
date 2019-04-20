package network.socket;
import org.junit.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import network.socket.launch.ChatServer;
import network.socket.launch.Client;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;


class MockTest {

    @Mock
    Client mockClient;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Disabled
    public void MockitoTest() {
        boolean done = false;
        try {
            Socket socket = new Socket("", 8234);
            ChatServer server = new ChatServer(8234);
            //mockClient.setConnection(socket);
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.stop();
            clientHandler.run();
            ClientController cc = new ClientController(mockClient);
            cc.createUser("username");
           // cc.chooseGroup(0);
            ClientController cc2 = new ClientController(mockClient);
            cc2.createUser("username2");
           // cc2.createGroup(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
