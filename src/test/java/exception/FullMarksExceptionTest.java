package exception;

import model.Player;
import model.PlayerBoard;
import model.PlayerBoardTest;
import model.room.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FullMarksExceptionTest {

    @Test
    void test(){
        FullMarksException fme = new FullMarksException();
        PlayerBoard pb = new PlayerBoard();
        assertThrows(fme.getClass(),() -> pb.addMarks(new Player(new User("pippo")),4));
        assertThrows(fme.getClass(),() -> pb.addMarks(new Player(new User("pippo")),1));
    }
}
