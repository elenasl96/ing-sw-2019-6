package model.decks;

import model.Board;
import model.Player;

public interface Grabbable {
    void pickGrabbable(Player player, Board board);
}
