package model.decks;


import model.Ammo;
import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The powerup cards of the game, contains a list of moves and an ammo
 * @see Move
 * @see Ammo
 */
public class Powerup implements Serializable {
    private String name;
    private Ammo ammo;
    private List<Move> moves = new ArrayList<>();

    public Powerup(String name, Ammo ammo){
        this.name = name;
        this.ammo = ammo;
    }

    void addMove(Move move){
        this.moves.add(move);
    }

    public Ammo getAmmo() {
        return ammo;
    }

    @Override
    public String toString(){
        return "Name: " + name.substring(0,name.length()-1) + "\nAmmo: " + ammo.getColor().getName() + "\n" +
                "=========================";
    }

    public String getName() {
        return name;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
