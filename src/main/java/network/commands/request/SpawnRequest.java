package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent when a player wants to spawn
 * If spawn attribute is set to null, it also asks for the possible spawn points
 */
public class SpawnRequest implements Request {
    private boolean firstTime;
    private Integer spawn;

    public SpawnRequest(Integer spawn) {
        this.spawn = spawn;
        this.firstTime = false;
    }

    public Integer getSpawn() {
        return spawn;
    }

    public boolean isFirstTime(){
        return this.firstTime;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
