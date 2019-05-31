package model.moves;

import exception.FullMarksException;
import model.Player;
import model.enums.Character;
import model.room.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MoveMarkEffectTest {

    @Test
    void MarkTest() throws FullMarksException {
        int groupId = 0;
        Player playerMarking = new Player(new User("goofy"));
        Player playerMarked = new Player(new User("donald"));
        MarkEffect m = new MarkEffect(Stream.of(playerMarked), 2, false);
        /*add 3 marks of playerMarking to playerMarked */
        m.execute(playerMarking, groupId);
        assertEquals(2, Collections.frequency(playerMarked.getPlayerBoards(groupId).get(0).getMarks(), playerMarking));

        /*add other 3 marks of playermarking to playermarked */
        m.execute(playerMarking, groupId);
        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoards(groupId).get(0).getMarks(), playerMarking));

        /*add other 3 marks --> this move will throw fullmarkexception*/
        try{
            m.execute(playerMarking, groupId);
        } catch (FullMarksException e){
            System.out.println(e.getMessage());
        }

        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoards(groupId).get(0).getMarks(), playerMarking));

    }
    @Test
    void MarkExceptionTest(){
        //TODO missing Exception Test
    }
}
