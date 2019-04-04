package model.decks;


import model.Player;
import model.field.Square;
import model.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Powerup {
    private String name;
    private Ammo ammo;
    private List<Move> moves;

    public Powerup(String name, Ammo ammo){
        this.name = name;
        this.ammo = ammo;
        this.moves = new ArrayList<>();
    }

    public void addMove(Move move){
        this.moves.add(move);
    }
}
