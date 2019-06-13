package model.decks;

import model.Ammo;
import model.GameContext;
import model.Player;
import model.enums.Color;
import model.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the AmmoTile object, divided in AmmoTile with an extra ammo
 * and AmmoTile with and extra powerup.
 * Extends Grabbable since it is a Grabbable Object.
 * Composes the ammos deck.
 * @see Grabbable
 * @see AmmoDeck
 * @see Ammo
 * @see Powerup
 */
public abstract class AmmoTile implements Grabbable {
    /**
     * The list of two ammos included with every ammo tile
     */
    String grabbableType = "ammo";
    private List<Ammo> ammos = new ArrayList<>();

    /**
     * Constructor for ammo tiles with ammo, that has three colors of ammo
     * @param color1    first color of the ammo tile
     * @param color2    second color of the ammo tile
     * @param color3    color of the extra ammo
     */
    AmmoTile(Color color1, Color color2, Color color3){
        ammos.add(new Ammo (color1));
        ammos.add(new Ammo (color2));
        ammos.add(new Ammo (color3));
    }

    /**
     * Constructor for ammo tiles with powerup, that only has two colors of ammo
     * @param color1    first color of the ammo tile
     * @param color2    second color of the ammo tile
     */
    AmmoTile(Color color1, Color color2){
        Ammo ammoTemp1 = new Ammo (color1);
        Ammo ammoTemp2 = new Ammo (color2);
        ammos.add(ammoTemp1);
        ammos.add(ammoTemp2);
    }

    @Override
    public String getGrabbableType() {
        return this.grabbableType;
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    @Override
    public boolean isGrabbable(Player player) {
        return true;
    }
}

//TODO javadoc
class AmmoTileWithAmmo extends AmmoTile{
    /**
     * Calls the constructor of the superclass AmmoTile that creates a list of 3 ammos
     * @param color1    the first ammo color
     * @param color2    the second ammo color
     * @param color3    the third ammo color
     */
    AmmoTileWithAmmo(Color color1, Color color2, Color color3){
        super(color1, color2, color3);
    }

    @Override
    public void pickGrabbable(int groupID, int toPick){
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        Update update = new Update("You grab these ammos: " + player.fillAmmoFromTile(this),"reload");
        update.setData(player.getAmmos().toString().replace("[","").replace("]","")
                .replace(" ","").toLowerCase());
        player.receiveUpdate(update);

    }
}
//TODO javadoc
class AmmoTileWithPowerup extends AmmoTile{
    AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void pickGrabbable(int groupID, int toPick) {
        String ammosGrabbed = GameContext.get().getGame(groupID).getCurrentPlayer()
                .fillAmmoFromTile(this);
        Powerup cardPicked = GameContext.get().getGame(groupID).getBoard().getPowerupsLeft().pickCard();
        GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups()
                .add(cardPicked);
        Update update = new Update("You grab these ammos:" + ammosGrabbed +
                "You pick a new powerup:" + cardPicked.toString(),"powerup");
        update.setData(cardPicked.getName());
        GameContext.get().getGame(groupID).getCurrentPlayer().receiveUpdate(update);
        update = new Update(null,"reload");
        update.setData(GameContext.get().getGame(groupID).getCurrentPlayer().getAmmos().toString().replace("[","").replace("]","")
                .replace(" ","").toLowerCase());
        GameContext.get().getGame(groupID).getCurrentPlayer().receiveUpdate(update);
    }
}
