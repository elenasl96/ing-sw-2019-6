package network.socket.commands.response;

import model.Player;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class MoveUpdateResponse implements Response {
    private Update update;

    public MoveUpdateResponse(Update update){
        this.update = update;
    }

    public Player getUpdatedPlayer(){
        return update.getPlayer();
    }
    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString(){
        return update.getPlayer().getName()+" moved to square "+update.getPlayer().getCurrentPosition().toString();
    }
}
