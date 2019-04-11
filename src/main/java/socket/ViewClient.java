package socket;


import socket.model.*;

import java.util.Scanner;

public class ViewClient implements MessageReceivedObserver, GroupChangeListener {
    private Scanner fromKeyBoard;
    // ----- The view is composed with the controller (strategy)
    private final ClientController controller;

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
        displayText("These are the groups at the moment:");
        displayText(controller.getSituation());
        displayText("Do you want to create a new game? [yes/no]");
        String answer = userInput();
        if (answer.equals("no")) {
            displayText("Which game do you want to join?");
            Group group = controller.chooseGroup(Integer.parseInt(userInput()));
            displayText("Welcome to " + group.getName());
            group.observe(this);
        } else if(answer.equals("yes")) {
            displayText("Creating a new group...");
            int groupNumber = controller.createGroup();
            Group group = controller.chooseGroup(groupNumber);
            displayText("Welcome to " + group.getName());
            group.observe(this);
        } else {
            displayText("I don't understand, let's try again");
            this.chooseGroupPhase();
        }
    }

    public void messagingPhase() {
        controller.startMessaging();
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
}
