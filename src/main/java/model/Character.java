package model;

public enum Character {
    PG1(":D-STRUTT-OR3"),
    PG2("BANSHEE"),
    PG3("DOZER"),
    PG4("VIOLETTA"),
    PG5("SPROG");

    private String nome;
    //private IMMAGINE carta
    //private IMMAGINE sprite
    //private IMMAGINE tipoPlancia
    //private IMMAGINE pedina;

    Character(String nome){
        this.nome = nome;
    }
}