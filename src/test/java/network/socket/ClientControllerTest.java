package network.socket;

import model.room.User;
import network.socket.launch.Client;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientControllerTest {
    //NOTE: a new Server needs to be running every time
    private static ClientController clientController;

    private static ViewClient view;
    private static Client client;
    private static ClientHandler clientHandler;
    private static ServerController serverController;

    @BeforeAll
    static void Constructor(){
        try {
            Socket socket = new Socket("", 8234);
            clientHandler = new ClientHandler(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverController = new ServerController(clientHandler);
        client = new Client("", 8234);
        try {
            client.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientController = new ClientController(client);
        view = new ViewClient(clientController);
    }

    @Test
    void ConstructorTest(){
        //Already constructed
        assertSame(client, clientController.client);
        assertNotNull(clientController.view);
    }

    @Test
    @Order(1)
    void createUserTest(){
        User userCreated = clientController.createUser("username");
        assertEquals(userCreated.getUsername(),"username");
    }

    private int groupCreatedID;
    @Test
    @Order(2)
    void createGroupTest(){
        groupCreatedID = clientController.createGroup(5,1);
    }

    @Test
    @Order(3)
    void chooseGroupRequest(){
        clientController.chooseGroup(groupCreatedID);
    }

    @Test
    void getSituationTest(){
        clientController.getSituation();
    }

    @Test
    @Order(4)
    void setCharacterTest(){
        clientController.setCharacter(4);
    }

    @AfterAll
    static void CloseAllTest(){
        try {
            client.close();
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
