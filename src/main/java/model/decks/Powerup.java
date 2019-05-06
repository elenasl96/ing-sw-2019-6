package model.decks;


import model.Ammo;
import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;

public class Powerup implements Serializable {
    private String name;
    private Ammo ammo;
    private ArrayList<Move> moves = new ArrayList<Move>();

    public Powerup(String name, Ammo ammo){
        this.name = name;
        this.ammo = ammo;
    }

    public void addMove(Move move){
        this.moves.add(move);
    }

    public Ammo getAmmo() {
        return ammo;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nAmmo: " + ammo.getColor().getName() + "\nMosse:\n";
    }
}
