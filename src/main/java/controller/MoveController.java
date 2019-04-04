package controller;

import exception.InvalidMoveException;
import exception.NotPlayersTurnException;
import model.Game;
import model.Player;
import model.enums.Phase;

public class MoveController {
    /**
     * The current game
     */
    private Game game;

    /**
     * Contructor
     * @param game  the current game
     */
    public MoveController(Game game){
        this.game = game;
    }

    /**
     *  Check's if the move's valid
     * @param player    the player requesting the move
     * @param move      the move
     *
     */
    public void command(Player player, Moves move){
        try{
            isValid(player, move);
        } catch (NotPlayersTurnException npt){
            //TODO print no es tu tuerno
        } catch (InvalidMoveException im){
            //TODO print no se puede
        }
    }


    public void isValid(Player player, Moves move) throws NotPlayersTurnException, InvalidMoveException {


    }
    /**
     *  Checks if it's player's turn
     * @param player    the player that invokes a move
     * @throws NotPlayersTurnException if player's in WAIT phase
     */

    public void isValid(Player player) throws NotPlayersTurnException {
        if (player.getPhase() != Phase.WAIT) {
            throw new NotPlayersTurnException();
        }
    }

}
