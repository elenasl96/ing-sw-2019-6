package model.moves;

import model.PlayerBoard;
import java.util.List;

public abstract class Target{
    private boolean canSee;
    private boolean cardinal;
    private int minDistance;
    private int maxDistance;

    public abstract List<PlayerBoard> getPlayerBoard(int groupId);
}
