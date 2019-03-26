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

    public void giocaArma(int numArma, int effetto) throws ArmaNonTrovata {
        //Controlla se l'arma è posseduta dal giocatore
        int i=0;
        while(numArma != weapon[i] && i<3){
            i++;
        }
        if(i==3) throw new ArmaNonTrovata();

        //Controlla se il giocatore ha danni>=6 poichè avrebbe
        // 1 movimento bonus prima di utilizzare l'arma
        if(this.playerBoard.getDanni()[6]!=0){
            //TODO richiede alla view del giocatore la scelta giocatore
        }

        //Esegue l'effetto scelto dal giocatore
        switch (effetto){
            case 1:
                //TODO Esegue effetto base
            case 2:
                //TODO Esegue effetto opzionale
            case 3:
                //TODO Esegue effetto alternativo
            default:
                break;
        }
    }

    public void giocaPotenziamento(Player g2, int numPotenziamento, int[] cubettoMirino, char direzione, int numeroPassi, int[] destinazione, Board tab) throws FullPlayerBoardException {

        switch(numPotenziamento){
            case 1:
            case 2:
            case 3:
                //effetto di Mirino
                //Pago un cubetto di qualsiasi color, do un danno extra all'avversario
                if(this.haiColpi(cubettoMirino) && g2.getPlayerBoard().isSoloMarchi())
                    this.paga(cubettoMirino);
                    this.danno(1, g2);
                break;
            case 4:
            case 5:
            case 6:
                //effetto di RaggioCinetico
                int[] pos = g2.getCurrentPosition();
                //Aumenta di 1 o 2 la posizione del giocatore avversario nella stessa direzione
                switch (direzione){
                    case 'n':
                        pos[0] = pos[0] - numeroPassi;
                        g2.muovi(pos);
                        break;
                    case 'e':
                        pos[1] = pos[1] + numeroPassi;
                        g2.muovi(pos);
                        break;
                    case 's':
                        pos[0] = pos[0] + numeroPassi;
                        g2.muovi(pos);
                        break;
                    case 'o':
                        pos[1] = pos[1] - numeroPassi;
                        g2.muovi(pos);
                        break;
                    default:
                        break;
                }
                break;
            case 7:
            case 8:
            case 9:
                //Effetto di Granata Venom
                //Marchia un giocatore che POSSO VEDERE
                if(this.shootable[g2.getId()]){
                    this.marchia(1, g2);
                }
                break;
            case 10:
            case 11:
            case 12:
                //Effetto di Teletrasporto
                //Mi teletrasporta alla destinazione
                g2.muovi(destinazione);
                break;
            default:
                break;
        }
        this.scartaPotenziamento(numPotenziamento, tab);
    }

    public void marchia (int numeroMarchi, Player g2) {
        //TODO
    }

    public void muovi(int[] destinazione) {
        //TODO
    }

    public void scartaPotenziamento(int numPotenziamento, Board tab){
        int[] temp;
        temp = tab.getPowerupsLeftover();
        int i = 0;
        while (temp[i] != 0){
            i++;
        }
        temp[i] = numPotenziamento;
        tab.setPowerupsLeftover(temp);
    }

    public boolean haiColpi(int[] cubettiRichiesti) {
        for(int i=0; i<3; i++){
            if(this.ammo[i] < cubettiRichiesti[i]){
                return false;
            }
        }
        return true;
    }

    public void danno(int numeroDanni, Player colpito) throws FullPlayerBoardException {
        //NON corrisponde allo sparare:
        //  aggiunge solo le goccioline di sangue, non controlla i marchi
        if(colpito.getPlayerBoard().getDanni()[11] != 0 ){
            throw new FullPlayerBoardException();
        }
        colpito.getPlayerBoard().setSoloMarchi(false);
        for (int i = 0; i < numeroDanni; i++){
            //TODO
        }
    }

    public void paga(int[] cubetti){
        //TODO
    }
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
