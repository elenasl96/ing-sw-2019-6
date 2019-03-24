package model;

import model.carte.*;

public class Tabellone {
    private int numeroGiocatori;
    private char[][] campo;
    private int numeroTeschi;
    private int[] tracciatoColpoMortale;
    private Armi[] armiRestanti;
    private Munizioni[] munizioniRestanti;
    private Munizioni[] scartiMunizioni;
    private Potenziamenti[] potenziamentiRestanti;



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
    //costruttore
    public Tabellone(){
        this.numeroGiocatori = 0;
        //TODO
        //riempio il mazzo di munizioni
        //riempio il mazzo di armi
        //riempio il mazzo dei potenziamenti
    }

    //setters
    public void setTracciatoColpoMortale(int[] tracciatoColpoMortale) {
        this.tracciatoColpoMortale = tracciatoColpoMortale;
    }

    public void setScartiMunizioni(Munizioni[] scartiMunizioni) {
        this.scartiMunizioni = scartiMunizioni;
    }

    public void setPotenziamentiRestanti(Potenziamenti[] potenziamentiRestanti) {
        this.potenziamentiRestanti = potenziamentiRestanti;
    }

    public void setNumeroTeschi(int numeroTeschi) {
        this.numeroTeschi = numeroTeschi;
    }

    public void setNumeroGiocatori(int numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    public void setMunizioniRestanti(Munizioni[] munizioniRestanti) {
        this.munizioniRestanti = munizioniRestanti;
    }

    public void setCampo(char[][] campo) {
        this.campo = campo;
    }

    public void setArmiRestanti(Armi[] armiRestanti) {
        this.armiRestanti = armiRestanti;
    }

    //getters
    public Potenziamenti[] getPotenziamentiRestanti() {
        return potenziamentiRestanti;
    }

    public Munizioni[] getScartiMunizioni() {
        return scartiMunizioni;
    }

    public Munizioni[] getMunizioniRestanti() {
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

    public Armi[] getArmiRestanti() {
        return armiRestanti;
    }
}
