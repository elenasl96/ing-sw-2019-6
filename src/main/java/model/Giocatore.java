package model;

import eccezioni.EccezionePlanciaPiena;

public class Giocatore {
    private String nome;
    private char colore;
    private int id; //da 0 a numeroGiocatori-1
    private Personaggio pg;
    private int[] posizioneCorrente;
    private int faseTurno;
    private int[] munizioni;
    private int[] potenziamenti;
    private int[] armi;
    private Plancia plancia;
    private int punti;
    private String fraseAdEffetto;
    private int adrenalinaLevel;
    private int pilaPunti;
    private boolean primoGiocatore;
    private boolean morto;
    private boolean[] vedo;

    public void giocaPotenziamento(Giocatore g2, int numPotenziamento, int[] cubettoMirino, char direzione, int numeroPassi, int[] destinazione, Tabellone tab) throws EccezionePlanciaPiena{
        switch(numPotenziamento){
            case 1:
            case 2:
            case 3:
                //effetto di Mirino
                //Pago un cubetto di qualsiasi colore, do un danno extra all'avversario
                if(this.haiColpi(cubettoMirino) && g2.getPlancia().isSoloMarchi())
                    this.paga(cubettoMirino);
                    this.danno(1, g2);
                break;
            case 4:
            case 5:
            case 6:
                //effetto di RaggioCinetico
                int[] pos = g2.getPosizioneCorrente();
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
                if(this.vedo[g2.getId()]){
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

    public void marchia (int numeroMarchi, Giocatore g2) {
        //TODO
    }

    public void muovi(int[] destinazione) {
        //TODO
    }

    public void scartaPotenziamento(int numPotenziamento, Tabellone tab){
        int[] temp;
        temp = tab.getScartiPotenziamenti();
        int i = 0;
        while (temp[i] != 0){
            i++;
        }
        temp[i] = numPotenziamento;
        tab.setScartiPotenziamenti(temp);
    }

    public boolean haiColpi(int[] cubettiRichiesti) {
        for(int i=0; i<3; i++){
            if(this.munizioni[i] < cubettiRichiesti[i]){
                return false;
            }
        }
        return true;
    }

    public void danno(int numeroDanni, Giocatore colpito) throws EccezionePlanciaPiena {
        //NON corrisponde allo sparare:
        //  aggiunge solo le goccioline di sangue, non controlla i marchi
        if(colpito.getPlancia().getDanni()[11] != 0 ){
            throw new EccezionePlanciaPiena();
        }
        colpito.getPlancia().setSoloMarchi(false);
        for (int i = 0; i < numeroDanni; i++){
            //TODO
        }
    }

    public void paga(int[] cubetti){
        //TODO
    }
    //Costruttore
    public Giocatore(Tabellone tab){
        //aumento il numero di giocatori e gli do l'id
        int temp = tab.getNumeroGiocatori();
        this.id = temp;
        temp++;
        tab.setNumeroGiocatori(temp);
        //Inizializzo la plancia col mio idGiocatore
        this.plancia = new Plancia(this.id);
        
        //altra roba che forse non serve
        this.adrenalinaLevel = 0;
        this.pilaPunti = 0;
    }//Costruttore Giocatore(Tabellone)

    //setter
    public void setMorto(boolean morto) {
        this.morto = morto;
    }

    public void setColore(char colore) {
        this.colore = colore;
    }

    public void setVedo(boolean[] vedo) {
        this.vedo = vedo;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void setPotenziamenti(int[] potenziamenti) {
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

    public void setArmi(int[] armi) {
        this.armi = armi;
    }

    public void setAdrenalinaLevel(int adrenalinaLevel) {
        this.adrenalinaLevel = adrenalinaLevel;
    }

    public void setPrimoGiocatore(boolean primoGiocatore) {
        this.primoGiocatore = primoGiocatore;
    }

    //getter
    public char getColore() {
        return colore;
    }

    public boolean[] getVedo() {
        return vedo;
    }

    public boolean isMorto() {
        return morto;
    }

    public String getNome() {
        return nome;
    }

    public String getFraseAdEffetto() {
        return fraseAdEffetto;
    }

    public int[] getPotenziamenti() {
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

    public int[] getArmi() {
        return armi;
    }

    public boolean isPrimoGiocatore() {
        return primoGiocatore;
    }
}
