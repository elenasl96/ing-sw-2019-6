package model.moves;

import java.io.Serializable;

public abstract class Target{
    private boolean canSee;
    private boolean cardinal;
    private int minDistance;
    private int maxDistance;

    public abstract void addDamage();
    public abstract void addMarks();
}
