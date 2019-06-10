package network.rmi;

import controller.ServerController;
import model.room.Update;
import model.room.User;
import network.ClientHandler;
import network.RemoteController;
import network.commands.Request;
import network.commands.Response;
import network.commands.response.GameUpdateNotification;
import network.commands.response.GroupChangeNotification;
import network.commands.response.MoveUpdateResponse;
import network.commands.response.StartGameResponse;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//TODO javadoc
public class RMIClientHandler extends UnicastRemoteObject implements RemoteController, ClientHandler {

    private transient ServerController controller;
    private Response response;

    public RMIClientHandler() throws RemoteException {
        this.controller = new ServerController(this);
    }

    @Override
    public synchronized void request(Request request) {
        this.response = request.handle(controller);
    }

    @Override
    public synchronized Response nextResponse() {
        return response;
    }

    @Override
    public void init() throws IOException {
        System.out.println(">>> RMIClientHandler initialized");
    }

    @Override
    public void onJoin(User u) {
        this.response = new GroupChangeNotification(true, u);
    }

    @Override
    public void onLeave(User u) {
        this.response = new GroupChangeNotification(false, u);
    }

    @Override
    public void onStart() {
        this.response = new StartGameResponse();
    }

    @Override
    public synchronized Response onUpdate(Update update) {
        System.out.print(">>> I'm RMIClientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            this.response = new MoveUpdateResponse(update.getPlayer());
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            this.response = new GameUpdateNotification(update);
        } return null;
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public synchronized void received(){
        response = null;
    }
}
