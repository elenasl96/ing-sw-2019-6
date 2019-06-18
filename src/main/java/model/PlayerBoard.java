package model;

import model.exception.FullMarksException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

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
    public int addDamage(Player pg, int numDamage) {
        int damagesSuffered = 0;
        for(int i=0; i<min(numDamage, this.getNumDamageLeft()); i++){
            this.damage.add(pg);
            damagesSuffered ++;
        }
        return damagesSuffered;
    }

    /**
     *
     * @return number of endured damages
     */
    public int getNumDamageLeft(){
        return 12-this.damage.size();
    }

    public List<Player> getMarks() {
        return marks;
    }

    /**
     * @param pg player who is marking
     * @param numMarks number of marks assigned
     */
    public int addMarks(Player pg, int numMarks) {
        int marksSuffered = 0;
        int frequency = Collections.frequency(this.marks, pg);
        for (int i=0; i<min(numMarks, 3 - frequency); i++){
            this.marks.add(pg);
            marksSuffered++;
        }
        return marksSuffered;
    }

    int getDeaths() {
        return deaths;
    }


    void addDeath() {
        this.deaths++;
    }
}
