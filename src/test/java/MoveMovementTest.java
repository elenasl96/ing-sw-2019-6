import exception.InvalidMoveException;
import exception.NotExistingFieldException;
import model.Player;
import model.field.*;
import model.moves.Movement;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MoveMovementTest {
    private Field field;

    public void createField(){
        field = new Field();
        try {
            field.generateField(1);
        } catch (NotExistingFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        Player p = new Player(1, true);
        createField();
        Movement movement = new Movement(this.field);
        movement.setMaxSteps(1);

        p.setCurrentPosition(field.getSquares().get(1));
        movement.setDestination(field.getSquares().get(0));
        try{
            movement.execute(p);
            System.out.println(movement.getReachList());
            System.out.println(p.getCurrentPosition());
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(0), p.getCurrentPosition());

        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p);
            System.out.println(movement.getReachList());
            System.out.println(p.getCurrentPosition());
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(0), p.getCurrentPosition());

        movement.setMaxSteps(3);
        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(3), p.getCurrentPosition());
    }

}
