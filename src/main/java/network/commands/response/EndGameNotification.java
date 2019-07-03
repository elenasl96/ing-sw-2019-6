package network.commands.response;

import network.commands.Response;
import network.commands.ResponseHandler;

public class EndGameNotification implements Response {

    public EndGameNotification(){
        //nothing
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }
}
