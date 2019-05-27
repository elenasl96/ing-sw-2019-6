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

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientHandler extends UnicastRemoteObject implements RemoteController, ClientHandler {

    private transient ServerController controller;
    private Response response;

    public RMIClientHandler() throws RemoteException {
        this.controller = new ServerController(this);
    }

    @Override
    public void request(Request request) {
        response = request.handle(controller);
    }

    @Override
    public Response nextResponse() {
        return response;
    }

    @Override
    public void receivedResponse() throws RemoteException {
        response = null;
    }

    @Override
    public void init() throws IOException {
        //nothing
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
        System.out.print(">>> I'm RMIClientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            response = new MoveUpdateResponse(update.getPlayer());
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            response = new GameUpdateNotification(update);
        }
    }
}
