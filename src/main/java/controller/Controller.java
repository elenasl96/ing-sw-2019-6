package controller;

import model.Board;
import model.Game;
import model.Player;
import model.enums.Phase;

import model.field.Square;

import pattern.Observer;


public class Controller implements Observer {
    private Square destination;


    /*public boolean isValid(Move move, Player player) {
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
    public void update(){
    }

}
