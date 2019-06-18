package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

//TODO javadoc
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

    public SpawnRequest(Integer spawn, boolean isFirstTime){
        this.firstTime = isFirstTime;
        this.spawn = spawn;
    }

    public boolean isFirstTime(){
        return this.firstTime;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
