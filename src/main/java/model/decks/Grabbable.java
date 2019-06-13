package model.decks;

import model.Player;

import java.io.Serializable;

public interface Grabbable extends Serializable {
    void pickGrabbable(int groupID, int toPick);
    String getGrabbableType();
    boolean isGrabbable(Player player);
}
