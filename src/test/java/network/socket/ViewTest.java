package network.socket;

import model.room.Group;
import model.room.Message;
import model.room.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ViewTest {

    @Mock
    ClientController mockController;

    @Test
    void OnTriggerTest(){
        ViewClient view = new ViewClient(mockController);
        view.onJoin(new User("1"));
        view.onMessage(new Message(new Group(5,8),new User("1"),"hi"));
        view.onLeave(new User("1"));
        view.onStart();
    }

    @Test
    void WaitingTest(){
        ViewClient view = new ViewClient(mockController);
        view.setWait(false);
        view.waitingPhase();
    }
}