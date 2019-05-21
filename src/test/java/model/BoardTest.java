package model;

import exception.NotExistingFieldException;
import model.field.Field;
import model.room.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @Test
    void Constructor(){
        Board board = new Board(1);
        Field field = new Field(1, board);
        try{field.generateField(1, board);}
        catch (NotExistingFieldException nef){
            System.out.println(nef.getMessage());
        }
        board.setField(field);
    }

    @Test
    void killShotTrackTest(){
        Player p1 = new Player(new User("pippo"));
        Board b1 = new Board(2);
        b1.addKillshot(p1);
        assertEquals(p1, b1.getKillshotTrack().get(0));
    }

    @Test
    void ExceptionTest(){
        assertThrows(NotExistingFieldException.class, () -> new Board(4));
    }
}
