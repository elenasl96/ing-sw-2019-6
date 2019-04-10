package model;

import model.enums.Color;
import model.field.Coordinate;
import model.field.SpawnSquare;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    public void startSquareTest(){
        Board board = new Board();
        SpawnSquare spawnSquare = new SpawnSquare(Color.YELLOW, new Coordinate('B',1));
        System.out.println(board.getWeaponsLeft().getWeapons());
        //spawnSquare.setGrabbable(board);
    }
}
