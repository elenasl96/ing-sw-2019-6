package network.commands.response;

import model.Player;
import network.commands.Response;
import network.commands.ResponseHandler;

/**
 * Response sent for EVERY update sent to the ClientController
 */
public class MoveUpdateResponse implements Response {
    private String phaseId;
    private String username;
    private int phaseNotDone;

    public MoveUpdateResponse(Player player){
        this.username = player.getName();
        this.phaseId = Integer.toString(player.getPhase().getId());
        if(player.isPhaseNotDone()){
            this.phaseNotDone = 1;
        } else {
            this.phaseNotDone = 0;
        }
    }

    public String getUsername(){
        return this.username;
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
