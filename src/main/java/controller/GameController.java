package controller;

import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.Game;
import model.Player;
import model.field.Square;
import model.moves.Damage;
import model.moves.Grab;
import model.moves.Movement;
import model.moves.Run;

public class GameController implements MoveRequestHandler{
    /**
     * the current game
     */
    private Game game;
    private Player currentPlayer;

    public GameController(Game game){
        this.game = game;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    @Override
    public void handle(Run run) throws InvalidMovementException{
        Square destination = null;
        for(Square square: game.getBoard().getField().getSquares()) {
            if (square.getCoord().equals(run.getCoordinate())){
                destination = square;
                break;
            }
        } if(destination == null){
            throw new InvalidMovementException();
        } else {
            run.setDestination(destination);
            if(game.isFinalFrenzy() && !currentPlayer.isFirstPlayer()){
                run.finalFrenzy();
            }
            run.setField(game.getBoard().getField());
        }
    }

    @Override
    public void handle(Movement movement) throws InvalidMoveException {

    }

    @Override
    public void handle(Damage damage) throws InvalidMoveException{

    }

    @Override
    public void handle(Grab grab) throws InvalidMoveException{

    }
}
