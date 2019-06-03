package model.moves;

import model.exception.NothingGrabbableException;
import model.Player;
import model.room.Update;
import network.commands.Response;
import network.commands.response.AskInput;
import org.jetbrains.annotations.Nullable;

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
     * //TODO non so bene come mai ritorni ciò, ma funziona con le ammotiles??
     */
    @Override
    public Response execute(Player p, int groupID) {
        if(p.getCurrentPosition().getGrabbable()==null) { throw new NothingGrabbableException(); }
        p.getUser().receiveUpdate(new Update(
                    "Insert the weapon you want to pick:" +  p.getCurrentPosition().getGrabbable().toString()));
        return new AskInput("weapon choose");
    }
}
