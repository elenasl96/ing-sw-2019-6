package controller;

import model.Board;
import model.Game;
import model.Player;
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

    public void startGame(){

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

}
