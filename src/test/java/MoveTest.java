import exception.NotEnoughAmmoException;
import model.Ammo;
import model.Player;
import model.enums.Color;
import model.moves.Pay;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MoveTest {
    private Pay payment;
    private Player player;


    /**
     * tests the payment gone wright
     */
    @Test
    public void paymentTest1(){
        payment = new Pay();
        player = new Player(1, true);
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
        //player.ammos is: <b,y,r>

        payment.getAmmos().add(blueAmmo);
        payment.getAmmos().add(redAmmo);
        payment.getAmmos().add(yellowAmmo);
        //payment.ammos is: <b,r,y>

        try {payment.execute(player);}
        catch (NotEnoughAmmoException nea){
            System.out.println(nea.getMessage());
        }
        assertTrue(player.getAmmos().isEmpty());

        player.setAmmos(ammoList);
        payment.getAmmos().add(blueAmmo);
        try {payment.execute(player);}
        catch (NotEnoughAmmoException nea){
            System.out.println(nea.getMessage());
        }
        assertEquals(ammoList, player.getAmmos());
    }

    /**
     * tests the payment gone wrong
     * should throw a new NotEnoughAmmoException
     */
    @Test
    public void paymentTest2(){
        payment = new Pay();
        player = new Player(1, true);
        Ammo yellowAmmo = new Ammo(Color.YELLOW);
        Ammo blueAmmo = new Ammo(Color.BLUE);
        Ammo redAmmo = new Ammo(Color.RED);

        ArrayList<Ammo> ammoList = new ArrayList<>();

        ammoList.add(yellowAmmo);
        ammoList.add(blueAmmo);
        ammoList.add(redAmmo);
        //ammoList is: <y,b,r>

        payment.getAmmos().add(blueAmmo);
        payment.getAmmos().add(redAmmo);
        payment.getAmmos().add(yellowAmmo);
        //payment.ammos is: <b,r,y>

        player.setAmmos(ammoList);
        //player.ammos is: <b,y,r>

        payment.getAmmos().add(blueAmmo);
        try {payment.execute(player);}
        catch (NotEnoughAmmoException nea){
            System.out.println(nea.getMessage());
        }
        assertEquals(ammoList, player.getAmmos());
    }
}
