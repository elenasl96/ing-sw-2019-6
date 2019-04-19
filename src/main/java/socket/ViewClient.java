package socket;


import exception.NotExistingFieldException;
import model.enums.Character;
import socket.exceptions.InvalidGroupNumberException;
import socket.model.*;

import java.util.Scanner;

public class ViewClient implements MessageReceivedObserver, GroupChangeListener, GameUpdateObserver {
    private Scanner fromKeyBoard;
    // ----- The view is composed with the controller (strategy)
    private final ClientController controller;
    private boolean wait;

    public ViewClient(ClientController controller) {
        this.controller = controller;
        this.fromKeyBoard = new Scanner(System.in);
    }

    private String userInput() {
        return fromKeyBoard.nextLine();
    }

    public void displayText(String text) {
        System.out.println(">>> " + text);
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public void chooseUsernamePhase() {
        User user;
        do {
            displayText("Provide username:");
            String userInput = userInput();
            user = controller.createUser(userInput);
        } while (user == null);

        displayText("You are " + user.getUsername());
        user.listenToMessages(this);
    }

    public void chooseGroupPhase() {
        Group group = null;
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
                    group = controller.chooseGroup(groupNumber);
                } catch (InvalidGroupNumberException|NumberFormatException| NotExistingFieldException e){
                    displayText("Insert a valid number");
                    group = null;
                }
            } else {
                displayText("I don't understand, let's try again");
                group = null;
            }
        } while (group == null);
        displayText("Welcome to " + group.getName());
        group.observe(this);
        wait = false;
        controller.startReceiverThread();
        do {
            messagingPhase();
        } while (wait);
    }

    public void preGamingPhase(){
        displayText("Which character do you want to be?");
        //TODO display possible character and description
        Character response;
        try{
            do{
                int character = Integer.parseInt(userInput());
                while (character < 1 || character > 5) {
                    displayText("Choose between character from 1 to 5");
                    character = Integer.parseInt(userInput());
                }
                response = controller.setCharacter(character);
                if(response == Character.NOT_ASSIGNED) displayText("Character already taken, choose another one");
            } while(response == Character.NOT_ASSIGNED);
            displayText("You are" + response);
        }catch (NumberFormatException e){
            displayText("Please insert a number");
        }
        displayText("End pregaming phase");
        do {
            messagingPhase();
        } while (wait);
        wait = true;
    }

    public void gamingPhase(){
        displayText("Game starting");
    }

    public void messagingPhase() {
        String content;
        do {
            content = userInput();
            controller.sendMessage(content);
        } while (!content.startsWith(":q"));
    }


    // ----- The view observes the state and reacts (the observable pushes the pieces of interesting state)

    @Override
    public void onMessage(Message message) {
        displayText(message.toString());
    }

    @Override
    public void onJoin(User user) {
        displayText(user.getUsername() + " joined the group");
    }

    @Override
    public void onLeave(User user) {
        displayText(user.getUsername() + " left the group");
    }

    @Override
    public void onUpdate(Update update) {

    }

    @Override
    public void onStart() {
        displayText("Get ready for A D R E N A L I N E");
        wait = true;
    }
}
