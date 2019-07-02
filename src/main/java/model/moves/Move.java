package model.moves;
import model.Player;
import model.exception.*;
import network.commands.Response;

import java.io.Serializable;

public interface Move extends Serializable {
    Response execute(Player player, int groupId) throws InvalidMoveException;
}
