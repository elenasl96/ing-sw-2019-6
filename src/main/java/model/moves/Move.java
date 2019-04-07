package model.moves;

import exception.NotEnaughAmmoException;
import model.Player;

public interface Move {
    void execute(Player p) throws NotEnaughAmmoException;
}
