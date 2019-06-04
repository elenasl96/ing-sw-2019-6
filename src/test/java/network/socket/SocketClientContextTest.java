package network.socket;

import network.ClientContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocketClientContextTest {

    @BeforeEach
    void beforeReset(){
        ClientContext.get().reset();
    }

    @Test
    void CreatePlayerTest(){
        ClientContext.get().createPlayer();
        assertNotNull(ClientContext.get().getCurrentPlayer());
    }

    @Test
    void resetTest(){
        assertNull(ClientContext.get().getCurrentUser());
        assertNull(ClientContext.get().getCurrentPlayer());
        assertNull(ClientContext.get().getCurrentGroup());
        assertNull(ClientContext.get().getCurrentSituation());
    }

    @AfterEach
    void afterReset(){
        ClientContext.get().reset();
    }
}
