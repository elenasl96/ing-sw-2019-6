package controller;

import model.Board;
import model.Game;
import model.Player;
import model.enums.Phase;
import pattern.Observer;


public class Controller implements Observer {

    public void run(Game game, Player player){

    }


    public boolean isValid(Move move, Player player) {
        if (player.getPhase() != Phase.WAIT) {
            switch (move) {
                case RUN:
                    break;
                case GRAB:
                    break;
                case
            }
        }
    }

    public void update(){}
}
