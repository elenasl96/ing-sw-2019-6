package model.moves;

import controller.ShootController;
import model.decks.Weapon;
import model.decks.WeaponTile;
import model.exception.*;
import model.Player;
import model.room.Update;
import network.commands.Response;
import network.commands.response.AskInput;

import static controller.GameController.cardsToString;

/**
 * The move representing the grabbing of something on current player's square
 * either an ammo tile or a weapon
 * It does not include the movements implied in the MoveAndGrab
 * @see Move
 * @see MoveAndGrab
 */
public class Shoot implements Move{
    int weapon = -1;

    /**
     *
     * @param p     the player shooting
     * @param groupID   the current game groupID to access the Game from the GameContext
     * @return  new AskInput request
     */
    @Override
    public Response execute(Player p, int groupID) throws InvalidMoveException {
        if(weapon == -1){
            WeaponTile weaponTile = new WeaponTile();
            for(Weapon w : p.getWeapons()) {
                if (w.isLoaded())
                    weaponTile.addWeapon(w);
            }
            if(weaponTile.getWeapons().isEmpty())
                throw new UnloadedWeaponException();
            Update update = new Update("Insert the weapon you want to use:" +
                cardsToString(p.getWeapons(), 0), "choosecard");
            update.setData(weaponTile.toStringForGUI());
            p.receiveUpdate(update, groupID);
            return new AskInput("weaponToPlay");
        }else{
            String fields = ShootController.get().prepareWeapon(p, weapon+"", groupID);
            Update update = new Update(fields,"fillfields");
            update.setData(fields);
            p.receiveUpdate(update, groupID);
            return new AskInput("fillFields");
        }
    }
}