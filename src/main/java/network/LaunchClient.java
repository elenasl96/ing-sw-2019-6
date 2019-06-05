package network;

import controller.ClientController;
import view.gui.ViewGui;
import network.rmi.RMIClientHandler;
import view.ViewClient;
import network.socket.SocketClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;
//TODO javadoc
public class LaunchClient {
    private static final String ERROR = "An error occurred, retrying...";
    public static void main(String[] args){
        RemoteController clientHandler = null;

        System.out.println(">>> Do you want to use RMI? [yes]\n>>> Default Socket");
        Scanner input = new Scanner(System.in);
        String rmi = input.nextLine();

        if(rmi.equals("yes")){
            while(clientHandler == null){
                try {
                    clientHandler = new RMIClientHandler();
                } catch (RemoteException e) {
                    System.out.println(ERROR);
                }
            }
        } else {
            while(clientHandler == null) {
                clientHandler = new SocketClient("192.168.137.1", 8234);

                try {
                    clientHandler.init();
                } catch (IOException e) {
                    System.out.println(ERROR);
                    clientHandler = null;
                }
            }
        }

        ClientController clientController = null;

        System.out.println(">>> Do you want to use the GUI? [yes]\n>>> Default CLI");
        String gui = input.nextLine();
        if(gui.equals("yes")){
            while(clientController == null){
                try {
                    clientController = new ClientController(clientHandler, new ViewGui());
                } catch (RemoteException e) {
                    System.out.println(ERROR);
                }
            }
        } else {
            while(clientController == null) {
                try {
                    clientController = new ClientController(clientHandler, new ViewClient());
                } catch (RemoteException e) {
                    System.out.println(ERROR);
                }
            }
        }

        try {
            clientController.run();
        } catch (RemoteException e) {
            System.out.println(ERROR);
        }

        //Via
    }
}
