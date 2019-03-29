package model.field;

import model.enums.Color;
import java.util.List;

public class Room {
    private Color color;
    private List<Square> squares;

    public Room(Color color, List<Square> squares) {
        this.color = color;
        this.squares = squares;
    }

    public Color getColor() {
        return color;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }
}
