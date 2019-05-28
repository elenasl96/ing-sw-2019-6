package gui;

import controller.ClientController;
import network.socket.launch.SocketClient;

import javax.swing.SwingUtilities;

public class Main {

    private MainFrame gui;

    private ClientController controller;

    public Main(ClientController clientController) {

        this.controller = clientController;
        gui = new MainFrame(null);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
            }
        });
    }


    public Main() {

        gui = new MainFrame(null);

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
        Main main=new Main();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(main.gui.getMove());
        main.gui.toggleBackGroundTurn();


    }
}

