package model.moves;

import model.GameContext;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import network.commands.Response;

//TODO javadoc
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

    @Override
    public Response execute(Player p, int groupID) {
        this.setMaxSteps(3);
        if(GameContext.get().getGame(groupID).isFinalFrenzy()
                && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
            this.setMaxSteps(4);
        }
        this.movement.execute(p, groupID);
        return null;
    }

}
