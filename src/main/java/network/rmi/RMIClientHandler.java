package network.rmi;

import controller.ServerController;
import model.room.Update;
import model.room.User;
import network.ClientHandler;
import network.RemoteController;
import network.commands.Request;
import network.commands.Response;
import network.commands.response.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * RMI Client Handler piece of the network:
 * connects the client to the server and vice versa.
 */
public class RMIClientHandler extends UnicastRemoteObject implements RemoteController, ClientHandler {

    private transient ServerController controller;
    private ArrayList<Response> responses;

    public RMIClientHandler() throws RemoteException {
        this.controller = new ServerController(this);
        this.responses = new ArrayList<>();
    }

    /**
     * @see RemoteController#request(Request)
     */
    @Override
    public synchronized void request(Request request) {
        this.responses.add(request.handle(controller));
    }

    /**
     * @see RemoteController#nextResponse()
     */
    @Override
    public synchronized Response nextResponse() {
        try{
            Response next = responses.get(0);
            responses.remove(0);
            return next;
        } catch (NullPointerException | IndexOutOfBoundsException e){
            return null;
        }

    }

    @Override
    public void init(){
        System.out.println(">>> RMIClientHandler initialized");
    }

    @Override
    public void bound() throws RemoteException {
        RMIClientHandler newClientHandler = new RMIClientHandler();
        Registry registry = LocateRegistry.getRegistry(1099);
        registry.rebind("controller", newClientHandler);
    }

    @Override
    public void onJoin(User u) {
        this.responses.add(new GroupChangeNotification(true, u));
    }

    @Override
    public void onLeave(User u) {
        this.responses.add(new GroupChangeNotification(false, u));
    }

    @Override
    public void onStart() {
        this.responses.add(new StartGameResponse());
    }

    /**
     * Sends update to the clientController
     * @param update the update
     */
    @Override
    public synchronized void onUpdate(Update update) {
        System.out.print(">>> I'm RMIClientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            this.responses.add(new MoveUpdateResponse(update.getPlayer()));
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            this.responses.add(new GameUpdateNotification(update));
        }
    }

    @Override
    public void onEndGame() {
        this.responses.add(new EndGameNotification());
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    /**
     * signals if every update is received
     */
    @Override
    public synchronized void received() {
        if(this.responses.isEmpty()){
            this.responses = new ArrayList<>();
        }
    }
}
