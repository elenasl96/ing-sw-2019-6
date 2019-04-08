import model.Player;
import model.moves.Mark;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoveMarkTest {
    private Mark marking;
    private Player playerMarked;
    private Player playerMarking;

    @Test
    public void MarkTest(){
        playerMarking = new Player(1, true);
        playerMarked = new Player(2, false);
        playerMarked.getPlayerBoard().addMarks(playerMarking, 3);

        assertEquals(1, playerMarked.getPlayerBoard().getMarks().get(0).getId());

    }
}
