package network.commands.response;

import model.Player;
import network.commands.Response;
import network.commands.ResponseHandler;

//TODO javadoc
public class MoveUpdateResponse implements Response {
    private Player player;
    private String phaseId;
    private int phaseNotDone;

    public MoveUpdateResponse(Player player){
        this.player = player;
        this.phaseId = Integer.toString(player.getPhase().getId());
        if(this.player.isPhaseNotDone()){
            this.phaseNotDone = 1;
        } else {
            this.phaseNotDone = 0;
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public String getPhaseId() {
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
