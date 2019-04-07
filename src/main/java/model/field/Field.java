package model.field;

import java.util.ArrayList;

public class Field {
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Square> squares = new ArrayList<>();

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }
}
