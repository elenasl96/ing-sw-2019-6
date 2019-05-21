package model;
import model.enums.Character;
import model.enums.Color;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class AmmoTest {
    @Test
    void equalsAmmoTest(){
        Ammo ammo1 = new Ammo(Color.BLUE);
        Ammo ammo2 = new Ammo(Color.BLUE);

        assertEquals(ammo1, ammo1);
        assertNotEquals(null, ammo1);

        assertNotEquals(ammo1, null);
        assertEquals(ammo1, ammo2);

        Player p = new Player();

        assertNotEquals(ammo1,p);

        assertNotEquals(ammo1.hashCode(), ammo2.hashCode());
    }

    @Test
    void cloneAmmoTest(){
        Ammo ammo = new Ammo(Color.BLUE);
        assertNotSame(ammo.cloneAmmo(), ammo);
    }
}
