package model.moves;

import model.exception.NotEnoughAmmoException;
import model.Ammo;
import model.Player;
import model.room.Update;
import network.commands.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the ammos payment move
 */
public class Pay implements Move {
    /**
     * The Ammos that are up for payment
     */
    private List<Ammo> ammos = new ArrayList<>();

    public Pay(){

    }

    public Pay(List<Ammo> ammos){
        this.ammos = ammos;
    }

    /**
     * The method, with a nested forEachRemaining() iteration, confronts the player's ammos
     *  with the Pay object's ammos and then removes them from the player's ammos pile.
     * @param p                         the Player that has to pay the ammos
     * @throws NotEnoughAmmoException   if p doesn't have enough ammo
     */
    public Response execute(Player p, int groupId) {
        for(int i=0; i<this.ammos.size(); i++){
            if(Collections.frequency(p.getAmmos(), this.ammos.get(i))<
                    Collections.frequency(this.ammos, this.ammos.get(i)))
                throw new NotEnoughAmmoException();
        }
        p.getAmmos().removeAll(this.ammos);
        Update update = new Update(null,"reload");
        update.setData(p.getAmmos().toString().replace("[","").replace("]","")
                .replace(" ","").toLowerCase());
        p.receiveUpdate(update);
        return null;
    }


    public List<Ammo> getAmmos() {
        return ammos;
    }
}