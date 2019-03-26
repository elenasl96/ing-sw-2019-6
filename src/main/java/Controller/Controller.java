package Controller;

import eccezioni.ArmaNonTrovata;
import eccezioni.FullPlayerBoardException;
import model.Board;
import model.Player;

public class Controller {


    //Begin player controller
    public void giocaArma(Player pg, int numArma, int effetto, int[] weapon) throws ArmaNonTrovata {
        //Controlla se l'arma è posseduta dal giocatore
        int i=0;
        while(numArma != weapon[i] && i<3){
            i++;
        }
        if(i==3) throw new ArmaNonTrovata();

        //Controlla se il giocatore ha danni>=6 poichè avrebbe
        // 1 movimento bonus prima di utilizzare l'arma
        if(pg.getPlayerBoard().getDanni()[6]!=0){
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

    public void giocaPotenziamento(Player pg, int numPotenziamento, int[] cubettoMirino, char direzione, int numeroPassi, int[] destinazione, Board tab) throws FullPlayerBoardException {

        switch(numPotenziamento){
            case 1:
            case 2:
            case 3:
                //effetto di Mirino
                //Pago un cubetto di qualsiasi color, do un danno extra all'avversario
                if(this.haiColpi(pg,cubettoMirino) && pg.getPlayerBoard().isSoloMarchi())
                    this.paga(pg, cubettoMirino);
                this.danno(1, pg);
                break;
            case 4:
            case 5:
            case 6:
                //effetto di RaggioCinetico
                int[] pos = pg.getCurrentPosition();
                //Aumenta di 1 o 2 la posizione del giocatore avversario nella stessa direzione
                switch (direzione){
                    case 'n':
                        pos[0] = pos[0] - numeroPassi;
                        muovi(pg, pos);
                        break;
                    case 'e':
                        pos[1] = pos[1] + numeroPassi;
                        muovi(pg, pos);
                        break;
                    case 's':
                        pos[0] = pos[0] + numeroPassi;
                        muovi(pg, pos);
                        break;
                    case 'o':
                        pos[1] = pos[1] - numeroPassi;
                        muovi(pg, pos);
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
                if(pg.getShootable()[pg.getId()]){
                    this.marchia(1, pg);
                }
                break;
            case 10:
            case 11:
            case 12:
                //Effetto di Teletrasporto
                //Mi teletrasporta alla destinazione
                muovi(pg, destinazione);
                break;
            default:
                break;
        }
        this.scartaPotenziamento(numPotenziamento, tab);
    }


    public void marchia (int numeroMarchi, Player g2) {
        //TODO
    }

    public void muovi(Player pg, int[] destinazione) {
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

    public boolean haiColpi(Player g2, int[] cubettiRichiesti) {
        for(int i=0; i<3; i++){
            if(g2.getAmmo()[i] < cubettiRichiesti[i]){
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

    public void paga(Player g2, int[] cubetti){
        //TODO
    }

    //Ends player controller

    public Controller(){

    }


}
