package model;

public class Plancia {
    private int[] danni;
    private int[] marchi;
    private int morti;
    private int idGiocatore;

    //Costruttore
    public Plancia(int idGiocatore) {
        //Inizializzo a 0 danni e marchi
        this.danni = new int[12];
        this.marchi = new int[12];
        for (int i=0; i<12; i++) {
            this.danni[i] = 0;
            this.marchi[i] = 0;
        }
        this.morti = 0;
        this.idGiocatore = idGiocatore;

    }//Costruttore

    //setters
    public void setMorti(int morti) {
        this.morti = morti;
    }

    public void setMarchi(int[] marchi) {
        this.marchi = marchi;
    }

    public void setDanni(int[] danni) {
        this.danni = danni;
    }

    public void setIdGiocatore(int idGiocatore) {
        this.idGiocatore = idGiocatore;
    }

    //getters
    public int getIdGiocatore() {
        return idGiocatore;
    }

    public int[] getMarchi() {
        return marchi;
    }

    public int[] getDanni() {
        return danni;
    }

    public int getMorti() {
        return morti;
    }
}
