package model.moves;

import model.PlayerBoard;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Target{
    private boolean canSee;
    private boolean cardinal;
    private int minDistance;
    private int maxDistance;

    public abstract void addDamage();
    public abstract void addMarks();

    public abstract ArrayList<PlayerBoard> getPlayerBoard();
}