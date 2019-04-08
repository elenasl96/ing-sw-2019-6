package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerBoard implements Serializable {
    private List<Player> damage = new ArrayList<>();
    private List<Player> marks = new ArrayList<>();
    private int deaths;

    public PlayerBoard() {
        this.deaths = 0;
    }

    public List<Player> getDamage() {
        return damage;
    }

    /**
     * @param pg player who is attacking
     * @param numDamage number of damages
     */
    public void addDamage(Player pg, int numDamage) {
        for(int i=0; i<numDamage; i++){
            this.damage.add(pg);
        }
    }

    public List<Player> getMarks() {
        return marks;
    }

    /**
     * @param pg player who is marking
     * @param numMarks number of marks assigned
     */
    public void addMarks(Player pg, int numMarks) {
        for (int i=0; i<numMarks; i++){
            this.marks.add(pg);
        }
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        this.deaths++;
    }
}
