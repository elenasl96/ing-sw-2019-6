package model.moves;

import exception.InvalidMoveException;
import exception.NothingGrabbableException;
import model.Player;
import model.decks.Grabbable;
import model.decks.Weapon;
import model.field.AmmoSquare;
import model.field.Field;
import model.field.Square;

public class Grab implements Move{

    private Field field;
    private Weapon weapon;

    @Override
    public void execute(Player p) throws InvalidMoveException {
        Grabbable grabbable=p.getCurrentPosition().getGrabbable();

        if(grabbable==null) { throw new NothingGrabbableException(); }

        grabbable.useGrabbable(p);
        //TODO togliere la carta dal terreno di giugo e metterla nel mazzo degli scarti
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

}
