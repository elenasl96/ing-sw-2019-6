package view;

import model.field.Coordinate;
import model.room.ModelObserver;

public interface View extends ModelObserver {
    Coordinate getCoordinate();

    void displayText(String insert_a_valid_move);

    void chooseUsernamePhase();

    void chooseGroupPhase();

    void chooseCharacterPhase();

    void setWait(boolean equalsTo);

    void waitingPhase();

    Integer spawnPhase();

    String movePhase();

    Boolean reloadPhase();

    int askNumber();
}
