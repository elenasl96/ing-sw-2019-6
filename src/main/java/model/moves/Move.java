package model.moves;
import model.Player;
import network.socket.commands.Response;

import java.io.Serializable;

public interface Move extends Serializable {
    Response execute(Player player, int groupId);
}
