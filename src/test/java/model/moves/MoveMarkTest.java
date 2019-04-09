package model.moves;

import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MoveMarkTest {

    @Test
    void MarkTest(){
        Player playerMarking = new Player(1, true);
        Player playerMarked = new Player(2, false);
        playerMarked.getPlayerBoard().addMarks(playerMarking, 3);

        assertEquals(1, playerMarked.getPlayerBoard().getMarks().get(0).getId());

    }
    @Test
    void MarkExceptionTest(){
        //TODO missing Exception Test
    }
}
