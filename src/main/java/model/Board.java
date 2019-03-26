package model;

import java.util.Random;

public class Board {
    private int playersNumber;
    private char[][] field;
    private int skullsNumber;
    private int[] killshotTrack;
    private int[] weaponsLeft;
    private int[] ammosLeft;
    private int[] ammosLeftover;
    private int[] powerupsLeft;
    private int[] powerupsLeftover;
    private Random r;
    //Metodo che chiamo quando i giocatori sono tutti loggati
    // e hanno scelto già il personaggio, il field,
    // il punto di spawn da cui partire, il giocatore che inizia per primo,
    // il numero dei teschi della partita
    public void inizioPartita(int numeroCampo, int numeroTeschi){
        this.skullsNumber = numeroTeschi;
        //Selezione field
        if (numeroCampo == 1) {
            this.field = new char[][]{
                    {'b', 'o', 'b', 'o', 'B', 'm', 'm'},
                    {'p', 'm', 'm', 'm', 'p', 'm', 'm'},
                    {'R', 'o', 'r', 'o', 'r', 'p', 'g'},
                    {'m', 'm', 'p', 'm', 'm', 'm', 'o'},
                    {'m', 'm', 'w', 'o', 'w', 'p', 'G'}};
        } else if (numeroCampo == 2){
            //TODO
        } else {
            //TODO
        }

        //TODO mischio le armi e le munizioni
        //schiero le 3 armi per punto di spawn
    }

    public void creaMazzoPotenziamenti(){
        for(int i=0; i < 12; i++){
            this.powerupsLeft[i] = i+1;
            this.powerupsLeft[i+12] = i+1;
        }//lo crea ordinato [1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9]
    }

    public void creaMazzoArmi(){
        //TODO
    }

    public void creaMazzoMunizioni(){
        //TODO
    }

    public void mischia(int[] mazzo){
        for(int i=0; i<mazzo.length; i++) {
            int temp = mazzo[i];
            int ran = r.nextInt(mazzo.length-1);
            mazzo[i] = mazzo[ran];
            mazzo[ran] = temp;
        }
    }

    //costruttore
    public Board(){
        this.playersNumber = 0;
        r = new Random();
        //TODO
        //riempio il mazzo di munizioni
        //riempio il mazzo di armi
        //riempio il mazzo dei potenziamenti
    }

    //setters
    public void setKillshotTrack(int[] killshotTrack) {
        this.killshotTrack = killshotTrack;
    }

    public void setAmmosLeftover(int[] ammosLeftover) {
        this.ammosLeftover = ammosLeftover;
    }

    public void setPowerupsLeft(int[] powerupsLeft) {
        this.powerupsLeft = powerupsLeft;
    }

    public void setSkullsNumber(int skullsNumber) {
        this.skullsNumber = skullsNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public void setAmmosLeft(int[] ammosLeft) {
        this.ammosLeft = ammosLeft;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public void setWeaponsLeft(int[] weaponsLeft) {
        this.weaponsLeft = weaponsLeft;
    }

    public void setPowerupsLeftover(int[] powerupsLeftover) {
        this.powerupsLeftover = powerupsLeftover;
    }

    //getters
    public int[] getPowerupsLeft() {
        return powerupsLeft;
    }

    public int[] getAmmosLeftover() {
        return ammosLeftover;
    }

    public int[] getAmmosLeft() {
        return ammosLeft;
    }

    public int[] getKillshotTrack() {
        return killshotTrack;
    }

    public int getSkullsNumber() {
        return skullsNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public char[][] getField() {
        return field;
    }

    public int[] getWeaponsLeft() {
        return weaponsLeft;
    }

    public int[] getPowerupsLeftover() {
        return powerupsLeftover;
    }
}
