package network.socket;

import model.Player;
import model.room.Group;
import model.room.Message;
import model.room.Update;
import model.room.User;
import network.socket.commands.request.SpawnRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandsTest {

    @Test
    void SpawnRequest(){
        SpawnRequest spawnRequest = new SpawnRequest(null);
        assertNull(spawnRequest.getSpawn());
        spawnRequest = new SpawnRequest(0);
        assertEquals(0, spawnRequest.getSpawn());
    }
}
