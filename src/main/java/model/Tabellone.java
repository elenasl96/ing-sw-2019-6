package model;

import model.carte.*;

import java.util.Random;

public class Tabellone {
    private int numeroGiocatori;
    private char[][] campo;
    private int numeroTeschi;
    private int[] tracciatoColpoMortale;
    private int[] armiRestanti;
    private int[] munizioniRestanti;
    private int[] scartiMunizioni;
    private int[] potenziamentiRestanti;
    private int[] scartiPotenziamenti;
    private Random r;
    //Metodo che chiamo quando i giocatori sono tutti loggati
    // e hanno scelto già il personaggio, il campo,
    // il punto di spawn da cui partire, il giocatore che inizia per primo,
    // il numero dei teschi della partita
    public void inizioPartita(int numeroCampo, int numeroTeschi){
        this.numeroTeschi = numeroTeschi;
        //Selezione campo
        if (numeroCampo == 1) {
            this.campo = new char[][]{
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
            this.potenziamentiRestanti[i] = i+1;
            this.potenziamentiRestanti[i+12] = i+1;
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
    public Tabellone(){
        this.numeroGiocatori = 0;
        r = new Random();
        //TODO
        //riempio il mazzo di munizioni
        //riempio il mazzo di armi
        //riempio il mazzo dei potenziamenti
    }

    //setters
    public void setTracciatoColpoMortale(int[] tracciatoColpoMortale) {
        this.tracciatoColpoMortale = tracciatoColpoMortale;
    }

    public void setScartiMunizioni(int[] scartiMunizioni) {
        this.scartiMunizioni = scartiMunizioni;
    }

    public void setPotenziamentiRestanti(int[] potenziamentiRestanti) {
        this.potenziamentiRestanti = potenziamentiRestanti;
    }

    public void setNumeroTeschi(int numeroTeschi) {
        this.numeroTeschi = numeroTeschi;
    }

    public void setNumeroGiocatori(int numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    public void setMunizioniRestanti(int[] munizioniRestanti) {
        this.munizioniRestanti = munizioniRestanti;
    }

    public void setCampo(char[][] campo) {
        this.campo = campo;
    }

    public void setArmiRestanti(int[] armiRestanti) {
        this.armiRestanti = armiRestanti;
    }

    public void setScartiPotenziamenti(int[] scartiPotenziamenti) {
        this.scartiPotenziamenti = scartiPotenziamenti;
    }

    //getters
    public int[] getPotenziamentiRestanti() {
        return potenziamentiRestanti;
    }

    public int[] getScartiMunizioni() {
        return scartiMunizioni;
    }

    public int[] getMunizioniRestanti() {
        return munizioniRestanti;
    }

    public int[] getTracciatoColpoMortale() {
        return tracciatoColpoMortale;
    }

    public int getNumeroTeschi() {
        return numeroTeschi;
    }

    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }

    public char[][] getCampo() {
        return campo;
    }

    public int[] getArmiRestanti() {
        return armiRestanti;
    }

    public int[] getScartiPotenziamenti() {
        return scartiPotenziamenti;
    }
}
