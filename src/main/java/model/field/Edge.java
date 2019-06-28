package model.field;

import java.io.Serializable;

/**
 * The esge of the field seen as a graph, between two squares
 */
public class Edge implements Serializable {
    private Square sq1;
    private Square sq2;

    public Edge(Square sq1, Square sq2){
        this.sq1 = sq1;
        this.sq2 = sq2;
    }

    public Square getSq1() {
        return sq1;
    }

    public Square getSq2() {
        return sq2;
    }
}
