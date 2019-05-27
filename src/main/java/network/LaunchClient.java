package network;

import controller.ClientController;
import gui.ViewGui;
import network.rmi.RMIClientHandler;
import network.rmi.RemoteController;
import network.socket.ViewClient;
import network.socket.launch.SocketClient;
import view.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LaunchClient {
    public static void main(String[] args){
        RemoteController clientHandler = null;
        View view = null;

        System.out.println(">>> Do you want to use RMI? [yes]\n>>> Default Socket");
        Scanner input = new Scanner(System.in);
        String rmi = input.nextLine();

        if(rmi.equals("yes")){
            try {
                clientHandler = (RemoteController) new RMIClientHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            clientHandler = (RemoteController) new SocketClient("", 8234);
            try {
                clientHandler.init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ClientController clientController = null;

        System.out.println(">>> Do you want to use the GUI? [yes]\n>>> Default CLI");
        String gui = input.nextLine();
        if(gui.equals("yes")){
            try {
                clientController = new ClientController(clientHandler, new ViewGui());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                clientController = new ClientController(clientHandler, new ViewClient());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        try {
            clientController.run();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //Via
    }
}
