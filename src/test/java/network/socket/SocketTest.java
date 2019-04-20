package network.socket;

import model.room.Group;
import model.room.User;
import network.socket.launch.Client;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocketTest {
    //NOTE: a new Server needs to be running every time

    private static Client client1;
    private static Client client2;
    private static Client client3;
    private static Client client4;
    private static Client client5;

    private static ClientController clientController1;
    private static ClientController clientController2;
    private static ClientController clientController3;
    private static ClientController clientController4;
    private static ClientController clientController5;

    @BeforeAll
    static void Constructor(){
        //Client 1
        client1 = new Client("", 8234);
        try {
            client1.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController1 = new ClientController(client1);

        //Client 2
        client2 = new Client("", 8234);
        try {
            client2.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController2 = new ClientController(client2);

        //Client 3
        client3 = new Client("", 8234);
        try {
            client3.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController3 = new ClientController(client3);

        //Client 4
        client4 = new Client("", 8234);
        try {
            client4.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController4 = new ClientController(client4);

        //Client 5
        client5 = new Client("", 8234);
        try {
            client5.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController5 = new ClientController(client5);
    }

    @Test
    @Order(1)
    void createUserTest(){
        clientController1.createUser("user1");
        clientController2.createUser("user2");
        clientController3.createUser("user3");
        clientController4.createUser("user4");
        clientController5.createUser("user5");
    }

    @Test
    @Order(2)
    void chooseGroupTest(){
        clientController1.chooseGroup(0);
        clientController2.chooseGroup(0);
        clientController3.chooseGroup(0);
        clientController4.chooseGroup(0);
        clientController5.chooseGroup(0);
    }

    @AfterAll
    static void CloseAllTest(){
        try {
            client1.close();
            client2.close();
            client3.close();
            client4.close();
            client5.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Setup to easily check what's displayed
    //Check via assertEquals("ExpectedOutput", outContent.toString());
    //          assertEquals("ExpectedOutput", errContent.toString());
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
