package gui;

import controller.ClientController;
import model.field.Coordinate;
import model.room.Update;
import model.room.User;
import network.socket.ViewClient;
import view.View;

import javax.swing.SwingUtilities;
import java.rmi.RemoteException;

public class ViewGui implements View {

    private MainFrame gui;
    private volatile boolean wait=true;
    private ViewClient viewCli;
    private ClientController controller;

    public ViewGui(){
        gui = new MainFrame(null);
        viewCli = new ViewClient();
    }

    public void setClientController(ClientController controller){
        this.controller = controller;
    }

    public void run() {
        try {
            controller.run();
        } catch(RemoteException e){
            //nothing
        }
    }

    private String userInput() {
        return gui.getJLabelText();
    }

    @Override
    public void onJoin(User u) {
        viewCli.onJoin(u);
    }

    @Override
    public void onLeave(User u) {
        viewCli.onLeave(u);
    }

    @Override
    public void onStart() {
        gui.initGUI();
        wait = false;
    }

    @Override
    public void onUpdate(Update update) {

    }

    @Override
    public Coordinate getCoordinate() {
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

    @Override
    public void displayText(String text) {
        gui.setConsole(text);
    }

    @Override
    public void chooseUsernamePhase() throws RemoteException{
        viewCli.setClientController(controller);
        viewCli.chooseUsernamePhase();
    }

    @Override
    public void chooseGroupPhase() throws RemoteException{
        viewCli.chooseGroupPhase();
    }

    @Override
    public void chooseCharacterPhase() throws RemoteException{
        viewCli.chooseCharacterPhase();
    }

    @Override
    public void setWait(boolean equalsTo) {
        this.wait = wait;
    }

    @Override
    public void waitingPhase() {
        while(wait);
    }

    @Override
    public Integer spawnPhase() {
        Integer spawnNumber = null;
        while (spawnNumber == null){
            try{
                spawnNumber = Integer.parseInt(userInput());
            }catch (NumberFormatException e){
                displayText("Please insert a Number");
                spawnNumber = null;
            }
        } return spawnNumber;
    }

    @Override
    public String movePhase() {
        return null;
    }

    @Override
    public Boolean reloadPhase() {
        return null;
    }

    @Override
    public int askNumber() {
        try{
            return Integer.parseInt(userInput());
        }catch (NumberFormatException e){
            displayText("Please insert a Number");
            return this.askNumber();
        }
    }

}