package network.socket;

import model.room.*;
import network.socket.commands.Request;
import network.socket.commands.response.StartGameResponse;
import network.socket.commands.Response;
import network.socket.commands.response.GroupChangeNotification;
import network.socket.commands.response.MessageNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, MessageReceivedObserver, GroupChangeListener, GameUpdateObserver {
    private static final String ERROR = "Errors in closing - ";

    private Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private boolean stop;

    private final ServerController controller;

    public ClientHandler(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.controller = new ServerController(this);
    }

    private void printError(String message) {
        System.err.println(">>> ERROR@" + socket.getRemoteSocketAddress() + ": " + message);
    }

    public void respond(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            printError("IO - " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            do {
                Response response = ((Request) in.readObject()).handle(controller);
                if (response != null) {
                    respond(response);
                }
            } while (!stop);
        } catch (Exception e) {
            printError(e.getClass().getSimpleName() + " - " + e.getMessage());
            controller.connectionLost();
        }

        close();
    }

    public void stop() {
        stop = true;
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

    // --- Directly forward notifications to clients

    @Override
    public void onMessage(Message message) {
        respond(new MessageNotification(message));
    }

    @Override
    public void onJoin(User user) {
        respond(new GroupChangeNotification(true, user));
    }

    @Override
    public void onLeave(User user) {
        respond(new GroupChangeNotification(false, user));
    }

    @Override
    public void onUpdate(Update update) {

    }

    @Override
    public void onStart() {
        respond(new StartGameResponse());

    }
}
