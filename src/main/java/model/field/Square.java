package model.field;

import model.Board;
import model.GameContext;
import model.Player;
import model.PlayerBoard;
import model.decks.Grabbable;
import model.enums.Color;
import model.moves.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Square extends Target implements Serializable {
    private Color color;
    private Coordinate coord;

    public Square(){
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

    public abstract Grabbable getGrabbable();

    public void setGrabbable(Board board){ }

    public Coordinate getCoord() {
        return coord;
    }

    public List<PlayerBoard> getPlayerBoard(int groupId){
        ArrayList<PlayerBoard> boards = new ArrayList<>();
        for(Player p : GameContext.get().getGame(groupId).getPlayers().stream()
                .filter(p -> p.getCurrentPosition().coord.equals(this.coord))
                .collect(Collectors.toList())){
            boards.add(p.getPlayerBoard(groupId).get(0));
        }
        return boards;
    }

    @Override
    public String toString() {
        return this.coord.toString();
    }
}
