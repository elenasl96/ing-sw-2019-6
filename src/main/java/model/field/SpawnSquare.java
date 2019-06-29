package model.field;

import model.Board;
import model.GameContext;
import model.decks.Grabbable;
import model.decks.Weapon;
import model.decks.WeaponTile;
import model.enums.Color;
import model.room.Update;

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
    public void setGrabbable(int groupID){
        Board board = GameContext.get().getGame(groupID).getBoard();
        for(int i=0; i<3; i++){
            Weapon weapon =  board.getWeaponsLeft().pickCard();
            weapons.addWeapon(weapon);
        }
        updateContentGUI(groupID);
    }

    @Override
    public void addGrabbable(Grabbable weapon, int groupID) {
        this.weapons.addWeapon((Weapon) weapon);
        updateContentGUI(groupID);
    }

    public void updateContentGUI(int groupID) {
        Update update = new Update(null, "tileinsquare");
        update.setData(weapons.toStringForGUI() + ":" + getCoord().toString());
        GameContext.get().getGame(groupID).sendUpdate(update);
    }
}
