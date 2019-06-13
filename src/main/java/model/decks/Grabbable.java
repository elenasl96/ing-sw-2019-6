package model.decks;

import java.io.Serializable;

public interface Grabbable extends Serializable {
    void pickGrabbable(int groupID, int toPick);
    String getGrabbableType();
}
