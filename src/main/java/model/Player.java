package model;

import eccezioni.ArmaNonTrovata;
import eccezioni.FullPlayerBoardException;

public class Player {
    private String name;
    private char color;
    private int id; //da 0 a numeroGiocatori-1
    private Character character;
    private int[] currentPosition;
    private int phase;
    private int[] ammo;
    private int[] powerup;
    private int[] weapon;
    private Plancia playerBoard;
    private int points;
    private String motto;
    private int adrenalinelevel;
    private int pilaPunti;
    private boolean firstPlayer;
    private boolean dead;
    private boolean[] shootable;




    //Costruttore

    public Player(Board tab){

        //aumento il numero di giocatori e gli do l'id
        int temp = tab.getPlayersNumber();
        this.id = temp;
        temp++;


        tab.setPlayersNumber(temp);
        //Inizializzo la plancia col mio idGiocatore
        this.playerBoard = new Plancia(this.id);

        //Inizializzo le carte in mano al giocatore
        this.ammo = new int[]{1,1,1};
        this.weapon = new int[3];
        
        //altra roba che forse non serve
        this.adrenalinelevel = 0;
        this.pilaPunti = 0;

    }//Costruttore Player(Tabellone)

    //setter
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public void setShootable(boolean[] shootable) {
        this.shootable = shootable;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPowerup(int[] powerup) {
        this.powerup = powerup;
    }

    public void setCurrentPosition(int[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setPlayerBoard(Plancia playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setPilaPunti(int pilaPunti) {
        this.pilaPunti = pilaPunti;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmmo(int[] ammo) {
        this.ammo = ammo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setWeapon(int[] weapon) {
        this.weapon = weapon;
    }

    public void setAdrenalinaLevel(int adrenalinaLevel) {
        this.adrenalinelevel = adrenalinaLevel;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    //getter
    public char getColor() {
        return color;
    }

    public boolean[] getShootable() {
        return shootable;
    }

    public boolean isDead() {
        return dead;
    }

    public String getName() {
        return name;
    }

    public String getMotto() {
        return motto;
    }

    public int[] getPowerup() {
        return powerup;
    }

    public Plancia getPlayerBoard() {
        return playerBoard;
    }

    public Character getCharacter() {
        return character;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public int[] getAmmo() {
        return ammo;
    }

    public int getPoints() {
        return points;
    }

    public int getPilaPunti() {
        return pilaPunti;
    }

    public int getId() {
        return id;
    }

    public int getPhase() {
        return phase;
    }

    public int getAdrenalinaLevel() {
        return adrenalinelevel;
    }

    public int[] getWeapon() {
        return weapon;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }
}
