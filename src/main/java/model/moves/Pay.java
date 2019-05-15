package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import exception.NotEnoughAmmoException;
import model.Ammo;
import model.Player;
import network.socket.commands.Response;

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

    /**
     * The method, with a nested forEachRemaining() iteration, confronts the player's ammos
     *  with the Pay object's ammos and then removes them from the player's ammos pile.
     * @param p                         the Player that has to pay the ammos
     * @throws NotEnoughAmmoException   if p doesn't have enough ammo
     */
    public void execute(Player p, int groupId) throws NotEnoughAmmoException {
        for(int i=0; i<this.ammos.size(); i++){
            if(Collections.frequency(p.getAmmos(), this.ammos.get(i))<
                    Collections.frequency(this.ammos, this.ammos.get(i)))
                throw new NotEnoughAmmoException();
        }
        p.getAmmos().removeAll(this.ammos);
    }

    @Override
    public void handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        //TODO
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }
}