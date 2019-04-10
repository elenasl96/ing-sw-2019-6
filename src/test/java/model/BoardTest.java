package model;

import exception.NotExistingFieldException;
import model.field.Field;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void Constructor(){
        Board board = new Board();
        Field field = new Field();
        try{field.generateField(1);}
        catch (NotExistingFieldException nef){
            System.out.println(nef.getMessage());
        }
        board.setField(field);

    }
}
