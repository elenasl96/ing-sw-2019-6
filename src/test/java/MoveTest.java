import exception.NotEnaughAmmoException;
import model.Ammo;
import model.Player;
import model.enums.Color;
import model.field.AmmoSquare;
import model.field.Square;
import model.moves.Move;
import model.moves.Movement;
import model.moves.Pay;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MoveTest {
    private Pay payment;
    private Player player = new Player(1, true);

    @Test
    public void paymentTest(){
        payment = new Pay();
        Ammo yellowAmmo = new Ammo(Color.YELLOW);
        Ammo blueAmmo = new Ammo(Color.BLUE);
        Ammo redAmmo = new Ammo(Color.RED);

        ArrayList<Ammo> ammoList = new ArrayList<>();

        ammoList.add(yellowAmmo);
        ammoList.add(blueAmmo);
        ammoList.add(redAmmo);
        //ammoList is: <y,b,r>

        //Checking if a new list from getPlayer modifies the player's list or its copy
        player.getAmmos().add(redAmmo);
        ArrayList<Ammo> testList = new ArrayList<>();
        testList = player.getAmmos();
        testList.remove(redAmmo);
        assertFalse(player.getAmmos().isEmpty());

        player.setAmmos(ammoList);
        //player.ammos is: <y,b,r>

        payment.getAmmos().add(blueAmmo);
        payment.getAmmos().add(redAmmo);
        payment.getAmmos().add(yellowAmmo);
        //payment.ammos is: <b,r,y>

        try {payment.execute(player);}
        catch (NotEnaughAmmoException nea){
            System.out.println(nea.getMessage());
        }

        payment.getAmmos().add(blueAmmo);
        try {payment.execute(player);}
        catch (NotEnaughAmmoException nea){
            System.out.println(nea.getMessage());
        }
    }
}
