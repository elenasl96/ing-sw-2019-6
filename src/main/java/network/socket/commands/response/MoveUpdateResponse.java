package network.socket.commands.response;

import model.Player;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class MoveUpdateResponse implements Response {
    private Player player;

    private boolean yourPlayer;

    public MoveUpdateResponse(Player player){
        this.player = player;
        this.yourPlayer = false;
    }

    public Player getPlayer(){
        return this.player;
    }
    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    public boolean isYourPlayer() {
        return this.yourPlayer;
    }

    public void setYourPlayer(boolean yourPlayer){
        this.yourPlayer = yourPlayer;
    }
}
