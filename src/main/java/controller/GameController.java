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
    Player currentPlayer;

    public GameController(Game game){
        this.game = game;
        currentPlayer = game.getCurrentPlayer();
        //Setting the first player phase to FIRST move
        currentPlayer.setPhase(Phase.FIRST);
        Update update = new Update("It's "+currentPlayer.getName()+"'s turn");
        System.out.println(">>> Sending broadcast update from GameController: "+update.toString());
        game.sendUpdate(update);
        System.out.println(">>> CurrentPlayer:" + currentPlayer.getUser().getUserID());
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

    public boolean isMyTurn (Player player){
        if(player == currentPlayer) return true;
        else return false;
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
        System.out.println("Il square ora is "+movement.getCoordinate());
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

    public Update possibleMoves(Player player) {
        StringBuilder content = new StringBuilder("These are the moves you can choose:");
        if(!this.game.isFinalFrenzy()){
            content.append("run\n" +
                    "grab\n" +
                    "shoot");
        } else {
            if(player.isFirstPlayer()){
                content.append("shoot (move up to 2 squares, reload, shoot)\n" +
                        "grab (move up to 3 squares, grab)");
            } else {
                content.append("shoot (move up to 1 squares, reload, shoot)\n" +
                        "run (move up to 4 squares)\n" +
                        "grab (move up to 2 squares, grab)");
            }
        }
        if(!player.getPowerups().isEmpty()){
            content.append("\npowerup");
        }
        return new Update(content.toString());
    }
}
