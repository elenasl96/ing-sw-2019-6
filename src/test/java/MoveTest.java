import model.Ammo;
import model.Player;
import model.enums.Color;
import model.field.AmmoSquare;
import model.field.Square;
import model.moves.Move;
import model.moves.Movement;
import model.moves.Pay;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveTest {
    private Pay payment;
    private Player player = new Player(1, true);

    @Test
    public void paymentTest(){
        Ammo yellowAmmo = new Ammo(Color.YELLOW);
        Ammo blueAmmo = new Ammo(Color.BLUE);
        Ammo redAmmo = new Ammo(Color.RED);
        payment.getAmmos().add(yellowAmmo);
        payment.getAmmos().add(blueAmmo);
        payment.getAmmos().add(redAmmo);
        assertTrue(payment.getAmmos().iterator().hasNext());
        assertEquals(yellowAmmo, payment.getAmmos().iterator().next());
    }
}
