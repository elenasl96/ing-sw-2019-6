package network.socket;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import network.socket.launch.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Execution(ExecutionMode.CONCURRENT)
public class SocketTest {
    private static final int PORT = 8887;

    private OutputStream serverOut;
    private InputStream serverIn;

    private Semaphore lock = new Semaphore(0);

    private ChatServer server;
    private Socket client;

    @Test
    public void testClientServer() throws IOException, InterruptedException {
        server = new ChatServer(PORT);
        //listen(server.getServerSocket());

        client = new Socket("localhost", PORT);

        //Client client2 = new Client(client);
        //client2.testInit2();
        //OutputStream clientOut = client2.getClientOut();
        //InputStream clientIn = client2.getClientIn();

        System.out.println("Client running");
        lock.acquire();
        System.out.println("Acquired lock");
        //ClientController clientController = new ClientController(client2);
        //clientController.run();
    }

    @BeforeEach
    void test(){
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
    }
    @Test
    void test1(){
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
    }
    @Test
    void test3(){
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
    }

    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = new ObjectOutputStream(socket.getOutputStream());
                serverIn = new ObjectInputStream(socket.getInputStream());

                lock.release();
                System.out.println("Released lock");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Disabled
    void closeAll(){
        try {
            client.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
