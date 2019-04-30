package model.moves;

import exception.InvalidMoveException;
import exception.NotExistingFieldException;
import model.Board;
import model.Player;
import model.enums.Character;
import model.enums.Color;
import model.field.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class MoveMovementTest {
    private Field field;
    private Board board = new Board(2);

    void createField(){

        field = new Field(1, board);
        try {
            field.generateField(1, board);
        } catch (NotExistingFieldException e) {
            e.printStackTrace();
        }
    }
    @Test
    void gettersAndSetters(){
        createField();
        Movement movement = new Movement(field);
        assertEquals(field, movement.getField());

        movement.setMaxSteps(3);
        assertEquals(3, movement.getMaxSteps());

        Square destination = new SpawnSquare(Color.BLUE, new Coordinate('B',2), board);
        movement.setDestination(destination);
        assertEquals(destination, movement.getDestination());

        Field field2 = new Field(1, board);
        field.getSquares().add(new AmmoSquare(Color.BLUE, new Coordinate('B',2)));
        field.getSquares().add(new AmmoSquare(Color.BLUE, new Coordinate('B',2)));
        field.getSquares().add(new SpawnSquare(Color.BLUE, new Coordinate('B',2), board));
        field.getEdges().add(new Edge(field.getSquares().get(0), field.getSquares().get(1)));
        field.getEdges().add(new Edge(field.getSquares().get(1), field.getSquares().get(2)));
        movement.setField(field2);
        assertEquals(field2, movement.getField());

        movement.setReachList(field.getSquares());
        assertEquals(field.getSquares(), movement.getReachList());

    }
    @Test//(expected = InvalidMoveException.class)
    void test1(){
        Player p = new Player(1, true, "pippo", Character.PG2);

        createField();

        Movement movement = new Movement(this.field);
        movement.setMaxSteps(1);
        
        assertEquals(movement.getField(),field);


        p.setCurrentPosition(field.getSquares().get(1));
        movement.setDestination(field.getSquares().get(0));
        try{
            movement.execute(p);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(0), p.getCurrentPosition());

        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(3), p.getCurrentPosition());

        movement.setMaxSteps(3);
        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(3), p.getCurrentPosition());

        movement.setMaxSteps(1);
        movement.setDestination(field.getSquares().get(6));

        try{
            movement.execute(p);
            fail();
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

    }

}
