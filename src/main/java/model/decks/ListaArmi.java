package model.decks;

public enum ListaArmi {
    DISTRUTTORE("Distruttore", 1),
    FALCE_PROTONICA( "Falce protonica", 2),
    MITRAGLIATRICE("Mitragliatrice", 3),
    RAGGIO_TRAENTE("Raggio traente", 4),
    TORPEDINE("Torpedine", 5),
    FUCILE_AL_PLASMA("Fucile al plasma", 6),
    FUCILE_DI_PRECISIONE("Fucile di precisione", 7),
    CANNONE_VORTEX("Cannone vortex", 8),
    VULCANIZZATORE("Vulcanizzazione", 9),
    RAZZO_TERMICO("Razzo termico", 10),
    RAGGIO_SOLARE("Raggio solare", 11),
    LANCIAFIAMME("Lanciafiamme", 12),
    LANCIAGRANATE("Lanciagranate", 13),
    LANCIARAZZI("Lanciarazzi", 14),
    ZX2("Zx2", 15),
    FUCILE_A_POMPA("Fucile a pompa", 16),
    FUCILE_LASER("Fucile laser", 17),
    CYBERGUANTO("Cyberguanto", 18),
    ONDA_DURTO("Onda d'urto", 19),
    SPADA_FOTONICA("Spada fotonica", 20),
    MARTELLO_IONICO("Martello ionico", 21);

    private String nome;
    private int numero;

    ListaArmi(String nome, int numero){
        this.nome = nome;
        this.numero = numero;
    }
}
