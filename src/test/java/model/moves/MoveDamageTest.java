package model.moves;


import model.Player;
import model.enums.Character;
import org.junit.jupiter.api.Disabled;

import static model.enums.EffectType.MOVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveDamageTest {
    private Player playerDamaging;
    private Player playerDamaged;
    private DamageEffect damage;

    @Disabled
    public void damageTest(){
        playerDamaging = new Player();
        playerDamaged = new Player();
        damage = new DamageEffect(MOVE, Target.addTargetList(playerDamaged), 6, false, null);
        damage.execute(playerDamaging, 0);
        assertEquals(6, playerDamaged.getPlayerBoards(0).get(0).getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoards(0).get(0).getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoards(0).get(0).getNumDamageLeft());

    }
}
