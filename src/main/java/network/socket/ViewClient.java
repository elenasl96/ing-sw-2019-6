package network.socket;


import controller.ClientController;
import exception.NotExistingFieldException;
import model.field.Coordinate;
import model.room.*;
import model.enums.Character;
import network.exceptions.InvalidGroupNumberException;
import network.socket.commands.Response;
import view.View;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ViewClient implements View {
    private Scanner fromKeyBoard;
    // ----- The view is composed with the controller (strategy)
    private ClientController controller;
    private volatile boolean wait=true;
    private static final String PLEASE_NUMBER = "Please insert a Number";

    public ViewClient() {
        this.fromKeyBoard = new Scanner(System.in);
    }

    public void run() {
        try {
            controller.run();
        } catch(RemoteException e){
            //nothing
        }
    }

    private String userInput() {
        if(fromKeyBoard.hasNextLine())
            return fromKeyBoard.nextLine();
        return "";
    }

    public void displayText(String text) {
        System.out.println(">>> " + text);
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }


    public int askNumber() {
        try{
            return Integer.parseInt(userInput());
        }catch (NumberFormatException e){
            displayText(PLEASE_NUMBER);
            return this.askNumber();
        }
    }

    public void chooseUsernamePhase()  throws RemoteException {
        User user;
        do {
            displayText("Provide username:");
            String userInput = userInput();
            user = controller.createUser(userInput);
        } while (user == null);

        displayText("You are " + user.getUsername());
        user.listenToMessages(this);
    }

    public void chooseGroupPhase()  throws RemoteException{
        Group group;
        do {
            displayText("These are the groups at the moment:");
            displayText(controller.getSituation());
            displayText("Group0 has 5 skulls by default (starter mode)");
            displayText("Do you want to create a new game? [yes/no]");
            String answer = userInput();
            if (answer.equals("no")) {
                displayText("Which game do you want to join?");
                try {
                    int a = Integer.parseInt(userInput());
                    group = controller.chooseGroup(a);
                } catch (InvalidGroupNumberException|NumberFormatException e){
                    displayText("Insert a valid number");
                    group = null;
                }
            } else if (answer.equals("yes")) {
                group = this.yesCreateGame();
            } else {
                displayText("I don't understand, let's try again");
                group = null;
            }
        } while (group == null);
        displayText("Welcome to " + group.getName());
    }

    public void chooseCharacterPhase() throws RemoteException{
        displayText("Which character do you want to be?");
        displayText("Insert 1 for :D-STRUCT-OR");
        displayText("Insert 2 for BANSHEE");
        displayText("Insert 3 for DOZER");
        displayText("Insert 4 for VIOLET");
        displayText("Insert 5 for SPROG");
        Character response;
        try{
            do{
                int character = Integer.parseInt(userInput());
                while (character < 1 || character > 5) {
                    displayText("Choose between character from 1 to 5");
                    character = Integer.parseInt(userInput());
                }
                response = controller.setCharacter(character);
                if(response == Character.NOT_ASSIGNED)
                    displayText("Character already taken, choose another one");
            } while(response == Character.NOT_ASSIGNED);
            displayText("You are" + response);
        }catch (NumberFormatException e){
            displayText(PLEASE_NUMBER);
        }
        ClientContext.get().getCurrentGroup().observe(this);
        controller.startReceiverThread();
        //noinspection StatementWithEmptyBody
        while(wait);
        //blocked until the game can start
    }

    public Integer spawnPhase(){
        Integer spawnNumber = null;
        while (spawnNumber == null){
            try{
                spawnNumber = Integer.parseInt(userInput());
            }catch (NumberFormatException e){
                displayText(PLEASE_NUMBER);
                spawnNumber = null;
            }
        } return spawnNumber;
    }

    public String movePhase(){
        return userInput();
    }

    public void waitingPhase(){
        //noinspection StatementWithEmptyBody
        while(wait);
    }

    public Coordinate getCoordinate(){
        displayText("Insert coordinates in format \'X 0\'");
        char letter;
        int number;
        Coordinate coordinate = null;
        while(coordinate == null){
            String[] input = userInput().split(" ");
            try {
                if(input.length !=2 || input[0].length()!=1 || input[1].length()!=1){
                    throw new NumberFormatException();
                } else {
                    letter = input[0].toUpperCase().charAt(0);
                    if (!java.lang.Character.isLetter(letter)){
                        throw new NumberFormatException();
                    }
                    number = Integer.parseInt(input[1]);
                    coordinate = new Coordinate(letter, number);
                }
            } catch (NumberFormatException e){
                displayText("Not a valid format! Examples\n A 4\nB 7\nF 5");
            }
        }
        return coordinate;
    }

    private Group yesCreateGame() throws RemoteException{
        try {
            displayText("How many number of Skulls do you want to use?");
            int skullNumber = Integer.parseInt(userInput());
            while(skullNumber!=5 && skullNumber!=8){
                displayText("Games can only have 5 or 8 skulls");
                skullNumber = Integer.parseInt(userInput());
            }
            displayText("Which field do you want to use?");
            int fieldNumber = Integer.parseInt(userInput());
            while(fieldNumber<1 || fieldNumber>3) {
                displayText("Choose between field 1, 2 or 3");
                fieldNumber = Integer.parseInt(userInput());
            }
            int groupNumber = controller.createGroup(skullNumber, fieldNumber);
            return controller.chooseGroup(groupNumber);
        } catch (InvalidGroupNumberException|NumberFormatException| NotExistingFieldException e){
            displayText("Insert a valid number");
            return null;
        }
    }

    @Override
    public void setClientController(ClientController controller){
        this.controller = controller;
    }

    // ----- The view observes the state and reacts (the observable pushes the pieces of interesting state)

    @Override
    public void onJoin(User user) {
        displayText(user.getUsername() + " joined the group");
    }

    @Override
    public void onLeave(User user) {
        displayText(user.getUsername() + " left the group");
    }

    @Override
    public Response onUpdate(Update update) {
        displayText("Update:" + update.toString());
        return null;
    }

    @Override
    public void onStart() {
        displayText("Get ready for A D R E N A L I N E");
        wait = false;
    }

    @Override
    public Boolean reloadPhase() {
        String input;
        do {
            displayText("Do you want to reload any weapon?");
            input = userInput();
        }while(!input.equals("yes") && !input.equals("no"));
        return (input.equals("yes"));
    }
}
