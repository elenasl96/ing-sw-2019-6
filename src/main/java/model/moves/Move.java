package model.moves;

import exception.InvalidMoveException;
import exception.NotEnoughAmmoException;
import model.Player;

public interface Move {
    void execute(Player p) throws NotEnoughAmmoException, InvalidMoveException;
}
