package model.decks;

import model.Ammo;
import model.Board;
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

    void replaceAmmoTile(AmmoTile ammoTilePicked, int groupID){
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        //Removes the AmmoTile picked up
        GameContext.get().getGame(groupID).sendUpdate(new Update(player.getName()+
                " picked " + ammoTilePicked.toString(),"updateconsole"));
    }

    @Override
    public String getGrabbableType() {
        return "ammo";
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    @Override
    public boolean isGrabbable(Player player) {
        return true;
    }

    public abstract void init(Board board);
}

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
    public void init(Board board) {
        //vuoto perchè non deve far niente
    }

    /**
     * Picks the ammoTile from the ground, refills te player's ammo with the ammos taken
     * Sends update only to the player.
     * @param groupID   groupID
     * @param toPick    unused in ammo tile, useful only in spawn point
     * @see Player#fillAmmoFromTile(AmmoTile)
     */
    @Override
    public void pickGrabbable(int groupID, int toPick) {
        Player player = GameContext.get().getGame(groupID).getCurrentPlayer();
        Update update = new Update("You grab these ammos: " + player.fillAmmoFromTile(this), "reload");
        update.setData(player.getAmmos().toString().replace("[", "").replace("]", "")
                    .replace(" ", "").toLowerCase());
        replaceAmmoTile(this, groupID);
        player.receiveUpdate(update, groupID);
    }

    @Override
    public String toString(){
        return getAmmos().get(0).getColor() + ", " + getAmmos().get(1).getColor() + ", " + getAmmos().get(2).getColor();
    }
    @Override
    public String toStringForGUI() {
        StringBuilder string = new StringBuilder();
        for(Ammo a: getAmmos()){
            string.append(a.getColor().getName()).append(";");
        }
        if(!string.toString().equals("") && string.length() > 0) return string.toString().toLowerCase()
                .substring(0,string.toString().length()-1);
        return "";
    }
}


class AmmoTileWithPowerup extends AmmoTile{
    private Powerup powerup;

    AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void init(Board board) {
        powerup = board.getPowerupsLeft().pickCard();
    }

    /**
     * Picks the ammoTile from the ground, refills te player's ammo with the ammos taken
     * and picks a random powerup from deck, giving it to the player.
     * Sends update only to the player.
     * @param groupID   groupID
     * @param toPick    unused in ammo tile, useful only in spawn point
     * @see Player#fillAmmoFromTile(AmmoTile)
     */
    @Override
    public void pickGrabbable(int groupID, int toPick) {
        String ammosGrabbed = GameContext.get().getGame(groupID).getCurrentPlayer()
                .fillAmmoFromTile(this);
        GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups()
                .add(powerup);
        Update update = new Update("You grab these ammos:" + ammosGrabbed +
                "You pick a new powerup:" + powerup.toString(),"powerup");
        update.setData(powerup.getName().substring(0,powerup.getName().length()-1));
        GameContext.get().getGame(groupID).getCurrentPlayer().receiveUpdate(update, groupID);
        update = new Update(null,"reload");
        update.setData(GameContext.get().getGame(groupID).getCurrentPlayer().getAmmos().toString().replace("[","").replace("]","")
                .replace(" ","").toLowerCase());
        GameContext.get().getGame(groupID).getCurrentPlayer().receiveUpdate(update, groupID);

        init(GameContext.get().getGame(groupID).getBoard());
    }

    @Override
    public String toStringForGUI() {
        StringBuilder string = new StringBuilder();
        for(Ammo a: getAmmos()){
            string.append(a.getColor().getName()).append(";");
        }
        string.append(powerup.getName()).append(powerup.getAmmo().getColor().getAbbr());
        return string.toString().toLowerCase().replace(" ","");
    }
}
