package network.socket;

import model.Player;
import model.enums.Phase;
import network.socket.commands.request.SituationViewerRequest;
import network.socket.commands.response.MoveUpdateResponse;
import network.socket.launch.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        player.setPhase(Phase.WAIT);
        MoveUpdateResponse moveUpdateResponse1 = new MoveUpdateResponse(player);

        assertEquals(Phase.WAIT , Phase.fromInteger(moveUpdateResponse1.getPhaseId()));
    }
}
