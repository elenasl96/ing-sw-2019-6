package model;

import model.decks.CardEffect;

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
    int addDamage(Player pg, int numDamage) {
        int damagesSuffered = 0;
        for(int i=0; i<min(numDamage, this.getNumDamageLeft()); i++){
            this.damage.add(pg);
            damagesSuffered ++;
        }
        return damagesSuffered;
    }

    /**
     * @param playerMarking the player who marked
     * @return  the number of marks that will be converted in damage
     */
    int deleteMarks(Player playerMarking) {
        List<Player> marksToDelete = new ArrayList<>();
        for(Player player : marks){
            if(player.equals(playerMarking))
                marksToDelete.add(player);
        }
        marks.removeAll(marksToDelete);
        return marksToDelete.size();
    }

    /**
     * @return number of endured damages
     */
    private int getNumDamageLeft(){
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
        if (frequency + numMarks > 3) {
            for (int i = 0; i < 3 - frequency; i++) {
                this.marks.add(pg);
                marksSuffered++;
            }
        } else {
            for (int i = 0; i < numMarks; i++) {
                this.marks.add(pg);
                marksSuffered++;
            }
        }
        return marksSuffered;
    }

    int getDeaths() {
        return deaths;
    }


    void addDeath() {
        this.deaths++;
    }

    /**
     * @return true if the player was damaged in the last turn
     */
    public boolean wasDamaged() {
        if(!damage.isEmpty()) {
            Player lastPlayerDamaging = damage.get(damage.size() - 1);
            for (CardEffect c : lastPlayerDamaging.getCurrentCardEffects()) {
                if (c.doesDamage())
                    return true;
            }
        }
        return false;
    }
}
