package model.moves;
import model.Player;
import network.commands.Response;

import java.io.Serializable;

//TODO javadoc
public interface Move extends Serializable {
    Response execute(Player player, int groupId);
}
