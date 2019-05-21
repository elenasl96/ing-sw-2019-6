package network.socket.commands.response;

import model.Player;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;

public class MoveUpdateResponse implements Response {
    private Player player;
    private int phaseId;
    private int phaseNotDone;

    public MoveUpdateResponse(Player player){
        this.player = player;
        this.phaseId = player.getPhase().getId();
        if(this.player.isPhaseNotDone()){
            this.phaseNotDone = 1;
        } else {
            this.phaseNotDone = 0;
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public int getPhaseNotDone(){
        return this.phaseNotDone;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }


}
