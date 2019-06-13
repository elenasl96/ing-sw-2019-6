package model.decks;

import model.Ammo;
import model.GameContext;
import model.Player;
import model.enums.WeaponStatus;
import model.exception.NotEnoughAmmoException;
import model.moves.Pay;
import model.room.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponTile implements Grabbable {
    private String grabbableType = "weapon";
    private List<Weapon> weapons;

    public WeaponTile() {
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void setWeapons(List<Weapon> weapons) { this.weapons = weapons; }

    public List<Weapon> getWeapons() { return weapons; }

    public String getStringIdWeapons() {
        StringBuilder stringbuilder = new StringBuilder("");
        for(Weapon w: weapons) {
            stringbuilder.append(w.getName()+";");
        }
        return stringbuilder.toString().substring(0,stringbuilder.toString().length()-1);
    }

    public String getGrabbableType() {
        return grabbableType;
    }

    @Override
    public boolean isGrabbable(Player p) {
        for(Weapon w: weapons){
            int costSize = w.getEffectsList().get(0).getCost().size();
            for(int i=1; i<costSize; i++){
                if(Collections.frequency(
                        p.getAmmos(), w.getEffectsList().get(0).getCost().get(i))>=
                        Collections.frequency(w.getEffectsList().get(0).getCost().subList(1, costSize),
                                w.getEffectsList().get(0).getCost().get(i)))
                    return true;
            }
        }
        return false;
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        Weapon weapon = this.weapons.get(toPick);
        ArrayList<Ammo> ammosToPay = new ArrayList<>();

        //Try to pay the weapon
        if(weapon.getStatus().equals(WeaponStatus.PARTIALLY_LOADED)) {
            if (weapon.getEffectsList().get(0).getCost().size() <= 1) {
                weapon.setStatus(WeaponStatus.LOADED);
            } else {
                for (int i = 1; i < weapon.getEffectsList().get(0).getCost().size(); i++) {
                    ammosToPay.add(weapon.getEffectsList().get(0).getCost().get(i));
                }
                Pay pay = new Pay(ammosToPay);
                pay.execute(GameContext.get().getGame(groupID).getCurrentPlayer(), groupID);
                weapon.setStatus(WeaponStatus.LOADED);
            }
        }
        //Pick weapon
        GameContext.get().getGame(groupID).getCurrentPlayer()
                .getWeapons().add(weapon); //Throws IndexOutOfBoundsException if toPick inserted by the user was >2
        //Removes the weapon picked up
        this.weapons.remove(toPick);

        //Send updates
        Update update;
        update = new Update(GameContext.get().getGame(groupID).getCurrentPlayer().getName()+
                " picked "+weapon.toString(),"weapons");
        update.setData(weapon.getName());
        GameContext.get().getGame(groupID).getCurrentPlayer().receiveUpdate(update);
        GameContext.get().getGame(groupID).sendUpdate(new Update(GameContext.get().getGame(groupID).getCurrentPlayer().getName()+
                " picked "+weapon.toString(),"updateconsole"));

        //Refills the weapon
        refillWeapon(groupID);
    }

    private void refillWeapon(int groupID) {
        Weapon newWeapon = GameContext.get().getGame(groupID).getBoard().getWeaponsLeft().pickCard();
        this.weapons.add(newWeapon);
        GameContext.get().getGame(groupID).sendUpdate(new Update(
                "Weapon replaced by "+ newWeapon.toString(), "updateconsole"));
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