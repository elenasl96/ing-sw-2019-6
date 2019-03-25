package model.carte;

public enum ListaPotenziamenti {
    MIRINOG("Mirino", 'g', 1),
    MIRINOB("Mirino", 'b', 2),
    MIRINOR("Mirino", 'r', 3),
    RAGGIOCINETICOG("Raggio Cinetico", 'g', 4);

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