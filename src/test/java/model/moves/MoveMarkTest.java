package model.moves;

import exception.FullMarksException;
import model.Player;
import model.enums.Character;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MoveMarkTest {

    @Test
    void MarkTest() throws FullMarksException {
        Player playerMarking = new Player(1, true, "pippo", Character.PG3);
        Player playerMarked = new Player(2, false, "paperino", Character.PG1);
        Mark m = new Mark(playerMarked, 2);
        /*add 3 marks of playermarking to playermarked */
        m.execute(playerMarking);
        assertEquals(2, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

        /*add other 3 marks of playermarking to playermarked */
        m.execute((playerMarking));
        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

        /*add other 3 marks --> this move will throw fullmarkexception*/
        try{
            m.execute((playerMarking));
        } catch (FullMarksException e){
            System.out.println(e.getMessage());
        }

        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

    }
    @Test
    void MarkExceptionTest(){
        //TODO missing Exception Test
    }
}
