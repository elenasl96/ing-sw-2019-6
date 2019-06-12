package model.decks;


import model.Ammo;
import model.moves.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Powerup implements Serializable {
    private String name;
    private Ammo ammo;
    private List<Move> moves = new ArrayList<>();

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
        return "Name: " + name + "\nAmmo: " + ammo.getColor().getName() + "\n" +
                "=========================";
    }

    public String getName() {
        return name;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
