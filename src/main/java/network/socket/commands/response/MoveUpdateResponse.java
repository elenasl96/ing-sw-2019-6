package network.socket.commands.response;

import model.Player;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class MoveUpdateResponse implements Response {
    private Player player;
    private int phaseId;

    public MoveUpdateResponse(Player player){
        this.player = player;
        this.phaseId = player.getPhase().getId();
    }

    public Player getPlayer(){
        return this.player;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }


}
