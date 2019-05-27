package gui;

import controller.ClientController;
import model.field.Coordinate;
import model.room.Update;
import model.room.User;
import network.socket.ViewClient;
import view.View;

import javax.swing.SwingUtilities;

public class ViewGui implements View {

    private MainFrame gui;
    private volatile boolean wait=true;
    private ViewClient viewCli;
    private final ClientController controller;

    public ViewGui(ClientController clientController){
        this.controller = clientController;
        gui = new MainFrame(null);
    }



    @Override
    public void onJoin(User u) {

    }

    @Override
    public void onLeave(User u) {

    }

    @Override
    public void onStart() {
        gui.initGUI();
    }

    @Override
    public void onUpdate(Update update) {

    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void displayText(String insert_a_valid_move) {

    }

    @Override
    public void chooseUsernamePhase() {

    }

    @Override
    public void chooseGroupPhase() {

    }

    @Override
    public void chooseCharacterPhase() {

    }

    @Override
    public void setWait(boolean equalsTo) {

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
