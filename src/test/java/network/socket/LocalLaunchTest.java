package network.socket;

import model.room.Group;
import model.room.User;
import network.socket.launch.ChatServer;
import network.socket.launch.Client;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;


@Execution(ExecutionMode.CONCURRENT)
class LocalLaunchTest {
    private ChatServer chatServer;

    @Test
    void LaunchingServer(){
        try {
            chatServer = new ChatServer(8500, true);
            chatServer.run();
        } catch (IOException e) {
            //nothing
        }

    }

    @Test
    void LaunchConcurrentTests(){
        Timer timer = new Timer();
        TimerTask dummyTask = new TimerTask() {
            int seconds = 5;
            @Override
            public void run() {
                if(seconds == 0){
                    timer.cancel();
                }
                else seconds--;
            }
        };
        timer.schedule(dummyTask, 1000);

        Client client1;
        Client client2;
        Client client3;
        Client client4;
        Client client5;
        Client client6;
        Client client7;

        ClientController clientController1;
        ClientController clientController2;
        ClientController clientController3;
        ClientController clientController4;
        ClientController clientController5;
        ClientController clientController6;
        ClientController clientController7;

        User user1;
        User user2;
        User user3;
        User user4;
        User user5;
        User user6;

        Group group0;
        Group group1;

        //Client 1
        client1 = new Client("", 8500);
        try {
            client1.init();
            } catch (IOException e) {
            e.printStackTrace();
        }
        clientController1 = new ClientController(client1);

        //Client 2
        client2 = new Client("", 8500);
        try {
            client2.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController2 = new ClientController(client2);

        //Client 3
        client3 = new Client("", 8500);
        try {
            client3.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController3 = new ClientController(client3);

        //Client 4
        client4 = new Client("", 8500);
        try {
            client4.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController4 = new ClientController(client4);

        //Client 5
        client5 = new Client("", 8500);
        try {
            client5.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController5 = new ClientController(client5);

        //Client 6
        client6 = new Client("", 8500);
        try {
            client6.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController6 = new ClientController(client6);

        //Client 7
        client7 = new Client("", 8500);
        try {
            client7.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientController7 = new ClientController(client7);

        assertSame(client1, clientController1.client);
        assertNotNull(clientController1.view);

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
        group0 = clientController1.chooseGroup(0);
        group1 = clientController2.chooseGroup(0);
        assertEquals(group0, group1);
        group1 = null;
        clientController2.startReceiverThread();
        clientController3.chooseGroup(0);
        clientController3.startReceiverThread();
        clientController4.chooseGroup(0);
        clientController4.startReceiverThread();
        clientController5.chooseGroup(0);
        clientController5.startReceiverThread();
        clientController7.chooseGroup(0);
        int newGroupID = clientController6.createGroup(8,1);
        group1 = clientController6.chooseGroup(newGroupID);
        clientController6.startReceiverThread();
        assertNotEquals(group0, group1);
        clientController7.chooseGroup(1);
        clientController7.startReceiverThread();
        clientController7.sendMessage("message1");

        //PoisonPill
        try {
            Client poisonClient = new Client("", 8500);
            poisonClient.setPoisonous();
            System.out.println("avvelenato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
