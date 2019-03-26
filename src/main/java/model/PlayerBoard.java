package model;

public class PlayerBoard {
    private int[] damage;
    private int[] marks;
    private int deaths;
    private int playerId;
    private boolean marksOnly;

    //Costruttore
    public PlayerBoard(int idGiocatore) {
        //Inizializzo a 0 damage e marks
        this.damage = new int[12];
        this.marks = new int[12];
        for (int i=0; i<12; i++) {
            this.damage[i] = 0;
            this.marks[i] = 0;
        }
        this.deaths = 0;
        this.playerId = idGiocatore;

    }//Costruttore

    //setters
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public void setDamage(int[] damage) {
        this.damage = damage;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setMarksOnly(boolean marksOnly) {
        this.marksOnly = marksOnly;
    }

    //getters

    public boolean isMarksOnly() {
        return marksOnly;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int[] getMarks() {
        return marks;
    }

    public int[] getDamage() {
        return damage;
    }

    public int getDeaths() {
        return deaths;
    }
}
