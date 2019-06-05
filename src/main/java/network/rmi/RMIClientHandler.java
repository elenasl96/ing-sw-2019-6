package network.rmi;

import controller.ServerController;
import model.room.Update;
import model.room.User;
import network.ClientHandler;
import network.RemoteController;
import network.commands.Request;
import network.commands.Response;
import network.commands.response.GameUpdateNotification;
import network.commands.response.MoveUpdateResponse;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//TODO javadoc
public class RMIClientHandler extends UnicastRemoteObject implements RemoteController, ClientHandler {

    private transient ServerController controller;
    private Request request;

    public RMIClientHandler() throws RemoteException {
        this.controller = new ServerController(this);
    }

    @Override
    public void request(Request request) {
        this.request = request;
    }

    @Override
    public synchronized Response nextResponse() {
        notifyAll();
        return request.handle(controller);
    }

    @Override
    public void init() throws IOException {
        //nothing
    }

    @Override
    public void onJoin(User u) {
        //nothing
    }

    @Override
    public void onLeave(User u) {
        //nothing
    }

    @Override
    public void onStart() {
        //nothing
    }

    @Override
    public Response onUpdate(Update update) {
        System.out.print(">>> I'm RMIClientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            return new MoveUpdateResponse(update.getPlayer());
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            return new GameUpdateNotification(update);
        }
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }
}
