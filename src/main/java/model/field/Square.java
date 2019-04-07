package model.field;

import model.enums.Color;

import java.util.List;

public abstract class Square {
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
}
