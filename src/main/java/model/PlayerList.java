package model;

import model.enums.Phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An extension of ArrayList implementing the next method in a cyclical way
 */
public class PlayerList extends ArrayList<Player> {
    private int counter = 0;

    /**
     * @return  the next player as in a linked list implementation,
     *          going back to the first if the previous player was the last one
     */
    public Player next(){
        counter++;
        if(counter>=super.size()) counter = 0;
        return super.get(counter);
    }

    /**
     * @param groupID   the groupID of the game
     * @return          the winners of the game, with the highest points and the most killed players
     */
    public List<Player> findHighest(int groupID){
        List<Player> players = new ArrayList<>();
        List<Player> killshot = GameContext.get().getGame(groupID).getBoard().getKillshotTrack();
        for(Player p : this) {
            if (p.getPhase() != Phase.DISCONNECTED) {
                players.add(p);
            }
        }
        List<Player> winners = new ArrayList<>(players);
        for(Player p: players){
            Player player = players.get(0);
            if(p.getPoints()< player.getPoints() &&
                        Collections.frequency(killshot, p)<Collections.frequency(killshot, player)){
                winners.remove(p);
            }
        }
        return winners;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
