package model.moves;

import model.GameContext;
import model.Player;
import model.enums.Phase;
import model.field.Coordinate;
import model.room.Update;
import network.commands.Response;

import static model.enums.Phase.WAIT;

/**
 * Second move of the game, made by a movement of one step and a grab
 */
public class MoveAndGrab implements Move {
    private Movement movement;
    private Grab grab;

    public MoveAndGrab(Coordinate coordinate){
        this.movement = new Movement(coordinate);
        this.grab = new Grab();
    }

    /**
     * Adds a movement and a grab to the player's current moves
     * @param p     the player
     * @param groupID   the group ID
     * @return null
     */
    @Override
    public Response execute(Player p, int groupID) {
        int maxSteps;
        if(p.getCurrentMoves().isEmpty()){
            if(p.getAdrenalineLevel() == 0)
                maxSteps = 1;
            else
                maxSteps = 2;

            if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
                maxSteps = 4;
            }
            movement.setMaxSteps(maxSteps);
            p.getCurrentMoves().add(movement);
            p.getCurrentMoves().add(grab);
        }

        while(!p.getCurrentMoves().isEmpty()){
                Response response = p.getCurrentMoves().get(0).execute(p, groupID);
                if(response != null) {
                    Phase phase = p.getPhase();
                    p.setPhase(WAIT);
                    p.getUser().receiveUpdate(new Update(p,true));
                    p.setPhase(phase);
                    return response;
                }
                p.getCurrentMoves().remove(0);
        }
        return null;
    }
}
