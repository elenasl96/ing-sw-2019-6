package network.rmi;

import controller.ClientController;
import network.Client;
import network.socket.ViewClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient{

    private RMIClient(){
        //null
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        run();
    }

    public static void run() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        System.out.println(">>> Registry located");

        for (String name : registry.list()) {
            System.out.println(">>> Registry bindings: " + name);
        }
        System.out.println("\n");

        // gets a reference for the remote controller
        Client controller = (Client) registry.lookup("controller");
        ClientController clientController = new ClientController(controller);

        // creates and launches the view
        new ViewClient(clientController).run();

    }
}