package network.socket;

import controller.GameController;
import controller.ServerController;
import model.room.*;
import network.ClientHandler;
import network.commands.Request;
import network.commands.response.*;
import network.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import static model.enums.Phase.DISCONNECTED;

/**
 * SERVER SIDE
 * Socket connection
 */
public class SocketClientHandler implements ClientHandler,Runnable,ModelObserver {
    private static final String ERROR = "Errors in closing - ";

    private Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private boolean stop;

    private final ServerController controller;

    public SocketClientHandler(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.controller = new ServerController(this);
    }

    private void printError(String message) {
        System.err.println(">>> ERROR@" + socket.getRemoteSocketAddress() + ": " + message);
    }

    /**
     * @param response sends the response in the socket
     */
    private void respond(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            controller.getUser().getPlayer().setPhase(DISCONNECTED);
            printError("IO - " + e.getMessage());
            controller.connectionLost();
        }
    }

    /**
     * Keeps receiving requests
     */
    @Override
    public synchronized void run() {
        Response response;
            do {
                try{
                    response = ((Request) in.readObject()).handle(controller);
                    if (response != null) {
                        respond(response);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    controller.getUser().getPlayer().setPhase(DISCONNECTED);
                    printError(e.getClass().getSimpleName() + "IO - " + e.getMessage());
                    stop = true;
                    controller.connectionLost();
                }
            } while (!stop);
        close();
    }

    private void close() {
        stop = true;
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                printError(ERROR + e.getMessage());
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                printError(ERROR + e.getMessage());
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            printError(ERROR + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return this.controller.getUser().getUsername()+"SocketClient Handler";
    }

    // --- Directly forward notifications to clients

    /**
     * Triggers onJoin on View
     * @param user the user Joining
     */
    @Override
    public void onJoin(User user) {
        respond(new GroupChangeNotification(true, user));
    }

    /**
     * Triggers onLeave on View
     * @param user the user Leaving
     */
    @Override
    public void onLeave(User user) {
        respond(new GroupChangeNotification(false, user));
    }

    /**
     * @param update sends the update, as in RMI
     * @see network.rmi.RMIClientHandler#onUpdate(Update)
     */
    @Override
    public void onUpdate(Update update) {
        try {
            System.out.print(">>> I'm SocketClientHandler sending: ");
         if (update.isPlayerChanges()) {
                System.out.print("a MoveUpdateResponse\n");
                respond(new MoveUpdateResponse(update.getPlayer()));
            } else {
                System.out.print("a GameUpdateNotification saying string " + update.toString() + "\n");
                respond(new GameUpdateNotification(update));
            }
        }catch (NullPointerException | ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onEndGame() {
        respond(new EndGameNotification());
    }

    /**
     * Sends a new start game response, triggering onStart on View
     */
    @Override
    public void onStart() {
        respond(new StartGameResponse());
    }
}
