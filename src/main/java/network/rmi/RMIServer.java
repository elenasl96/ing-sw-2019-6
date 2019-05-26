package network.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException {
        System.setProperty("java.security.policy", "rmi.policy");
        System.setSecurityManager(new SecurityManager());

        System.out.println(">>> Creating new ClientController");
        RMIClientHandler controller = new RMIClientHandler();

        System.out.println(">>> Binding");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("controller", controller);

        System.out.println(">>> Waiting for invocations...");
    }
}
