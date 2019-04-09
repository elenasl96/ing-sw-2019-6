package model.field;

import model.Board;
import model.decks.Grabbable;
import model.enums.Color;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.List;

public abstract class Square implements Serializable {
    private Color color;
    private List<Square> adjacents;

    public Square(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Square> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(List<Square> adjacents) {
        this.adjacents = adjacents;
    }

    public abstract Grabbable getGrabbable();

    public void setGrabbable(Board board){

    }
}
