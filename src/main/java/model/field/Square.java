package model.field;

import model.Board;
import model.decks.Grabbable;
import model.enums.Color;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.List;

public abstract class Square implements Serializable {
    private Color color;
    private final Coordinate coord;

    public Square(Color color, Coordinate coord) {
        this.color = color;
        this.coord = coord;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract Grabbable getGrabbable();

    public void setGrabbable(Board board){ }

    public Coordinate getCoord() {
        return coord;
    }
}
