package model.moves;

import exception.NotEnaughAmmoException;
import model.Ammo;
import model.Player;

import java.util.ArrayList;

/**
 * Implements the ammos payment move
 */
public class Pay implements Move {
    /**
     * The Ammos that are up for payment
     */
    private ArrayList<Ammo> ammos = new ArrayList<Ammo>();

    /**
     * The method, with a nested forEachRemaining() iteration, confronts the player's ammos
     *  with the Pay object's ammos and then removes them from the player's ammos pile.
     * @param p                         the Player that has to pay the ammos
     * @throws NotEnaughAmmoException   if p doesn't have enough ammo
     */
    public void execute(Player p) throws NotEnaughAmmoException {
        if(!p.getAmmos().containsAll(this.ammos))throw new NotEnaughAmmoException();
        else p.getAmmos().removeAll(this.ammos);
    }

    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }
}
