package network.socket;

import model.room.Group;
import model.room.Message;
import model.room.User;
import network.socket.launch.Client;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocketTest {
    //NOTE: a new Server needs to be running every time

    private static Client client1;
    private static Client client2;
    private static Client client3;
    private static Client client4;
    private static Client client5;
    private static Client client6;
    private static Client client7;

    private static ClientController clientController1;
    private static ClientController clientController2;
    private static ClientController clientController3;
    private static ClientController clientController4;
    private static ClientController clientController5;
    private static ClientController clientController6;
    private static ClientController clientController7;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;

    private Group group0;
    private Group group1;

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

        //Client 6
        client6 = new Client("", 8234);
        try {
            client6.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController6 = new ClientController(client6);

        //Client 7
        client7 = new Client("", 8234);
        try {
            client7.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController7 = new ClientController(client7);
    }

    @Test
    @Order(1)
    void createUserTest(){
        user1 = clientController1.createUser("user1");
        assertEquals("user1", user1.getUsername());
        user2 = clientController2.createUser("user1");
            //Not performed because the username is taken
        user2 = clientController2.createUser("user2");
        assertEquals("user2", user2.getUsername());
        user3 = clientController3.createUser("user3");
        user4 = clientController4.createUser("user4");
        user5 = clientController5.createUser("user5");
        clientController6.createUser("Server");
            //Not performed because name contains Server
        user6 = clientController6.createUser("user6");
        clientController7.createUser("user7");
    }

    @Test
    @Order(2)
    void chooseGroupTest(){
        group0 = clientController1.chooseGroup(0);
        group1 = clientController2.chooseGroup(0);
        assertEquals(group0, group1);
        group1 = null;
        clientController2.startReceiverThread();
        clientController3.chooseGroup(0);
        clientController3.startReceiverThread();
        //Timer Starting
        clientController4.chooseGroup(0);
        clientController4.startReceiverThread();
        clientController5.chooseGroup(0);
        clientController5.startReceiverThread();
        clientController7.chooseGroup(0);
        //Not performed because group is full
    }

    @Test
    @Order(3)
    void createGroupTest(){
        int newGroupID = clientController6.createGroup(8,1);
        group1 = clientController6.chooseGroup(newGroupID);
        clientController6.startReceiverThread();
        assertNotEquals(group0, group1);
        clientController7.chooseGroup(1);
        clientController7.startReceiverThread();
    }

    @Test
    @Order(4)
    void sendMessageTest() {
        clientController1.sendMessage("message1");
    }

    @Test
    @Order(5)
    void characterSelectionTest(){
        //clientController6.setCharacter(1);
    }


    @AfterAll
    static void CloseAllTest(){
        try {
            client1.close();
            client2.close();
            client3.close();
            client4.close();
            client5.close();
            client6.close();
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
