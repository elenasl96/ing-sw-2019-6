package model.field;

import model.Board;
import model.PlayerBoard;
import model.decks.Grabbable;
import model.enums.Color;
import model.moves.Target;

import java.io.Serializable;

public abstract class Square extends Target implements Serializable {
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

    @Override
    public String toString() {
        return this.coord.toString();
    }

    public PlayerBoard getPlayerBoard(){

        return null;
    }
}
