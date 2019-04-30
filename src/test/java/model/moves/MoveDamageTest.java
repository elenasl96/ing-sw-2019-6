package model.moves;


import model.Player;
import model.enums.Character;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveDamageTest {
    private Player playerDamaging;
    private Player playerDamaged;
    private Damage damage = new Damage();

    @Test
    public void damageTest(){
        playerDamaging = new Player(1, true, "pippo", Character.PG1);
        playerDamaged = new Player(2, false, "paperino", Character.PG3);
        damage = new Damage(playerDamaged, 6);
        damage.execute(playerDamaging);
        assertEquals(6, playerDamaged.getPlayerBoard().getNumDamageLeft());
        damage.execute(playerDamaging);
        assertEquals(0, playerDamaged.getPlayerBoard().getNumDamageLeft());
        damage.execute(playerDamaging);
        assertEquals(0, playerDamaged.getPlayerBoard().getNumDamageLeft());

    }


}
