package view.gui;

import controller.ClientController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

    private MainFrame gui;

    private ClientController controller;

    public Main(ClientController clientController) {
        this.controller = clientController;
        gui = new MainFrame(null);
        gui.initGUI();
    }


    public Main() {

        gui = new MainFrame(null);

        gui.initGUI();
        gui.setVisible(true);
    }

    public static void main(String[] args) {
       /* ClientController clientController = null;
        try{
            SocketClient socketClient = new SocketClient("", 8234);
            socketClient.init();
            clientController = new ClientController(socketClient);
        } catch(Exception e){
            //nientes
        }*/
        //new Main(clientController);
        //clientController.run();
        Main main = new Main();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }

        //System.out.println(main.gui.getMove());
        //System.out.println(main.gui.getMove());
        //System.out.println(main.gui.getJLabelText());
        //System.out.println(main.gui.getMove());
        //System.out.println(main.gui.getJLabelText());
        //System.out.println(main.gui.getJLabelText());
        //System.out.println(main.gui.getMove());

        main.gui.chooseEffectPopUp("furnace", 2);
        System.out.println(main.gui.askEffects());
    }
}

