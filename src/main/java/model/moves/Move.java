package model.moves;
import model.Player;
import model.exception.NotEnoughAmmoException;
import model.exception.NotExistingPositionException;
import model.exception.NotValidDestinationException;
import model.exception.NothingGrabbableException;
import network.commands.Response;

import java.io.Serializable;

public interface Move extends Serializable {
    Response execute(Player player, int groupId) throws NotEnoughAmmoException, NotExistingPositionException, NotValidDestinationException, NothingGrabbableException;
}
