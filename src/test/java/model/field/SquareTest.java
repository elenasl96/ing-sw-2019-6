package model.field;

import model.Board;
import model.enums.Color;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    public void startSquareTest(){
        Board board = new Board();
        SpawnSquare spawnSquare = new SpawnSquare(Color.YELLOW, new Coordinate('B',1));
        //spawnSquare.setGrabbable(board);

        AmmoSquare ammoSquare = new AmmoSquare(Color.YELLOW, new Coordinate('C',1));
        ammoSquare.setGrabbable(board);
    }
}
