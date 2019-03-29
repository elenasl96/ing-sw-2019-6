package model;

import java.util.List;

public class PlayerBoard {
    private List<Player> damage;
    private List<Player> marks;
    private int deaths;
 // private int playerId; -- forse inutile

    public PlayerBoard(List<Player> damage, List<Player> marks, int deaths) {
        this.damage = damage;
        this.marks = marks;
        this.deaths = deaths;
    }

    public List<Player> getDamage() {
        return damage;
    }

    public void setDamage(List<Player> damage) {
        this.damage = damage;
    }

    public List<Player> getMarks() {
        return marks;
    }

    public void setMarks(List<Player> marks) {
        this.marks = marks;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
