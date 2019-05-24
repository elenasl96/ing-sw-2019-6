package network.rmi;

import controller.ClientController;
import network.Client;
import network.exceptions.WrongDeserializationException;
import network.socket.ViewClient;
import network.socket.commands.Request;
import network.socket.commands.Response;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.Scanner;

public class RMIClient{

    private RMIClient(){
        //null
    }

    public static void run(String[] args) throws RemoteException, NotBoundException {

        System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());

        Registry registry = LocateRegistry.getRegistry();

        System.out.println("RMI registry bindings: ");
        for (String binding : registry.list()) {
            System.out.println(">>> " + binding);
        } System.out.println("\n");
        System.out.println(">>> Do you want to use the GUI? [yes]\n>>> Default CLI");
        Scanner input = new Scanner(System.in);
        String GUI = input.nextLine();
        ClientController clientController = (ClientController) registry.lookup("controller");

        if(GUI.equals("yes")){
            //launch GUI
        } else {
            new ViewClient(clientController).run();
        }

    }
}