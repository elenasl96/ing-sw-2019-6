package view.gui;

import controller.ClientController;
import model.field.Coordinate;
import model.room.Update;
import model.room.User;
import network.ClientContext;
import view.ViewClient;
import view.View;

import java.rmi.RemoteException;

public class ViewGui implements View {

    private MainFrame gui;
    private volatile boolean wait=true;
    private ViewClient viewCli;
    private ClientController controller;

    public ViewGui(){
        gui = new MainFrame();
        gui.initGUI();
        viewCli = new ViewClient();
    }

    public void setClientController(ClientController controller){
        this.controller = controller;
        gui.setClientController(controller);
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
        return gui.askEffects();
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
        System.out.println("Gui starting");
        viewCli.onStart();
        gui.setVisible(true);
        System.out.println("Gui started");
        wait = false;
    }

    @Override
    public void onUpdate(Update update) {
        String[] data;
        if(update.getMove()!=null) {
            switch (update.getMove()) {
                case "movement":
                    data = update.getData().split(";");
                    gui.updateMap(Integer.parseInt(data[0])-1, data[1]);
                    break;
                case "updateconsole":
                    gui.setConsole(update.toString());
                    break;
                case "reload":
                    data = update.getData().split(",");
                    gui.clearAmmoPanels();
                    for(String s: data) {
                        gui.changeAmmoPanel(s);
                        System.out.println(s);
                    }
                    gui.updateAmmoPanels();
                    break;
                case "weapons":
                    gui.addWeaponBox(update.getData());
                    break;
                case "powerup":
                    gui.addPuBox(update.getData());
                    break;
                case "disablebutton":
                    data = update.getData().split(";");
                    gui.disableButtons(data);
                    gui.setBackGroundTurn(true);
                    break;
                case "choosecard":
                    data = update.getData().split(";");
                    gui.createPopUp(data);
                    break;
                case "turnbar":
                    gui.setBackGroundTurn(false);
                    break;
                case "layouteffect":
                    data = update.getData().split(";");
                    gui.chooseEffectPopUp(data[0], Integer.parseInt(data[1]));
                    break;
                case "damages":
                    data = update.getData().split(";");
                    for(int i=0;i<Integer.parseInt(data[0]);i++) {
                        gui.addDropPlayerBoard(Integer.parseInt(data[1]));
                    }
                    break;
                case "markers":
                    data = update.getData().split(";");
                    for(int i=0;i<Integer.parseInt(data[0]);i++) {
                        gui.addMarkerPlayerBoard(Integer.parseInt(data[1]));
                    } break;
                case "weaponsAsked":
                    try {
                        controller.askWeapons(update.getData());
                    } catch (RemoteException e) {
                        System.out.println(">>> An error occurred");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public Coordinate getCoordinate() {
        String[] alphabetCoordinate = gui.getCoordinate().split(" ");
        return new Coordinate(alphabetCoordinate[0].toUpperCase().charAt(0), Integer.parseInt(alphabetCoordinate[1]));
    }

    @Override
    public void displayText(String insertAValidMove) {
        gui.setConsole(insertAValidMove);
    }

    @Override
    public String chooseUsernamePhase() throws RemoteException{
        viewCli.setClientController(controller);
        gui.setPlayerNameLabel(viewCli.chooseUsernamePhase().toUpperCase());
        ClientContext.get().getCurrentUser().listenToMessages(this);
        System.out.println(ClientContext.get().getCurrentUser().getUpdateObservers());
        return null;
    }

    @Override
    public void chooseGroupPhase() throws RemoteException{
        viewCli.chooseGroupPhase();
    }

    @Override
    public void chooseCharacterPhase() throws RemoteException{
        viewCli.chooseCharacterPhase();
        ClientContext.get().getCurrentGroup().observe(this);
    }

    @Override
    public void setWait(boolean wait) {
        this.wait = wait;
    }

    @Override
    public void waitingPhase() {
        while(wait);
    }

    @Override
    public Integer spawnPhase() {
        gui.setBackGroundTurn(true);
        int val = Integer.parseInt(gui.cardChoose());
        gui.setBackGroundTurn(false);
        return val;
    }

    @Override
    public String movePhase() {
        return gui.getMove();
    }

    @Override
    public Boolean reloadPhase() {
        gui.yesNoPopUp();
        return (gui.yesNoChoose().equals("yes"));
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

    @Override
    public String cardChoose() {
        return gui.cardChoose();
    }
}