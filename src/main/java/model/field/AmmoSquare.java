package model.field;

import model.Board;
import model.decks.AmmoTile;
import model.enums.Color;

public class AmmoSquare extends Square{
    private AmmoTile ammo;

    public AmmoSquare(Color color) {
        super(color);
    }

    public AmmoTile getGrabbable() {
        return ammo;
    }

    @Override
    public void setGrabbable(Board board){
        this.ammo=board.getAmmosLeft().pickRandomCard();
    }

}
