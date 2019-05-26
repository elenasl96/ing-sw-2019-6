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

    public Main() {

        gui = new MainFrame();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
            }
        });
    }

    public static void main(String[] args) {
       /* ClientController clientController = null;
        try{
            SocketClient socketClient = new SocketClient("", 8234);
            socketClient.init();
            clientController = new ClientController(socketClient);
        } catch(Exception e){
            //niente
        }*/
        //new Main(clientController);
        //clientController.run();
        new Main();
    }
}

