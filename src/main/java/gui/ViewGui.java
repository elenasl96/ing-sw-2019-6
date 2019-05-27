package gui;

import controller.ClientController;
import model.field.Coordinate;
import model.room.Update;
import model.room.User;
import network.socket.ViewClient;
import view.View;

import javax.swing.SwingUtilities;
import java.rmi.RemoteException;

public class ViewGui implements View {

    private MainFrame gui;
    private volatile boolean wait=true;
    private ViewClient viewCli;
    private ClientController controller;

    public ViewGui(){
        gui = new MainFrame(null);
        viewCli = new ViewClient();
    }

    public void setClientController(ClientController controller){
        this.controller = controller;
    }

    public void run() {
        try {
            controller.run();
        } catch(RemoteException e){
            //nothing
        }
    }

    @Override
    public void onJoin(User u) {
        viewCli.onJoin(u);
    }

    @Override
    public void onLeave(User u) {
        viewCli.onLeave(u);
    }

    @Override
    public void onStart() {
        gui.initGUI();
        wait = false;
    }

    @Override
    public void onUpdate(Update update) {

    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void displayText(String text) {
        gui.setConsole(text);
    }

    @Override
    public void chooseUsernamePhase() throws RemoteException{
        viewCli.setClientController(controller);
        viewCli.chooseUsernamePhase();
    }

    @Override
    public void chooseGroupPhase() throws RemoteException{
        viewCli.chooseGroupPhase();
    }

    @Override
    public void chooseCharacterPhase() throws RemoteException{
        viewCli.chooseCharacterPhase();
    }

    @Override
    public void setWait(boolean equalsTo) {
        this.wait = wait;
    }

    @Override
    public void waitingPhase() {

    }

    @Override
    public Integer spawnPhase() {
        return null;
    }

    @Override
    public String movePhase() {
        return null;
    }

    @Override
    public Boolean reloadPhase() {
        return null;
    }

    @Override
    public int askNumber() {
        return 0;
    }

}