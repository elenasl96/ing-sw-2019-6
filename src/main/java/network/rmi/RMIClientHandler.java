package network.rmi;

import controller.ServerController;
import model.room.ModelObserver;
import model.room.Update;
import model.room.User;
import network.Client;
import network.ClientHandler;
import network.socket.commands.Request;
import network.socket.commands.Response;
import network.socket.commands.response.GameUpdateNotification;
import network.socket.commands.response.GroupChangeNotification;
import network.socket.commands.response.MoveUpdateResponse;
import network.socket.commands.response.StartGameResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientHandler extends UnicastRemoteObject implements RemoteController, ClientHandler {

    private transient ServerController controller;
    private Response response;

    RMIClientHandler() throws RemoteException {
        this.controller = new ServerController(this);
    }

    @Override
    public void request(Request request) {
        response = request.handle(controller);
        //response = null???
    }

    @Override
    public Response nextResponse() {
        return response;
    }

    @Override
    public void onJoin(User u) {

    }

    @Override
    public void onLeave(User u) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate(Update update) {

    }
}
