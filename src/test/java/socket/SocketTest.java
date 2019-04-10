package socket;
import org.junit.jupiter.api.Test;
import socket.network.ChatServer;
import socket.network.Client;

import java.io.*;
import java.net.Socket;


public class SocketTest {
    /*
    @Test
    public void SeverTest(){
        try{ChatServer server = new ChatServer(8000);
            server.run();}
        catch (IOException io){System.out.print("IOException occurred");}

    }

    @Test
    public void ClientTest() {
        Client client = new Client("", 8000);
        try {
            Socket connection = new Socket("", 8000);
            System.out.println("np1");
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            System.out.println("np2");
            InputStream test1 = new ByteArrayInputStream(("ciao").getBytes());
            System.setIn(test1);
            System.out.println(test1);
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            System.out.println("init finished");
        }catch (IOException ioe){
            System.out.println("An error occurred");
        }
        try{
            ClientController controller = new ClientController(client);
            controller.run();
            String input = "marti";
            System.out.println(input);
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            input = "close";
            InputStream close = new ByteArrayInputStream(input.getBytes());
            System.setIn(close);
            client.close();
        }catch (IOException ioe){
            System.out.println("An error occurred");
        }
    }

    @Test
    public void initTest(){
        Client client = new Client("", 8000);
        try {
            client.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
