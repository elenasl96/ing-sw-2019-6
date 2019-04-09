import exception.InvalidMoveException;
import model.Player;
import model.enums.Color;
import model.field.*;
import model.moves.Movement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveMovementTest {
    private Field field;
    @Before
    public void createField(){
        field = new Field();

        //Creating the field
        //Model:
        // b0 o b1 o B2
        // p         p
        // R3 o r4 o r5 p G6
        field.getSquares().add(new AmmoSquare(Color.BLUE));
        field.getSquares().add(new AmmoSquare(Color.BLUE));
        field.getSquares().add(new SpawnSquare(Color.BLUE));
        field.getSquares().add(new SpawnSquare(Color.RED));
        field.getSquares().add(new AmmoSquare(Color.RED));
        field.getSquares().add(new AmmoSquare(Color.RED));
        field.getSquares().add(new SpawnSquare(Color.YELLOW));
        field.getEdges().add(new Edge(field.getSquares().get(0), field.getSquares().get(1)));
        field.getEdges().add(new Edge(field.getSquares().get(1), field.getSquares().get(2)));
        field.getEdges().add(new Edge(field.getSquares().get(0), field.getSquares().get(3)));
        field.getEdges().add(new Edge(field.getSquares().get(2), field.getSquares().get(5)));
        field.getEdges().add(new Edge(field.getSquares().get(3), field.getSquares().get(4)));
        field.getEdges().add(new Edge(field.getSquares().get(4), field.getSquares().get(5)));
        field.getEdges().add(new Edge(field.getSquares().get(5), field.getSquares().get(6)));
    }

    @Test//(expected = InvalidMoveException.class)
    public void test1(){
        Player p = new Player(1, true);

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
