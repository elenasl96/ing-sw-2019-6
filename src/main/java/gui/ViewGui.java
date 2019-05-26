package gui;

import controller.ClientController;
import model.room.Update;
import model.room.User;
import view.View;

import javax.swing.SwingUtilities;

public class ViewGui implements View {
    private MainFrame gui;

    private final ClientController controller;

    public ViewGui(ClientController clientController){
        this.controller = clientController;
        gui = new MainFrame();
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
