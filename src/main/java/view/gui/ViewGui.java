package view.gui;

import controller.ClientController;
import model.field.Coordinate;
import model.room.Update;
import model.room.User;
import view.ViewClient;
import network.commands.Response;
import view.View;

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

    @Override
    public int chooseWeapon() {
        //TODO
        return 0;
    }

    public void run() {
        try {
            controller.run();
        } catch(RemoteException e){
            //nothing
        }
    }

    public String userInput() {
        return gui.getJLabelText();
    }

    @Override
    public String askEffects() {
        return null;
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
    public Response onUpdate(Update update) {

        String data[];

        switch(update.getMove())
        {
            case "movement":
                data = update.getData().split(";");
                gui.updateMap(Integer.parseInt(data[0]), data[1]);
                break;
            case "updateconsole":
                gui.setConsole(update.toString());
                break;
            case "reload": {
                data = update.getData().split(",");
                gui.clearAmmoPanels();
                for(int i=0;i<data.length;i++)
                {
                    gui.changeAmmoPanel(data[i]);
                }
            }
            case "weapons": //TODO
            default: break;
        }

        return null;
    }

    @Override
    public Coordinate getCoordinate() {
        return viewCli.getCoordinate();
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
        gui.setBackGroundTurn(false);
        while(wait);
        gui.setBackGroundTurn(true);
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
        return gui.getMove();
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

    @Override
    public int askNumber() {
        try{
            return Integer.parseInt(userInput());
        }catch (NumberFormatException e){
            displayText("Please insert a Number");
            return this.askNumber();
        }
    }

    @Override
    public void playMusic(String s){
        viewCli.playMusic(s);
    }
}