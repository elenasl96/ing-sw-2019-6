package network.socket;

import model.Player;
import model.enums.Phase;
import network.socket.commands.response.MoveUpdateResponse;
import network.socket.launch.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientControllerTest {

    private ClientController clientController;

    @Mock
    private Client client;

    @BeforeEach
    void start(){
        ClientContext.get().reset();
        clientController = new ClientController(client);
    }

    @Test
    void handleMoveUpdateTest(){
        ClientContext.get().createPlayer();
        Player player = new Player();
        MoveUpdateResponse moveUpdateResponse = new MoveUpdateResponse(player, 1);

        clientController.handle(moveUpdateResponse);
        assertEquals(Phase.FIRST, ClientContext.get().getCurrentPlayer().getPhase());

        moveUpdateResponse = new MoveUpdateResponse(player, 0);
        clientController.handle(moveUpdateResponse);
        assertEquals(Phase.WAIT, ClientContext.get().getCurrentPlayer().getPhase());
    }
}
