package controller;

import model.Board;
import model.Game;
import model.Player;
import model.decks.PowerupDeck;
import model.enums.Phase;

import model.field.Square;

import pattern.Observer;


public class Controller implements Observer {
    /**
     * the current game
     */
    private Game game;

    public Controller(){
        this.game = new Game();
    }

    public Player chooseFirstPlayer() {
        //Wait players for a common response
        return null;
    }

    public void startGame(){
        //todo Add number of skulls
        game.getBoard().getField().getSquares().forEach(square-> {
            square.setGrabbable(game.getBoard());
        });
        chooseFirstPlayer();
    }

    /*public boolean isValid(Movement move, Player player) {
        if (player.getPhase() != Phase.WAIT) {
            switch (move) {
                case RUN:
                    return isValidMove(3, player, destination);
                case MOVE:
                    return isValidMove(1, player, destination);
                    break;
                case GRAB:
                    return isValidMove(1, player, destination) && isValidGrab();
                    break;
            }
        }
    }

    public boolean isValidMove(int maxSteps, Player player, Square destination){

    }
    */

    public void update(String command){

    }

    public void play(){
        Player current;
        for(int i=0; i < game.getNumberPlayers(); i++) {
            if(game.getPlayers().get(i).isFirstPlayer()){
                break;
            }
            game.getPlayers().iterator().next();
        }

        while(!game.isDone()) {
            current = game.getPlayers().iterator().next();
            current.setPhase(Phase.FIRST);
            playPhase(current);
            current.setPhase(Phase.SECOND);
            playPhase(current);
            current.setPhase(Phase.RELOAD);
            endPhase(current);
            current.setPhase(Phase.WAIT);
        }
    }

    private void endPhase(Player player) {
    }

    private void playPhase(Player player) {

    }

}
