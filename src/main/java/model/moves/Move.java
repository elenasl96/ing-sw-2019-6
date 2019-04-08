package model.moves;

import exception.FullMarksException;
import exception.InvalidMoveException;
import exception.NotEnoughAmmoException;
import model.Player;

import java.io.Serializable;

public interface Move extends Serializable{
    void execute(Player p) throws FullMarksException, NotEnoughAmmoException, InvalidMoveException;
}
