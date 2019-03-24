package model;

import model.carte.Armi;
import model.carte.Potenziamenti;

public class Giocatore {
    private String nome;
    private int id;
    private Personaggio pg;
    private int[] posizioneCorrente;
    private int faseTurno;
    private int[] munizioni;
    private Potenziamenti[] potenziamenti;
    private Armi[] armi;
    private Plancia plancia;
    private int punti;
    private String fraseAdEffetto;
    private int adrenalinaLevel;
    private int pilaPunti;
    private boolean primoGiocatore;

    //Costruttore
    public Giocatore(Tabellone tab){
        //aumento il numero di giocatori e gli do l'id
        int temp = tab.getNumeroGiocatori();
        temp++;
        this.id = temp;
        tab.setNumeroGiocatori(temp);
        //Inizializzo la plancia col mio idGiocatore
        this.plancia = new Plancia(this.id);
        
        //altra roba che forse non serve
        this.adrenalinaLevel = 0;
        this.pilaPunti = 0;
    }//Costruttore Giocatore(Tabellone)

    //setter
    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void setPotenziamenti(Potenziamenti[] potenziamenti) {
        this.potenziamenti = potenziamenti;
    }

    public void setPosizioneCorrente(int[] posizioneCorrente) {
        this.posizioneCorrente = posizioneCorrente;
    }

    public void setPlancia(Plancia plancia) {
        this.plancia = plancia;
    }

    public void setPilaPunti(int pilaPunti) {
        this.pilaPunti = pilaPunti;
    }

    public void setPg(Personaggio pg) {
        this.pg = pg;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMunizioni(int[] munizioni) {
        this.munizioni = munizioni;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFraseAdEffetto(String fraseAdEffetto) {
        this.fraseAdEffetto = fraseAdEffetto;
    }

    public void setFaseTurno(int faseTurno) {
        this.faseTurno = faseTurno;
    }

    public void setArmi(Armi[] armi) {
        this.armi = armi;
    }

    public void setAdrenalinaLevel(int adrenalinaLevel) {
        this.adrenalinaLevel = adrenalinaLevel;
    }

    public void setPrimoGiocatore(boolean primoGiocatore) {
        this.primoGiocatore = primoGiocatore;
    }

    //getter
    public String getNome() {
        return nome;
    }

    public String getFraseAdEffetto() {
        return fraseAdEffetto;
    }

    public Potenziamenti[] getPotenziamenti() {
        return potenziamenti;
    }

    public Plancia getPlancia() {
        return plancia;
    }

    public Personaggio getPg() {
        return pg;
    }

    public int[] getPosizioneCorrente() {
        return posizioneCorrente;
    }

    public int[] getMunizioni() {
        return munizioni;
    }

    public int getPunti() {
        return punti;
    }

    public int getPilaPunti() {
        return pilaPunti;
    }

    public int getId() {
        return id;
    }

    public int getFaseTurno() {
        return faseTurno;
    }

    public int getAdrenalinaLevel() {
        return adrenalinaLevel;
    }

    public Armi[] getArmi() {
        return armi;
    }

    public boolean isPrimoGiocatore() {
        return primoGiocatore;
    }
}
