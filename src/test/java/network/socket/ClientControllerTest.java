package network.socket;

import model.Player;
import model.enums.Character;
import model.enums.Phase;
import model.room.Group;
import model.room.User;
import network.socket.commands.request.SituationViewerRequest;
import network.socket.commands.response.*;
import network.socket.launch.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientControllerTest {

    private ClientController clientController;
    private MoveUpdateResponse moveUpdateResponse1;

    @Mock
    Client client;

    @BeforeEach
    void start(){
        ClientContext.get().reset();
        clientController = new ClientController(client);
        ClientContext.get().createPlayer();

        Player player = new Player();
        player.setPhase(Phase.WAIT);

        clientController.handle(new UserCreatedResponse(new User("1")));
        moveUpdateResponse1 = new MoveUpdateResponse(player);
        clientController.handle(moveUpdateResponse1);
        clientController.handle(new SetCharacterResponse(Character.PG2));
        clientController.handle(new JoinGroupResponse(new Group(5,1)));

    }

    @Test
    void handleMoveUpdateTest(){
        assertEquals(Phase.WAIT , Phase.fromInteger(moveUpdateResponse1.getPhaseId()));
    }

    @Test
    void standardHandling(){
        clientController.startReceiverThread();
        clientController.handle(new TextResponse("error"));
        clientController.handle(new SituationViewerResponse("all fine"));
    }
}
