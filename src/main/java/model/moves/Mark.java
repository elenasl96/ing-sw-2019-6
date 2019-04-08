package model.moves;

import exception.FullMarksException;
import model.Player;

import java.util.Collections;

import static java.lang.Math.min;

public class Mark implements Move{
    private Player playerMarked;
    private int nMarks;

    public void execute(Player p) throws FullMarksException {
        int occurrences = Collections.frequency(playerMarked.getPlayerBoard().getMarks(), p);
        if(occurrences<3){
                playerMarked.getPlayerBoard().addMarks(p, min(3-occurrences, nMarks));
        } else{
            throw new FullMarksException();
        }
    }

    public Mark(Player playerMarked, int nMarks) {
        this.playerMarked = playerMarked;
        this.nMarks = nMarks;
    }

    public Player getPlayerMarked() {
        return playerMarked;
    }

    public void setPlayerMarked(Player playerMarked) {
        this.playerMarked = playerMarked;
    }

    public int getnMarks() {
        return nMarks;
    }

    public void setnMarks(int nMarks) {
        this.nMarks = nMarks;
    }
}
