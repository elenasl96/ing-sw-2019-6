package model.moves;

import exception.InvalidMoveException;
import model.GameContext;
import model.Player;
import model.field.Coordinate;
import model.field.Field;
import network.socket.commands.Response;

public class Run implements Move {
    private Movement movement;

    public Run(Coordinate destination){
        this.movement = new Movement(destination);
    }

    public void setMaxSteps(int maxSteps){
        movement.setMaxSteps(maxSteps);
    }

    public void setField(Field field){
        this.movement.setField(field);
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
