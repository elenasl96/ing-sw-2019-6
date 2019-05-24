package network.rmi;

import controller.ClientController;
import controller.ServerController;
import network.Client;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());

        System.out.println(">>> Creating new ClientController");
        RMIClientHandler controller = new RMIClientHandler();

        System.out.println("Binding");
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("controller", controller);

        System.out.println("Waiting for invocations...");
    }
}
