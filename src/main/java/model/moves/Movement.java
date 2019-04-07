package model.moves;

import exception.InvalidMoveException;
import model.Player;
import model.field.Field;
import model.field.Square;

import java.util.ArrayList;
import java.util.List;

public class Movement implements Move{
    private Square destination;
    private static Field field;
    private ArrayList<Square> reachList = new ArrayList<>();
    private int steps;

    public void execute(Player p) throws InvalidMoveException {
        createReachList(p, steps);
        if(reachList.contains(this.destination)){
        }else throw new InvalidMoveException();
    }

    public void createReachList(Player p, int maxSteps) {
        if (maxSteps != 0) {
            for (int i = 0; i < maxSteps; i++) {
                field.getEdges().forEach(edge -> {
                    if (edge.getSq1().equals(p.getCurrentPosition())) {
                        this.reachList.add(edge.getSq2());
                        createReachList(p, maxSteps-1);
                    } else if (edge.getSq2().equals(p.getCurrentPosition())) {
                        this.reachList.add(edge.getSq1());
                        createReachList(p, maxSteps-1);
                    }

                });
            }
        }

    }

    public Movement(Square destination){
        this.destination = destination;
    }
}
