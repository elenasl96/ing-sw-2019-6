package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

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
