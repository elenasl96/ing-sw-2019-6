package model.moves;


import model.Player;
import model.enums.Character;
import model.room.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static model.enums.EffectType.MOVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveDamageTest {
    private Player playerDamaging;
    private Player playerDamaged;
    private DamageEffect damage;

    @Disabled
    public void damageTest(){
        playerDamaging = new Player(1, true, "pippo", Character.PG1);
        playerDamaged = new Player(2, false, "paperino", Character.PG3);
        damage = new DamageEffect(MOVE, playerDamaged, 6);
        damage.execute(playerDamaging, 0);
        assertEquals(6, playerDamaged.getPlayerBoard(0).get(0).getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoard(0).get(0).getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoard(0).get(0).getNumDamageLeft());

    }


}
