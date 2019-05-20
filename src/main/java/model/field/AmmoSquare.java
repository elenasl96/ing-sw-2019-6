package model.field;

import model.Board;
import model.decks.AmmoTile;
import model.enums.Color;

public class AmmoSquare extends Square{
    private AmmoTile ammo;

    public AmmoSquare() {
        super();
        this.ammo = null;
    }

    public AmmoSquare(Boolean canSee, Boolean cardinal, Integer minDistance, Integer maxDistance){

    }

    public AmmoSquare(Color color, Coordinate coord) {
        super(color, coord);
    }

    public AmmoTile getGrabbable() {
        return ammo;
    }

    public void addGrabbable(Board board){
        this.ammo=board.getAmmosLeft().pickCard();
    }

}
