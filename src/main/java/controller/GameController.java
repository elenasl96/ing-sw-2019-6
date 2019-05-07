package controller;

import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.Game;
import model.Player;
import model.decks.Powerup;
import model.enums.Phase;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.*;
import model.room.Update;

import java.util.Optional;

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
        currentPlayer.setPhase(Phase.SPAWN);
        Update update = new Update("It's "+currentPlayer.getName()+"'s turn");
        System.out.println(">>> Sending broadcast update from GameController: "+update.toString());
        game.sendUpdate(update);
        System.out.println(">>> CurrentPlayer:" + currentPlayer.getUser().getUserID());
        currentPlayer.getUser().receiveUpdate(new Update(currentPlayer, true));
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public boolean isMyTurn (Player player){
        return player.equals(currentPlayer);
    }


    public Update possibleMoves(Player player) {
        StringBuilder content = new StringBuilder();
        switch (player.getPhase()){
            case FIRST: case SECOND:
                content.append("These are the moves you can choose\n");
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
                    content.append("\nPowerups:\n").append(player.getPowerups());
                }
                break;
            case RELOAD:
                content.append("You can reload:\n").append(player.getWeapons());
                break;
            default:
                break;
        }
        return new Update(content.toString());
    }

    public void setSpawn(Player player, int spawn) {
        Powerup discarded;
        if(isMyTurn(player) &&
                currentPlayer.getPhase().equals(Phase.SPAWN) &&
                player.getPowerups().get(spawn)!=null){
            Optional<SpawnSquare> optional = this.game.getBoard().getField().getSpawnSquares().stream()
                    .filter(ss -> ss.getColor().equals(currentPlayer.getPowerups().get(spawn).getAmmo().getColor()))
                    .findFirst();
            optional.ifPresent(currentPlayer::setCurrentPosition);
            discarded = currentPlayer.getPowerups().remove(spawn);
            //set phase wait to current player and send update
            currentPlayer.setPhase(Phase.WAIT);
            currentPlayer.getUser().receiveUpdate(new Update(player, true));
            //go to next player and set phase
            this.currentPlayer = this.game.getPlayers().next();
            System.out.println("CURRENT PLAYER" + currentPlayer);
            if(currentPlayer.equals(game.getPlayers().get(0))) currentPlayer.setPhase(Phase.FIRST);
            else currentPlayer.setPhase(Phase.SPAWN);
            //send updates
            game.sendUpdate(new Update(player.getName()+ " discarded "+ discarded.toString()+"\n"
                    + player.getName() + " spawn is set in " + player.getCurrentPosition().toString()));
            currentPlayer.getUser().receiveUpdate(new Update(currentPlayer, true));
        } else player.getUser().receiveUpdate(new Update("not working spawn:" + player.toString()+ "," + this.currentPlayer.toString()));
    }

    public Update getSpawn(Player player) {
        player.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        player.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        System.out.println(player.getPowerups().toString());
        return new Update("Choose spawn point from: " + player.getPowerups().toString());
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
        System.out.println("The new square is "+movement.getCoordinate());
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
        //TODO
    }

    @Override
    public void handle(Grab grab) throws InvalidMoveException{
        //TODO
    }

}
