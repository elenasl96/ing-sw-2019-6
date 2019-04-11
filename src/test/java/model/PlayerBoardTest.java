package model;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class PlayerBoardTest {
    @Test
    void test1(){
        PlayerBoard pb = new PlayerBoard();
        Player p = new Player(1, true);
        pb.addDamage(p, 1);
        assertEquals(p,pb.getDamage().get(0));
        pb.addDeath();
        assertEquals(1, pb.getDeaths());
    }
}
