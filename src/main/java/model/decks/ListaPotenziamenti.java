package model.decks;

public enum ListaPotenziamenti {
    MIRINO_G("Mirino", 'g', 1),
    MIRINO_B("Mirino", 'b', 2),
    MIRINO_R("Mirino", 'r', 3),
    RAGGIO_CINETICO_G("Raggio Cinetico", 'g', 4);

    private String nome;
    private char munizione;
    private int numero;

    ListaPotenziamenti(String nome,char munizione, int num){
        this.nome = nome;
        this.munizione = munizione;
        this.numero = num;
    }

    public int getNumero() {
        return numero;
    }
    //giocatore.mossa()
}