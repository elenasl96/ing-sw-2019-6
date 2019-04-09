package model.moves;

import model.Player;

import static java.lang.Math.min;

public class Damage implements Move{
    private Player playerDamaged;
    private int numDamage;

    public void execute(Player playerDamaging){
        playerDamaged.getPlayerBoard().addDamage(playerDamaging, min(numDamage, playerDamaged.getPlayerBoard().getNumDamageLeft()));
    }

    public Damage(){
    }

    public Damage(Player playerDamaged, int numDamage){
        this.playerDamaged = playerDamaged;
        this.numDamage = numDamage;
    }
}
