package controller;

import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.GameContext;
import model.Player;
import model.decks.Powerup;
import model.enums.Phase;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.*;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.response.AskInput;

import java.util.Optional;

/**
 * SINGLETON (SERVER SIDE)
 * Elaborates the ServerController requests
 * Every method is called receiving the game groupID, to call it from the GameContext
 */

public class GameController implements MoveRequestHandler{
    private static GameController instance;
    private GameController() {
    }

    public static synchronized GameController get() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    boolean isMyTurn (Player player, int groupID){
        return player.equals(GameContext.get().getGame(groupID).getCurrentPlayer());
    }


    public synchronized Update possibleMoves(Player player, int groupID) {
        StringBuilder content = new StringBuilder();
        switch (player.getPhase()){
            case FIRST: case SECOND:
                content.append("\nThese are the moves you can choose\n");
                if(!GameContext.get().getGame(groupID).isFinalFrenzy()){
                    content.append("run\n" +
                            "grab\n" +
                            "shoot\n");
                    content.append("If you want to play a powerup, write \"powerup\"");
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
                    content.append(player.powerupsToString(player.getPowerups()));
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

    public synchronized void setSpawn(Player player, int spawn, int groupID) {
        Powerup discarded;
        if(isMyTurn(player, groupID) &&
                GameContext.get().getGame(groupID).getCurrentPlayer().getPhase().equals(Phase.SPAWN) &&
                spawn >= 0 &&
                spawn < player.getPowerups().size()
        ){
            Optional<SpawnSquare> optional = GameContext.get().getGame(groupID).getBoard().getField().getSpawnSquares().stream()
                    .filter(ss -> ss.getColor().equals(GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups().get(spawn).getAmmo().getColor()))
                    .findFirst();
            optional.ifPresent(GameContext.get().getGame(groupID).getCurrentPlayer()::setCurrentPosition);
            discarded = GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups().remove(spawn);
            //set phase wait to current player and send update
            GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.WAIT);
            GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update(player, true));
            //go to next player and set phase
            GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
            System.out.println("CURRENT PLAYER" + GameContext.get().getGame(groupID).getCurrentPlayer());
            GameContext.get().getGame(groupID).sendUpdate(new Update("It's " + GameContext.get().getGame(groupID).getCurrentPlayer()+"'s turn"));
            if(GameContext.get().getGame(groupID).getCurrentPlayer().equals(GameContext.get().getGame(groupID).getPlayers().get(0))) GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.FIRST);
            else GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.SPAWN);
            //send updates
            GameContext.get().getGame(groupID).sendUpdate(new Update(
                    "\n>>> Player " + player.getName()+ " discarded:\n" +
                        "==========Powerup========\n"
                        + discarded.toString()
                        +"\n>>> Player " + player.getName() + " spawn in " + player.getCurrentPosition().toString()));
            GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update(GameContext.get().getGame(groupID).getCurrentPlayer(), true));
        } else {
            player.getUser().receiveUpdate(new Update(player, true));
            player.getUser().receiveUpdate(new Update("not working spawn:" + player.toString()+ "," + GameContext.get().getGame(groupID).getCurrentPlayer().toString()));
        }
    }

    public synchronized Update getFirstTimeSpawn(Player player, int groupID) {
        if(player.getPowerups().isEmpty()){
            player.getPowerups().add(GameContext.get().getGame(groupID)
                    .getBoard().getPowerupsLeft().pickCard());
            player.getPowerups().add(GameContext.get().getGame(groupID)
                    .getBoard().getPowerupsLeft().pickCard());
            System.out.println(">>> "+player.getPowerups().toString());
        }
        return new Update(">>> Choose spawn point from:" + player.powerupsToString(player.getPowerups()));
    }

    public int receiveInput(int input){
        return input;
    }
    // Moves handling
    @Override
    public synchronized void handle(Run run, int groupID) throws InvalidMoveException{
        run.setMaxSteps(3);
        if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            run.setMaxSteps(4);
        }
        handle(run.getMovement(), groupID);
    }

    @Override
    public synchronized Response handle(MoveAndGrab moveAndGrab, int groupID) throws InvalidMoveException {
        moveAndGrab.setMaxSteps(1);
        if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            moveAndGrab.setMaxSteps(4);
        }
        handle(moveAndGrab.getMovement(), groupID);

        return new AskInput();
    }

    @Override
    public synchronized void handle(Movement movement, int groupID) throws InvalidMoveException {
        System.out.println("The square inserted is: "+movement.getCoordinate());
        Square destination = null;
        //Check if the coordinate is valid
        for(Square square: GameContext.get().getGame(groupID).getBoard().getField().getSquares()) {
            if (square.getCoord().equals(movement.getCoordinate())){
                destination = square;
                break;
            }
        } if(destination == null){
            throw new InvalidMovementException();
        } else {
            movement.setDestination(destination);
            movement.setField(GameContext.get().getGame(groupID).getBoard().getField());
        }
    }

    @Override
    public synchronized Response handle(DamageEffect damage, int groupID) throws InvalidMoveException{
        AskInput toAsk = new AskInput();
        toAsk.append("Who do you want to shoot to?");
        return toAsk;

    }

    @Override
    public synchronized Response handle(Grab grab, int groupID) throws InvalidMoveException{
        //TODO
        return null;
    }

}
