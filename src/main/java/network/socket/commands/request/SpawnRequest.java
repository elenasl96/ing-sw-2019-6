package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class SpawnRequest implements Request {
    private Integer spawn;

    public SpawnRequest(Integer spawn) {
        this.spawn = spawn;
    }

    public Integer getSpawn() {
        return spawn;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
