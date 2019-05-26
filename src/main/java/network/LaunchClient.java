package network;

import network.socket.ViewClient;

import java.util.Scanner;

public class LaunchClient {
    public static void main(String[] args){

        System.out.println(">>> Do you want to use the GUI? [yes]\n>>> Default CLI");
        Scanner input = new Scanner(System.in);
        String gui = input.nextLine();
        if(gui.equals("yes")){
            //launch GUI
        } else {
            //launch CLI
        }

        //poi RMI o Socket

        //Via
    }
}
