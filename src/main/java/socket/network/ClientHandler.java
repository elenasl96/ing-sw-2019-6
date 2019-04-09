package socket.network;

import chat.ServerController;
import chat.model.GroupChangeListener;
import chat.model.Message;
import chat.model.MessageReceivedObserver;
import chat.model.User;
import chat.network.commands.GroupChangeNotification;
import chat.network.commands.MessageNotification;
import chat.network.commands.Request;
import chat.network.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, MessageReceivedObserver, GroupChangeListener {
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
                printError("Errors in closing - " + e.getMessage());
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                printError("Errors in closing - " + e.getMessage());
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            printError("Errors in closing - " + e.getMessage());
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
}
