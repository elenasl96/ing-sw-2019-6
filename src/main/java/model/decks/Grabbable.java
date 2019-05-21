package model.decks;

import exception.InvalidMoveException;
import model.Board;
import model.Player;

public interface Grabbable {
    void pickGrabbable(int groupID, int toPick);
}
