package model.field;

import model.GameContext;
import model.Player;
import model.enums.Color;
import model.enums.TargetType;
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
        this.color = null;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void addDamages(Player playerDamaging, int damages, int groupId) {
        for(Square s: GameContext.get().getGame(groupId).getBoard().getField().getSquares()){
            if(s.getColor().equals(this.color)) s.addDamages(playerDamaging, damages, groupId);
        }
    }

    @Override
    public void setMine(int groupID) {
        this.color = GameContext.get().getGame(groupID).getCurrentPlayer().getCurrentPosition().getColor();
    }

    @Override
    public List<Target> findAllTargets(Target target, int groupID) {
        //TODO forse non serve da implementare
        return null;
    }

    @Override
    public void receiveUpdate(Update update) {
        //TODO implement send update to all room
    }

    @Override
    public String getFieldsToFill() {
        if (this.color == null) return "choose the room ";
        else return null;
    }

    public boolean canBeSeen(Player player, int groupID) {
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
        if(color == null) setColor(Color.fromName(input));
    }

    @Override
    public Target fillFields(int groupID) {
        //TODO fill real room with what?
        return null;
    }

    @Override
    public void addMarks(Player playerMarking, int groupID, int nMarks) {
        //TODO add marks to room
    }

    @Override
    public boolean isFilled() {
        return !this.color.equals(Color.NONE);
    }

    @Override
    public Square getCurrentPosition() {
        //nothing
        return null;
    }

    @Override
    public Target findRealTarget(String inputName, int groupID) {
        //TODO find room
        return null;
    }

    @Override
    public boolean sameAsMe(int groupID) {
        return false;
    }
}
