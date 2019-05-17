package model;

import java.util.ArrayList;

public class PlayerList extends ArrayList<Player> {
    private int counter = 0;


    public Player next(){
        counter++;
        if(counter>=super.size()) counter = 0;
        return super.get(counter);
    }

    public void nextCurrentPlayer(int groupID){
        for(int i=0; i<GameContext.get().getGame(groupID).getPlayers().size(); i++){
            if(GameContext.get().getGame(groupID).getPlayers().get(i).equals(
                    GameContext.get().getGame(groupID).getCurrentPlayer())) {
                //If i overflows the number of players, it comes back to 0
                if(i+1 == GameContext.get().getGame(groupID).getPlayers().size()) {
                    GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().get(0));
                } else {
                    GameContext.get().getGame(groupID).setCurrentPlayer(GameContext.get().getGame(groupID).getPlayers().get(i+1));
                }
                break;
            }
        }
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
