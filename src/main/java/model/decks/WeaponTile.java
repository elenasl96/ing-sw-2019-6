package model.decks;

import model.GameContext;
import model.room.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponTile implements Grabbable {
    private List<Weapon> weapons;

    public WeaponTile() {
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void setWeapons(List<Weapon> weapons) { this.weapons = weapons; }

    public List<Weapon> getWeapons() { return weapons; }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        System.out.println(toPick);
        GameContext.get().getGame(groupID).getCurrentPlayer()
                .getWeapons().add(this.weapons.get(toPick)); //Throws IndexOutOfBoundsException if toPick inserted by the user was >2
        //Removes the weapon picked up
        GameContext.get().getGame(groupID).sendUpdate(new Update(
                GameContext.get().getGame(groupID).getCurrentPlayer().getName()+
                        " picked "+this.weapons.get(toPick).toString()));
        this.weapons.remove(toPick);
        //Refills the weapon
        Weapon newWeapon = GameContext.get().getGame(groupID).getBoard().getWeaponsLeft().pickCard();
        GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition().addGrabbable(newWeapon, groupID);
        GameContext.get().getGame(groupID).sendUpdate(new Update(
                "Weapon replaced by "+ newWeapon.toString()));
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int count = 0;
        for(Weapon w: weapons){
            string.append("\nID: ").append(count).append(w);
            count ++;
        }
        return string.toString();
    }
}