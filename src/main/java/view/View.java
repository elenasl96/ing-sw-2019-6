package view;

import controller.ClientController;
import model.field.Coordinate;
import model.room.ModelObserver;

import java.rmi.RemoteException;

public interface View extends ModelObserver {
    Coordinate getCoordinate();

    void displayText(String insert_a_valid_move);

    void chooseUsernamePhase() throws RemoteException;

    void chooseGroupPhase() throws RemoteException;

    void chooseCharacterPhase() throws RemoteException;

    void setWait(boolean equalsTo);

    void waitingPhase();

    Integer spawnPhase();

    String movePhase();

    Boolean reloadPhase();

    int askNumber();

    void setClientController(ClientController controller);

    int chooseWeapon();

    String userInput();

    String askEffects();

    void playMusic(String s);
}
