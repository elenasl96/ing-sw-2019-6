package model.moves;

import exception.NotEnoughAmmoException;
import model.Ammo;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the ammos payment move
 */
public class Pay implements Move {
    /**
     * The Ammos that are up for payment
     */
    private List<Ammo> ammos = new ArrayList<Ammo>();

    /**
     * The method, with a nested forEachRemaining() iteration, confronts the player's ammos
     *  with the Pay object's ammos and then removes them from the player's ammos pile.
     * @param p                         the Player that has to pay the ammos
     * @throws NotEnoughAmmoException   if p doesn't have enough ammo
     */
    public void execute(Player p) throws NotEnoughAmmoException {
        if(!p.getAmmos().containsAll(this.ammos))throw new NotEnoughAmmoException();
        else {
            p.getAmmos().removeAll(this.ammos);
        }
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }
}