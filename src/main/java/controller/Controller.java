package controller;

import model.Game;
import model.Player;
import model.enums.Phase;

public class Controller{
    /**
     * the current game
     */
    private Game game;

    public Controller(){
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
