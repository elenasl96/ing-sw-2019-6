package gui;

import controller.ClientController;
import network.socket.launch.SocketClient;

import javax.swing.SwingUtilities;

public class Main {

    private MainFrame gui;

    private ClientController controller;

    public Main(ClientController clientController) {

        this.controller = clientController;
        gui = new MainFrame();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
            }
        });
    }
}

