package model;

import java.util.ArrayList;

public class PlayerList extends ArrayList<Player> {
    private int counter = 0;


    public Player next(){
        counter++;
        if(counter>=super.size()) counter = 0;
        return super.get(counter);
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
