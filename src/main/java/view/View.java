package view;

import controller.ClientController;
import model.field.Coordinate;
import model.room.ModelObserver;

import java.rmi.RemoteException;

//TODO javadoc
public interface View extends ModelObserver {
    Coordinate getCoordinate();

    void displayText(String insertAValidMove);

    String chooseUsernamePhase() throws RemoteException;

    void chooseGroupPhase() throws RemoteException;

    void chooseCharacterPhase() throws RemoteException;

    void setWait(boolean equalsTo);

    void waitingPhase();

    Integer spawnPhase();

    String movePhase();

    Boolean reloadPhase();

    int askNumber();

    void setClientController(ClientController controller);

    String userInput();

    String askEffects();

    void playMusic(String s);

    String cardChoose();

    Boolean choosePowerup();

    String fillFields();
}
