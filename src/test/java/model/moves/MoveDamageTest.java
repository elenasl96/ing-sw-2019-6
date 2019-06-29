package model.moves;


import model.Player;
import model.enums.Character;
import model.exception.NotExistingPositionException;
import org.junit.jupiter.api.Disabled;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveDamageTest {
    private Player playerDamaging;
    private Player playerDamaged;
    private DamageEffect damage;

    @Disabled
    public void damageTest() throws NotExistingPositionException {
        playerDamaging = new Player();
        playerDamaged = new Player();
        damage = new DamageEffect(Stream.of(playerDamaged), 6, false);
        damage.execute(playerDamaging, 0);
        assertEquals(6, playerDamaged.getPlayerBoard().getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoard().getNumDamageLeft());
        damage.execute(playerDamaging, 0);
        assertEquals(0, playerDamaged.getPlayerBoard().getNumDamageLeft());

    }
}
