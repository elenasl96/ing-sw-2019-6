package model.field;

import model.Board;
import model.GameContext;
import model.Player;
import model.PlayerBoard;
import model.decks.Grabbable;
import model.decks.Weapon;
import model.enums.Color;
import model.enums.TargetType;
import model.moves.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Square extends Target implements Serializable {
    private Color color;
    private Coordinate coord;

    public Square(){
        this.color = null;
        this.coord = null;
    }

    public Square(TargetType targetType, Integer minDistance, Integer maxDistance ){
        super(targetType, minDistance,maxDistance);
        this.color = null;
        this.coord = null;
    }

    public Square(Color color, Coordinate coord) {
        this.color = color;
        this.coord = coord;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Grabbable getGrabbable(){
        return null;
    }

    public void setGrabbable(Board board){
        //DO nothing
    }

    public Coordinate getCoord() {
        return coord;
    }

    public List<PlayerBoard> getPlayerBoards(int groupId){
        ArrayList<PlayerBoard> boards = new ArrayList<>();
        for(Player p : GameContext.get().getGame(groupId).getPlayers().stream()
                .filter(p -> p.getCurrentPosition().coord.equals(this.coord))
                .collect(Collectors.toList())){
            boards.add(p.getPlayerBoards(groupId).get(0));
        }
        return boards;
    }

    @Override
    public String toString() {
        return this.coord.toString();
    }

    public boolean isEmpty() {
        return this.getGrabbable()==null;
    }

    public void addGrabbable(Weapon grabbable, int groupID) {
        //DO nothing
    }
}
