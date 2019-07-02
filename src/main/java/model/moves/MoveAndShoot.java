package model.moves;

import model.GameContext;
import model.Player;
import model.enums.Phase;
import model.exception.*;
import model.field.Coordinate;
import model.room.Update;
import network.commands.Response;
import network.commands.response.AskInput;

import static model.enums.Phase.WAIT;
import static model.field.Coordinate.fillCoordinate;

/**
 * Second move of the game, made by a movement of one step and a grab
 */
public class MoveAndShoot implements Move {
    private Coordinate coordinate;
    private int weapon;
    private Movement movement;
    private Shoot shoot;

    public MoveAndShoot(Coordinate coordinate, int weapon){
        this.coordinate = coordinate;
        this.movement = new Movement(coordinate);
        this.shoot = new Shoot();
    }

    /**
     * Adds a movement and a shoot to the player's current moves
     * @param p     the player
     * @param groupID   the group ID
     * @return null
     */
    @Override
    public Response execute(Player p, int groupID) throws InvalidMoveException {
        if (p.getCurrentMoves().isEmpty() && coordinate == null){
            movement.setMaxSteps(setMaxSteps(p, groupID));
            if(movement.getMaxSteps() > 0)
                return new AskInput("coordinate");
            else
                p.getCurrentMoves().add(shoot);

        }

        if (p.getCurrentMoves().isEmpty() && coordinate != null){
            if(movement.getMaxSteps() > 0){
                p.getCurrentMoves().add(movement);
                movement.setCoordinate(coordinate);
            }
            p.getCurrentMoves().add(shoot);
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

    private int setMaxSteps(Player player, int groupID) {
        int maxSteps;
        if (GameContext.get().getGame(groupID).isFinalFrenzy()) {
            if (GameContext.get().getGame(groupID).getCurrentPlayer().isFirstPlayer())
                maxSteps = 2;
            else
                maxSteps = 1;
        }else{
            if (player.getAdrenalineLevel() == 2)
                maxSteps = 1;
            else
                maxSteps = 0;
        }
        return maxSteps;
    }
}
