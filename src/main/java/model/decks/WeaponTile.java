package model.decks;

import exception.InvalidMoveException;
import exception.NothingGrabbableException;
import model.Board;
import model.GameContext;
import model.Player;
import model.room.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponTile implements Grabbable, Serializable {
    private List<Weapon> weapons;

    public WeaponTile() {
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    @Override
    public void pickGrabbable(int groupID) throws InvalidMoveException{
        throw new InvalidMoveException("No Ammo to grab here!");
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) throws NothingGrabbableException {
        GameContext.get().getGame(groupID).getCurrentPlayer().getWeapons().add(this.weapons.get(toPick));
        //Refills the weapon
        GameContext.get().getGame(groupID).sendUpdate(new Update(
                GameContext.get().getGame(groupID).getCurrentPlayer().getName()+
                        " picked "+this.weapons.get(toPick).toString()));
        Weapon newWeapon = GameContext.get().getGame(groupID).getBoard().getWeaponsLeft().pickCard();
        GameContext.get().getGame(groupID).sendUpdate(new Update(
                "Weapon replaced by "+ newWeapon.toString()));
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int count = 0;
        for(Weapon w: weapons){
            string.append("\nID: " + count).append(w);
            count ++;
        }
        return string.toString();
    }
}