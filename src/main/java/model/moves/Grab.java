package model.moves;

import model.decks.WeaponTile;
import model.exception.NotEnoughAmmoException;
import model.exception.NotExistingPositionException;
import model.exception.NothingGrabbableException;
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
public class Grab implements Move{

    /**
     * Throws NothingGrabbableException if there was no grabbable object
     * (example: if the weapons were all taken and none was left)
     * Grabs the grabbable object from p's current position
     * @param p     the player grabbing
     * @param groupID   the current game groupID to access the Game from the GameContext
     * @return  new AskInput request
     */
    @Override
    public Response execute(Player p, int groupID) throws NotExistingPositionException, NotEnoughAmmoException, NothingGrabbableException {
        if(p.getCurrentPosition().isEmpty() ||
                !p.getCurrentPosition().getGrabbable().isGrabbable(p)) {
            throw new NothingGrabbableException();
        }
        if(p.getCurrentPosition().getGrabbable().getGrabbableType().equals("weapon")) {
            Update update = new Update("Insert the weapon you want to pick:" +
                    cardsToString(((WeaponTile) p.getCurrentPosition().getGrabbable()).getWeapons(), 0), "choosecard");
            update.setData(((WeaponTile) p.getCurrentPosition().getGrabbable()).getStringIdWeapons().toLowerCase().toLowerCase().replaceAll(" ", ""));
            p.receiveUpdate(update, groupID);
            return new AskInput("weapon choose");
        }else{
            p.getCurrentPosition().getGrabbable().pickGrabbable(groupID, 0);
            //Remove ammoTile from Field
            p.getCurrentPosition().replaceAmmoTile(groupID);
            p.setPhaseNotDone(false);
            return null;
        }
    }
}