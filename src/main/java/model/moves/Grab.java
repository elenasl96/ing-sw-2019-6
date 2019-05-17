package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import exception.NothingGrabbableException;
import model.Player;
import model.decks.Grabbable;
import model.decks.Weapon;
import network.socket.commands.Response;

public class Grab implements Move{


    private Weapon weapon;
    
    @Override
    public void execute(Player p, int gorupId) throws InvalidMoveException {
        Grabbable grabbable=p.getCurrentPosition().getGrabbable();
        if(grabbable==null) { throw new NothingGrabbableException(); }
        grabbable.useGrabbable(p);
        //TODO togliere la carta dal terreno di giugo e metterla nel mazzo degli scarti
    }

    @Override
    public Response handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        return null;//TODO
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

}
