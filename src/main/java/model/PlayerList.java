package model;

import model.enums.Phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO javadoc
public class PlayerList extends ArrayList<Player> {
    private int counter = 0;


    public Player next(){
        counter++;
        if(counter>=super.size()) counter = 0;
        return super.get(counter);
    }

    public Player nextCurrentPlayer(int groupID){
        Player player = null;
        for(int i=0; i<GameContext.get().getGame(groupID).getPlayers().size(); i++){
            if(GameContext.get().getGame(groupID).getPlayers().get(i).equals(
                    GameContext.get().getGame(groupID).getCurrentPlayer())) {
                //If i overflows the number of players, it comes back to 0
                if(i+1 == GameContext.get().getGame(groupID).getPlayers().size()) {
                    player = GameContext.get().getGame(groupID).getPlayers().get(0);
                } else {
                    player = GameContext.get().getGame(groupID).getPlayers().get(i+1);
                }
                break;
            }
        } return player;
    }

    public List<Player> findHighest(int groupID){
        List<Player> players = new ArrayList<>();
        List<Player> killshot = GameContext.get().getGame(groupID).getBoard().getKillshotTrack();
        for(Player p : this) {
            if (p.getPhase() != Phase.DISCONNECTED) {
                players.add(p);
            }
        }
        System.out.println("Players not disconnected: "+ players);
        List<Player> winners = new ArrayList<>(players);
        for(Player p: players){
            Player player = players.get(0);
            if(p.getPoints()< player.getPoints() &&
                        Collections.frequency(killshot, p)<Collections.frequency(killshot, player)){
                System.out.println("Removing: "+p);
                winners.remove(p);
            }
            System.out.println("Winners left: "+winners);
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
