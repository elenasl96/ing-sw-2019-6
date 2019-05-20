package controller;

import exception.InvalidMoveException;
import exception.InvalidMovementException;
import model.GameContext;
import model.Player;
import model.decks.Powerup;
import model.decks.Weapon;
import model.enums.Phase;
import model.field.SpawnSquare;
import model.field.Square;
import model.moves.*;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.response.AskInput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.enums.Phase.SPAWN;

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
                content.append("\nThese are the moves you can choose");
                if(!GameContext.get().getGame(groupID).isFinalFrenzy()){
                    content.append("\n||RUN||");
                    if(!player.getCurrentPosition().isEmpty())
                        content.append("\n||GRAB||");
                    if(!player.getWeapons().isEmpty())
                        content.append("\n||SHOOT||").append(player.weaponsToString());
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
                if(!player.getPowerups().isEmpty())
                    content.append("\n||POWERUPS||").append(player.powerupsToString());
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
                GameContext.get().getGame(groupID).getCurrentPlayer().getPhase().equals(SPAWN) &&
                spawn >= 0 &&
                spawn < player.getPowerups().size()){
            Optional<SpawnSquare> optional = GameContext.get().getGame(groupID).getBoard().getField().getSpawnSquares().stream()
                    .filter(ss -> ss.getColor().equals(GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups().get(spawn).getAmmo().getColor()))
                    .findFirst();
            optional.ifPresent(GameContext.get().getGame(groupID).getCurrentPlayer()::setCurrentPosition);
            discarded = player.getPowerups().remove(spawn);
            //set phase wait to current player and send update
            GameContext.get().getGame(groupID).getCurrentPlayer().setDead(false);
            GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.WAIT);
            GameContext.get().getGame(groupID).getCurrentPlayer().getUser().receiveUpdate(new Update(player, true));
            //go to next player and set phase
            GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
            System.out.println("CURRENT PLAYER" + GameContext.get().getGame(groupID).getCurrentPlayer());
            GameContext.get().getGame(groupID).sendUpdate(new Update("It's " + GameContext.get().getGame(groupID).getCurrentPlayer()+"'s turn"));
            if(GameContext.get().getGame(groupID).getCurrentPlayer().equals(GameContext.get().getGame(groupID).getPlayers().get(0))) GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(Phase.FIRST);
            else GameContext.get().getGame(groupID).getCurrentPlayer().setPhase(SPAWN);
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

    public synchronized Update getSpawn(Player player, int groupID) {
        if(!player.isDead()){
            player.getPowerups().add(GameContext.get().getGame(groupID)
                    .getBoard().getPowerupsLeft().pickCard());
        }
        player.getPowerups().add(GameContext.get().getGame(groupID)
                .getBoard().getPowerupsLeft().pickCard());
        System.out.println(">>> Powerups picked up: "+player.getPowerups().toString());
        return new Update(">>> Choose spawn point from:" + player.powerupsToString());
    }

    public void updatePhase(int groupID){
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        //go to next player and set phase
        switch(GameContext.get().getGame(groupID).getCurrentPlayer().getPhase()) {
            case FIRST:
                player.setPhase(Phase.SECOND);
                break;
            case SECOND:
                player.setPhase(Phase.RELOAD);
                break;
            case RELOAD:
                player.setPhase(Phase.WAIT);
                player.getUser().receiveUpdate(new Update(player, true));
                this.updatePoints(groupID);
                GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().next());
                player = GameContext.get().getGame(groupID).getCurrentPlayer();
                if(player.isDead()){
                    player.setPhase(SPAWN);
                }
                else {
                    player.setPhase(Phase.FIRST);
                }
                break;
            default:
                break;
        }
        player.getUser().receiveUpdate(new Update(player, true));
    }

    private void updatePoints(int groupID) {
        for(Player p: GameContext.get().getGame(groupID).getPlayers()){
            if(p.isDead()){
                //add the player dead on the killshotTrack
                GameContext.get().getGame(groupID).getBoard().addKillshot(p);
                //overkill: readd the same player and mark the overkiller
                if(p.isOverkilled()){
                    GameContext.get().getGame(groupID).getBoard().addKillshot(p);
                    p.getPlayerBoard().getDamage().get(11).getPlayerBoard().addMarks(p,1);
                }
                //give 1 point to the player who gives the first blood
                p.getPlayerBoard().getDamage().get(0).addPoints(1);
                //decrease the maximum number of points for the killShot of the player
                p.addDeath();
                //counting points from other damages
                Map<Player, Long> playerBoardSorted =  p.getPlayerBoard().getDamage().stream()
                        .collect(Collectors.groupingBy(pm->pm, TreeMap::new,Collectors.counting()));
                int pointsToAdd = 8;
                for (Player pm : playerBoardSorted.keySet()) {
                    pm.addPoints(pointsToAdd);
                    if(pointsToAdd<=2) pointsToAdd=1;
                    else pointsToAdd -= 2;
                }
            }
        }
    }

    public void playWeapon(int groupId, Player player, Weapon weapon){
        //TODO
    }

    public void playPowerup(int groupId, Player player, Powerup powerup){
        //TODO
    }

    public int receiveInput(int input){
        return input;
    }
    // Moves handling

    public synchronized void handle() {
        //TODO
    }

    @Override
    public synchronized void handle(Run run, int groupID) throws InvalidMoveException{
        run.setMaxSteps(3);
        if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            run.setMaxSteps(4);
        }
        handle(run.getMovement(), groupID);
        //go to next player and set phase
    }

    @Override
    public synchronized Response handle(MoveAndGrab moveAndGrab, int groupID) throws InvalidMoveException {
        moveAndGrab.setMaxSteps(1);
        if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            moveAndGrab.setMaxSteps(4);
        }
        handle(moveAndGrab.getMovement(), groupID);
        return new AskInput("moveAndGrab");

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
        AskInput toAsk = new AskInput("damage");
        toAsk.append("Who do you want to shoot to?");
        return toAsk;

    }

    @Override
    public synchronized Response handle(Grab grab, int groupID) throws InvalidMoveException{
        //TODO
        return null;
    }

}
