package model.field;

import model.GameContext;
import model.Player;
import model.PlayerBoard;
import model.enums.Color;
import model.enums.TargetType;
import model.moves.Target;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//TODO finish javadoc
public class Room extends Target {
    private Color color;
    private List<Square> squares;

    public Room(Color color, List<Square> squares) {
        this.color = color;
        this.squares = squares;
    }

    public Room(TargetType targetType, Integer minDistance, Integer maxDistance){
        super(targetType, minDistance,maxDistance);
        this.color = null;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns all the playerBoards of every player in every square of the room.
     * with a for loop over the squares in this room, it looks for the players who are in
     * any of the squares of the room and then gets their player boards.
     * @param groupId the current group ID, the key to get to the Game via the GameContext
     * @return a list of player boards
     */
    @Override
    public List<PlayerBoard> getPlayerBoards(int groupId) {
        List<PlayerBoard> list = new ArrayList<>();
        for (Square square : this.squares) {
            for(int i = 0; i<GameContext.get().getGame(groupId).getPlayers().size(); i++){
                Player player = GameContext.get().getGame(groupId).getPlayers().get(i);
                if (player.getCurrentPosition().equals(square)){
                    list.add(player.getPlayerBoards(groupId).get(0));
                }
            }
        }
        return list;
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
    public Room setFieldsToFill(String input, int groupID) {
        if(color == null) setColor(Color.fromName(input));
        //TODO return square or color?
        return null;
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
