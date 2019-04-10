package model.field;

import model.Board;
import model.decks.Grabbable;
import model.decks.WeaponTile;
import model.enums.Color;

public class SpawnSquare extends Square{
    private WeaponTile weapons;

    public SpawnSquare(Color color, Coordinate coord) {
        super(color, coord);
    }


    public void setWeapons(WeaponTile weapons) {
        this.weapons = weapons;
    }


    @Override
    public Grabbable getGrabbable() {
        return weapons;
    }

    @Override
    public void setGrabbable(Board board){
        for(int i=0; i<3; i++){
            weapons.addWeapon(board.getWeaponsLeft().pickRandomCard());
        }
    }

}
