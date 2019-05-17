package network.socket;

import model.moves.Move;
import model.room.Command;
import model.room.Message;
import network.socket.commands.request.MoveRequest;
import network.socket.commands.request.SpawnRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandsTest {

    @Mock
    private Message message;
    @Mock
    private Command command;
    @Mock
    private Move move;

    @Test
    void SpawnRequestTest(){
        SpawnRequest spawnRequest = new SpawnRequest(null);
        assertNull(spawnRequest.getSpawn());
        spawnRequest = new SpawnRequest(0);
        assertEquals(0, spawnRequest.getSpawn());
    }

    @Test
    void MoveRequestTest(){
        MoveRequest moveRequest = new MoveRequest();
        moveRequest.addMove(move);
        Move result = moveRequest.getMove();
    }
}
