package model.decks;

import org.junit.jupiter.api.Test;

public class WeaponTest {
    @Test
    void test(){
        WeaponDeck wd = new WeaponDeck();
        for(int i = 0; i < 20; i++){
            System.out.println(wd.pickCard());
        }
        assertEmpty()
    }
}
