package controller;

import model.Game;
import model.Player;
import model.enums.Phase;
import model.field.Field;

/**
 * It checks if a move is valid.
 */

public class ValidController {
    /**
     *  Checks if it's player's turn
     * @param game      the current game
     * @param player    the player that invokes a move
     * @return   true   if it's player's turn
     */
    public boolean isValid(Game game, Player player){
        if (player.getPhase() != Phase.WAIT) {
            return true;
        }
        else return false;
    }

    public boolean isValid(Game game, Player player, Moves move){
        //TODO
        return true;
    }
}
