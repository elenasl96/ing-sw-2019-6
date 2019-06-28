package model.field;

import model.Board;
import model.decks.Grabbable;
import model.decks.Weapon;
import model.decks.WeaponTile;
import model.enums.Color;

//TODO javadoc
public class SpawnSquare extends Square{
    private WeaponTile weapons;

    public SpawnSquare(Color color, Coordinate coordinate) {
        super(color, coordinate);
        this.weapons = new WeaponTile();
    }

    @Override
    public Grabbable getGrabbable() {
        return weapons;
    }

    @Override
    public void setGrabbable(Board board){
        for(int i=0; i<3; i++){
            Weapon weapon =  board.getWeaponsLeft().pickCard();
            weapons.addWeapon(weapon);
            //TODO qui va inviato tutto alla gui immagino
        }
    }

    @Override
    public void addGrabbable(Grabbable weapon, int groupID) {
        this.weapons.addWeapon((Weapon) weapon);
        //TODO e anche qui
    }
}
