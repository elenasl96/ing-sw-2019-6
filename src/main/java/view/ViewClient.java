package view;

import controller.ClientController;
import model.enums.Character;
import model.exception.NotExistingFieldException;
import model.field.Coordinate;
import model.room.Group;
import model.room.Update;
import model.room.User;
import network.ClientContext;
import network.commands.Response;
import network.exceptions.InvalidGroupNumberException;

import javax.sound.sampled.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ViewClient implements View {
    private Scanner fromKeyBoard;
    private ClientController controller;
    private volatile boolean wait=true;
    private static final String PLEASE_NUMBER = "Please insert a Number";

    private Clip clip;

    public ViewClient() {
        this.fromKeyBoard = new Scanner(System.in);
    }


    public void playMusic(String string){
        try {
            File yourFile = new File(string);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            displayText("Error!!");
            System.err.println(e.getMessage());
        }
    }

    public void run() {
        try {
            controller.run();
        } catch(RemoteException e){
            //nothing
        }
    }

    public String userInput() {
        if(fromKeyBoard.hasNextLine())
            return fromKeyBoard.nextLine();
        return "";
    }

    @Override
    public String askEffects() {
        displayText("Insert effects numbers in format '0 1 2'");
        String input = userInput();
        boolean done = false;
        while(!done){
            String[] inputSplitted = input.split(" ");
            try {
                for(String i: inputSplitted) {
                    if(i.length()!=1){
                        throw new NumberFormatException();
                    }
                    Integer.parseInt(i);
                }
                done = true;
            } catch (NumberFormatException e){
                displayText("Not a valid format! Examples\n0 1 2\n2 1\n0");
            } catch (IndexOutOfBoundsException e ){
                displayText("Index out");
            }
        }
        return input;
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
        do{
            int character = 0;
            try{
                character = Integer.parseInt(userInput());
            } catch (NumberFormatException e){
                displayText(PLEASE_NUMBER);
            }
            if (character < 1 || character > 5) {
                displayText("Choose between characters from 1 to 5");
                response = Character.NOT_ASSIGNED;
                continue;
            }
            response = controller.setCharacter(character);
            if(response == Character.NOT_ASSIGNED)
                displayText("Character already taken, choose another one");
        } while(response == Character.NOT_ASSIGNED);
        displayText("You are" + response);
        ClientContext.get().getCurrentGroup().observe(this);
        controller.startReceiverThread();
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
        String input = userInput();
        while(!input.equals("grab") &&
                !input.equals("run") &&
                !input.equals("0") &&
                !input.equals("1") &&
                !input.equals("2") &&
                !input.equals("6") &&
                !input.equals("4") &&
                !input.equals("5") &&
                !input.equals("3") )
            input = userInput();
        return input;
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

    @Override
    public int chooseWeapon() {
        displayText("Which weapon do you choose?");
        return askNumber();
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
        if(update.toString()!= null) {
            displayText("Update:" + update.toString());
        }
        return null;
    }

    @Override
    public void onStart() {
        displayText("Get ready for A D R E N A L I N E");
        wait = false;
        this.clip.stop();
        this.playMusic("src/resources/Music/Intro.wav");
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
