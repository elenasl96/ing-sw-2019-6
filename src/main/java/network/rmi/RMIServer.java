package network.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        System.out.println(">>> Creating new RMIClientHandler");
        RMIClientHandler clientHandler = new RMIClientHandler();

        System.out.println(">>> Binding");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("controller", clientHandler);

        System.out.println(">>> Waiting for invocations...");
    }
}
