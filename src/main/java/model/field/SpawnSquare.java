package model.field;

import model.enums.Color;

import java.util.List;

public class SpawnSquare extends Square{


    public SpawnSquare(Color color, List<Square> adjacents) {
        super(color);
    }
}
