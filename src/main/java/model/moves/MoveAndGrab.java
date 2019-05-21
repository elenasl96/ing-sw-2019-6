package model.moves;

import exception.InvalidMoveException;
import model.GameContext;
import model.Player;
import model.field.Coordinate;
import network.socket.commands.Response;

public class MoveAndGrab implements Move {
    private Movement movement;
    private Grab grab;

    public MoveAndGrab(Coordinate coordinate){
        this.movement = new Movement(coordinate);
        this.grab = new Grab();
    }

    @Override
    public Response execute(Player p, int groupID) throws InvalidMoveException {
        int maxSteps;
        if(p.getCurrentMoves().isEmpty()){
            maxSteps = 1;
            if(GameContext.get().getGame(groupID).isFinalFrenzy() && !GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer()){
                maxSteps = 4;
            }
            movement.setMaxSteps(maxSteps);
            p.getCurrentMoves().add(movement);
            p.getCurrentMoves().add(grab);
        }
        while(!p.getCurrentMoves().isEmpty()){
            Response response = p.getCurrentMoves().get(0).execute(p, groupID);
            p.getCurrentMoves().remove(0);
            if(response != null) return response;
        }
        return null;
    }
}
