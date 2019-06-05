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
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        (main.gui.getMapGrid())[1][1].remove(2);
        (main.gui.getMapGrid())[1][1].repaint();
        System.out.println(main.gui.getMove());

        try {
            (main.gui.getMapGrid())[1][1].add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/pedina4.jpg"))
                    .getScaledInstance(70, 60, Image.SCALE_SMOOTH))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        (main.gui.getMapGrid())[1][1].repaint();
        System.out.println(main.gui.getMove());
        System.out.println(main.gui.getMove());
        System.out.println(main.gui.getMove());
    }
}

