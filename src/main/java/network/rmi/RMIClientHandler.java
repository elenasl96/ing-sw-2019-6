package network.rmi;

import controller.ServerController;
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

public class RMIClientHandler extends UnicastRemoteObject implements Client, ClientHandler, Remote {

    private ServerController controller;
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
        response = new GroupChangeNotification(true, controller.getUser());
    }

    @Override
    public void onLeave(User u) {
        response = new GroupChangeNotification(false, controller.getUser());
    }

    @Override
    public void onStart() {
        response = new StartGameResponse();
    }

    @Override
    public void onUpdate(Update update) {
        System.out.print(">>> I'm clientHandler sending: ");
        if(update.isPlayerChanges()){
            System.out.print("a MoveUpdateResponse modifying player "+update.getPlayer()+" username "+update.getPlayer().getName()+
                    " of user "+update.getPlayer().getUser()+" with phaseId "+ update.getPlayer().getPhase().getId()+"\n");
            response = new MoveUpdateResponse(update.getPlayer());
        } else {
            System.out.print("a GameUpdateNotification saying string "+ update.toString()+"\n");
            response = new GameUpdateNotification(update);
        }
    }

    @Override
    public String toString(){
        return this.controller.getUser().getUsername()+"RMI Client Handler";
    }

}
