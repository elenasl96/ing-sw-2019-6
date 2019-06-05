package network.socket;

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

public class SocketClientHandler implements ClientHandler, Runnable, ModelObserver {
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

    private void respond(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            printError("IO - " + e.getMessage());
        }
    }

    @Override
    public synchronized void run() {
        try {
            do {
                Response response = ((Request) in.readObject()).handle(controller);
                if (response != null) {
                    notifyAll();
                    respond(response);
                }
            } while (!stop);
        } catch (Exception e) {
            printError(e.getClass().getSimpleName() + " - " + e.getMessage());
            controller.connectionLost();
        }

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

    @Override
    public void onJoin(User user) {
        respond(new GroupChangeNotification(true, user));
    }

    @Override
    public void onLeave(User user) {
        respond(new GroupChangeNotification(false, user));
    }

    @Override
    public Response onUpdate(Update update) {
        System.out.print(">>> I'm SocketClientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            respond(new MoveUpdateResponse(update.getPlayer()));
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            respond(new GameUpdateNotification(update));
        }
        return null;
    }

    @Override
    public void onStart() {
        respond(new StartGameResponse());
    }
}
