package model;
import model.enums.Color;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class AmmoTest {
    @Test
    void equalsTest(){
        Ammo ammo1 = new Ammo(Color.BLUE);
        Ammo ammo2 = new Ammo(Color.BLUE);

        assertEquals(ammo1, ammo2);
    }
}
