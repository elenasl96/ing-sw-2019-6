package view;

import controller.ClientController;
import model.field.Coordinate;
import model.room.ModelObserver;

import java.io.IOException;
import java.rmi.RemoteException;

//TODO javadoc
public interface View extends ModelObserver {
    Coordinate getCoordinate();

    void displayText(String insertAValidMove);

    String chooseUsernamePhase() throws IOException;

    void chooseGroupPhase() throws IOException;

    void chooseCharacterPhase() throws IOException;

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
