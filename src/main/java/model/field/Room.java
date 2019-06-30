package model.field;

import model.GameContext;
import model.Player;
import model.enums.Color;
import model.enums.TargetType;
import model.exception.InvalidMoveException;
import model.exception.NotExistingPositionException;
import model.exception.NotExistingRoomException;
import model.exception.NotExistingTargetException;
import model.moves.Target;
import model.room.Update;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//TODO finish javadoc
public class Room extends Target {
    private Color color;
    private List<Square> squares;

    public Room(Color color, List<Square> squares) {
        this.color = color;
        this.squares = squares;
    }

    public Room(TargetType targetType, TargetType targetState, Integer minDistance, Integer maxDistance){
        super(targetType, targetState, minDistance,maxDistance);
        this.color = Color.NONE;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void addDamages(Player playerDamaging, int damages, int groupId) throws NotExistingPositionException {
        for(Square s: GameContext.get().getGame(groupId).getBoard().getField().getSquares()){
            if(s.getColor().equalsTo(this.color)) s.addDamages(playerDamaging, damages, groupId);
        }
    }

    @Override
    public void setMine(int groupID) throws NotExistingPositionException {
        this.color = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition().getColor();
    }

    @Override
    public List<Target> findAllTargets(Target target, int groupID) {
        //TODO forse non serve da implementare
        return null;
    }

    @Override
    public String getName() {
        return color.getName();
    }

    @Override
    public void receiveUpdate(Update update) {
        for(Square s: this.squares){
            s.receiveUpdate(update);
        }
    }

    @Override
    public String getFieldsToFill() {
        if (this.color == null) return "choose the room ";
        else return null;
    }

    /**
     * @param player    the player attacking
     * @param groupID   the groupID
     * @return          true if the room is either the same room or
     *                  an adjacent room to player's position
     */
    public boolean canBeSeen(Player player, int groupID) throws NotExistingPositionException {
        if(player.getCurrentPosition().getColor().equals(this.getColor()))
            return true;
        else {
            List<Edge> edges = GameContext.get().getGame(groupID).getBoard().getField().getEdges();
            for (Edge e: edges) {
                if ((e.getSq1().equals(player.getCurrentPosition()) &&
                        !e.getSq2().getColor().equals(player.getCurrentPosition().getColor()) &&
                        this.getColor().equals(e.getSq2().getColor()))
                        || (e.getSq2().equals(player.getCurrentPosition()) &&
                        !e.getSq1().getColor().equals(player.getCurrentPosition().getColor()) &&
                        this.getColor().equals(e.getSq1().getColor()))) {
                    return true;
                }
            }
        } return false;
    }

    @Override
    public void setFieldsToFill(String input, int groupID) {
        if(color == null || color.equalsTo(Color.NONE)) setColor(Color.fromName(input));
    }

    @Override
    public Target fillFields(int groupID) {
        for(Room r: GameContext.get().getGame(groupID).getBoard().getField().getRooms()){
            if(this.color.equals(r.color)) return r;
        }
        return null;
    }

    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) {
        //TODO add marks to room
    }

    @Override
    public boolean isFilled() {
        if(this.color == null) return false;
        else
            return !this.color.equals(Color.NONE);
    }

    @Override
    public Square getCurrentPosition() {
        return null;
    }

    @Override
    public Target findRealTarget(String inputName, int groupID) throws NotExistingTargetException {
        for(Room r: GameContext.get().getGame(groupID).getBoard().getField().getRooms()){
            if(r.color.equalsTo(Color.fromName(inputName)))
                return r;
        }
        throw new NotExistingTargetException(this.getColor().toString());
    }

    @Override
    public boolean sameAsMe(int groupID) {
        return false;
    }
}
