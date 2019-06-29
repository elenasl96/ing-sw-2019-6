package model.moves;

import model.Player;
import model.exception.NotExistingPositionException;
import model.room.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MoveMarkEffectTest {

    @Test
    void MarkTest() {
        int groupId = 0;
        Player playerMarking = new Player(new User("goofy"));
        Player playerMarked = new Player(new User("donald"));
        MarkEffect m = new MarkEffect(Stream.of(playerMarked), 2, false);
        /*add 3 marks of playerMarking to playerMarked */
        try {
            m.execute(playerMarking, groupId);
        } catch (NotExistingPositionException e) {
            e.printStackTrace();
        }
        assertEquals(2, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

        /*add other 3 marks of playermarking to playermarked */
        try {
            m.execute(playerMarking, groupId);
        } catch (NotExistingPositionException e) {
            e.printStackTrace();
        }
        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

        assertEquals(3, Collections.frequency(playerMarked.getPlayerBoard().getMarks(), playerMarking));

    }
    @Test
    void MarkExceptionTest(){
        //TODO missing Exception Test
    }
}
