package model;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private List<Player> damage;
    private List<Player> marks;
    private int deaths;

    public PlayerBoard() {
        this.damage = new ArrayList<Player>();
        this.marks = new ArrayList<>();
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
