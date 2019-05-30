package model.field;

import model.Board;
import model.GameContext;
import model.decks.AmmoTile;
import model.decks.Weapon;
import model.enums.Color;

public class AmmoSquare extends Square{
    private AmmoTile ammo;

    public AmmoSquare() {
        super();
        this.ammo = null;
    }

    public AmmoSquare(Color color, Coordinate coord) {
        super(color, coord);
    }

    @Override
    public AmmoTile getGrabbable() {
        return ammo;
    }

    @Override
    public void setGrabbable(Board board) {
        ammo = board.getAmmosLeft().pickCard();
    }


    //questo non è utilizzabile nell'ammo square,
    //poichè solo lo spawn square può contenere armi
    @Override
    public void addGrabbable(Weapon weapon, int groupID) {
        this.ammo = GameContext.get().getGame(groupID).getBoard().getAmmosLeft().pickCard();
    }
}
