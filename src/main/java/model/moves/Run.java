package model.moves;

import model.GameContext;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import network.commands.Response;

/**
 * The RUN move, composed by max 3 or 4 steps
 */
public class Run implements Move {
    private Movement movement;

    public Run(Coordinate destination){
        this.movement = new Movement(destination);
    }

    private void setMaxSteps(int maxSteps){
        movement.setMaxSteps(maxSteps);
    }

    public Movement getMovement() {
        return movement;
    }

    /**
     * @see Movement#execute(Player, int)
     */
    @Override
    public Response execute(Player p, int groupID) {
        if(GameContext.get().getGame(groupID).isFinalFrenzy()
                && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            this.setMaxSteps(4);
        } else {
            this.setMaxSteps(3);
        }
        this.movement.execute(p, groupID);
        return null;
    }

}
