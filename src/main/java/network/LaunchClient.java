package network;

import controller.ClientController;
import view.gui.ViewGui;
import view.ViewClient;
import network.socket.SocketClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * The Launcher ClientSide
 * It's the only class that needs to be launched when playing main class
 * Handles the choice of Socket or RMI and CLI or GUI
 */
public class LaunchClient {
    /**
     * The constant printed when catching errors
     */
    private static final String ERROR = "An error occurred, retrying...";

    /**
     * The RemoteController of the Client
     * Can be either SocketClientHandler or RMIClientHandler
     * @see RemoteController
     * @see network.socket.SocketClientHandler
     * @see network.rmi.RMIClientHandler
     */
    private static RemoteController clientHandler;

    /**
     * The Controller client side
     * @see ClientController
     */
    private static ClientController clientController;

    /**
     * Main method.
     * Asks first for Socket or RMI, then CLI or GUI
     * At last, launches Client Controller's run method
     * Throws RemoteException in running controller
     * @param args unused
     * @see ClientController#run()
     * @see this#socketOrRMI()
     * @see this#cliOrGui()
     */
    public static void main(String[] args){
        clientHandler = null;
        socketOrRMI();
        clientController = null;
        cliOrGui();
        try {
            clientController.run();
        } catch (RemoteException e) {
            System.out.println(ERROR);
        }

        //Via
    }

    /**
     * Handles the choice of Socket or RMI by the user
     * RMI if the user inserts "yes", CLI otherwise
     */
    private static void socketOrRMI(){
        System.out.println(">>> Do you want to use RMI? [yes]\n>>> Default Socket");
        Scanner input = new Scanner(System.in);
        String rmi = input.nextLine();

        if(rmi.equals("yes")){
            while(clientHandler == null){
                Registry registry;
                try {
                    registry = LocateRegistry.getRegistry();
                    for (String name : registry.list()) {
                        System.out.println("Registry bindings: " + name);
                    }
                    System.out.println("\n");

                    // gets a reference for the remote controller
                    clientHandler = (RemoteController) registry.lookup("controller");
                    clientHandler.bound();
                } catch (RemoteException | NotBoundException e) {
                    System.out.println("An error occurred");
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
    }

    /**
     * Handles the choice of CLI or GUI by the user
     * GUI if the user inserts "yes", CLI otherwise
     */
    private static void cliOrGui(){
        Scanner input = new Scanner(System.in);
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

    }
}
