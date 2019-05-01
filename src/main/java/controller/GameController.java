package controller;

import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.Game;
import model.Player;
import model.enums.Phase;
import model.field.Square;
import model.moves.*;
import model.room.Update;

public class GameController implements MoveRequestHandler{
    /**
     * the current game
     */
    private Game game;
    private Player currentPlayer;

    public GameController(Game game){
        this.game = game;
        currentPlayer = game.getCurrentPlayer();
        //Setting the first player phase to FIRST move
        currentPlayer.setPhase(Phase.FIRST);
        Update update = new Update("It's "+currentPlayer.getName()+"'s turn");
        System.out.println(">>> Sending broadcast update from GameController: "+update.toString());
        game.sendUpdate(update);
        System.out.println("CurrentPlayer:" + currentPlayer.getUser().getUserID());
        currentPlayer.getUser().receiveUpdate(new Update(currentPlayer, true));
    }

    public void lifeCycle(){
        while(!game.isDone()){
            switch(currentPlayer.getPhase()){
                case WAIT:
                    //The currentPlayer must have been just updated,
                    //Triggers him to FirstPhase and send a "it's your turn notification" to the user
                    currentPlayer.setPhase(Phase.FIRST);
                    game.sendUpdate(new Update(currentPlayer));
                    break;
                case FIRST:

                case SECOND:

                case RELOAD:

                default:
                    break;
            }
        }
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    // Moves handling
    @Override
    public void handle(Run run) throws InvalidMoveException{
        run.setMaxSteps(3);
        if(game.isFinalFrenzy() && !currentPlayer.isFirstPlayer()){
            run.setMaxSteps(4);
        }
        handle(run.getMovement());
    }

    @Override
    public void handle(MoveAndGrab moveAndGrab) throws InvalidMoveException {
        moveAndGrab.setMaxSteps(1);
        if(game.isFinalFrenzy() && !currentPlayer.isFirstPlayer()){
            moveAndGrab.setMaxSteps(4);
        }
        handle(moveAndGrab.getMovement());
    }

    @Override
    public void handle(Movement movement) throws InvalidMoveException {
        Square destination = null;
        //Check if the coordinate is valid
        for(Square square: game.getBoard().getField().getSquares()) {
            if (square.getCoord().equals(movement.getCoordinate())){
                destination = square;
                break;
            }
        } if(destination == null){
            throw new InvalidMovementException();
        } else {
            movement.setDestination(destination);
            movement.setField(game.getBoard().getField());
        }
    }

    @Override
    public void handle(Damage damage) throws InvalidMoveException{

    }

    @Override
    public void handle(Grab grab) throws InvalidMoveException{

    }
}
