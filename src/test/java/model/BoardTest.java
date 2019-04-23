package model;

import exception.NotExistingFieldException;
import model.field.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @Test
    void Constructor(){
        Board board = new Board(1);
        Field field = new Field(1);
        try{field.generateField(1);}
        catch (NotExistingFieldException nef){
            System.out.println(nef.getMessage());
        }
        board.setField(field);
    }

    @Test
    void ExceptionTest(){
        assertThrows(NotExistingFieldException.class, () -> new Board(4));
    }
}
