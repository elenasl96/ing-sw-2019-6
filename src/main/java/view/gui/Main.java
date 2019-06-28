package view.gui;

import controller.ClientController;
import model.enums.Color;

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
        gui = new MainFrame();
        gui.initGUI();
    }


    public Main() {

        gui = new MainFrame();

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

        //main.gui.chooseEffectPopUp("furnace", 2);
        //System.out.println(main.gui.askEffects());

        try {
            main.gui.addDropPlayerBoard(1);
            Thread.sleep(2000);
            /*main.gui.addDropPlayerBoard(2);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(2);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(2);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(4);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(4);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(1);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(5);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(5);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(5);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(3);
            Thread.sleep(2000);
            main.gui.addDropPlayerBoard(2);

           main.gui.addMarkerPlayerBoard(3);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(3);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(4);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(4);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(1);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(1);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(5);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(2);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(1);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(1);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(4);
           Thread.sleep(2000);
           main.gui.addMarkerPlayerBoard(4);
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();
           Thread.sleep(2000);
           main.gui.addSkullPlayerBoard();*/

            main.gui.getMapGrid()[0][0].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[0][1].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[0][2].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[0][3].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[1][0].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[1][1].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[1][2].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[1][3].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[2][0].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[2][1].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[2][2].setGrabbable("furnace;mp40;powerglove");
            main.gui.getMapGrid()[2][3].setGrabbable("furnace;mp40;powerglove");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //main.gui.yesNoPopUp();
        //System.out.println(main.gui.yesNoChoose());
    }
}

