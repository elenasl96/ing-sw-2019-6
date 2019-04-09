package model;

import model.enums.Color;
import model.field.SpawnSquare;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    public void startSquareTest(){
        Board board = new Board();
        SpawnSquare spawnSquare = new SpawnSquare(Color.YELLOW);
        spawnSquare.setGrabbable(board);
    }
}
