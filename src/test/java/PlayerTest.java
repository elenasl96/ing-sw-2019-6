import model.Player;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private Player john;

    @Before
    public void createPlayer(){
        int coord[]={1,1};
        int powerup[]={1,1,1};

        john = new Player("john", null, 3, null, null,5, null, null, null,
                null,50,"spezzeremo le reni al negus",1,2,true,false,null);
    }
    @Test
    public void nameTest() {
        createPlayer();
        int coord[]={1,1};
        int powerup[]={1,1,1};
        assertEquals("john", john.getName());
        assertEquals(3,john.getId());
        assertEquals(null,john.getCurrentPosition());
        assertEquals(5,john.getPhase());

        assertEquals(null, john.getShootable());
    }
}
