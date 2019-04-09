package model.field;

import model.decks.Grabbable;
import model.enums.Color;

import java.util.List;

public class SpawnSquare extends Square{

    public SpawnSquare(Color color) {
        super(color);
    }

    @Override
    public Grabbable getGrabbable() {
        return null;
    }
}
