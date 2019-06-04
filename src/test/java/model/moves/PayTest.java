package model.moves;

import model.exception.NotEnoughAmmoException;
import model.Ammo;
import model.Player;
import model.enums.Color;
import model.room.User;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

class PayTest {
    private Pay payment;
    private Player player;
    private int groupId = 0;

    /**
     * tests the payment gone wright
     */
    @Test
    void paymentTest1(){

        payment = new Pay();
        player = new Player(new User("pippo"));
        System.out.println(player.getAmmos());
        Ammo yellowAmmo = new Ammo(Color.YELLOW);
        Ammo blueAmmo = new Ammo(Color.BLUE);
        Ammo redAmmo = new Ammo(Color.RED);

        ArrayList<Ammo> ammoList = new ArrayList<>();

        ammoList.add(new Ammo(Color.YELLOW));
        ammoList.add(new Ammo(Color.BLUE));
        ammoList.add(new Ammo(Color.RED));
        //ammoList is: <y,b,r>

        player.setAmmos(ammoList);
        //player.ammos is: <b,y,r>

        payment.getAmmos().add(blueAmmo);
        payment.getAmmos().add(redAmmo);
        payment.getAmmos().add(yellowAmmo);
        //payment.ammos is: <b,r,y>

        try {
            System.out.println(payment.getAmmos());
            System.out.println(player.getAmmos());
            payment.execute(player, groupId);
            System.out.println(payment.getAmmos());
            System.out.println(player.getAmmos());
        }
        catch (NotEnoughAmmoException nea){
            System.out.println(nea.getMessage());
        }
        assertTrue(player.getAmmos().isEmpty());
    }

    /**
     * tests the payment gone wrong
     * should throw a new NotEnoughAmmoException
     */
    @Test
    void paymentTest2(){
        payment = new Pay();
        player = new Player(new User("pippo"));
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

        player.setAmmos(new ArrayList<>(ammoList));
        //player.ammos is: <b,y,r>
        payment.getAmmos().add(new Ammo(Color.BLUE));

        try {
            System.out.println(payment.getAmmos());
            System.out.println(player.getAmmos());
            System.out.println(player.getAmmos().containsAll(payment.getAmmos()));
            payment.execute(player, groupId);
            System.out.println(player.getAmmos());
        }
        catch (NotEnoughAmmoException nea){
            System.out.println(nea.getMessage());
        }
        System.out.println(ammoList);
        System.out.println(player.getAmmos());
        assertEquals(ammoList, player.getAmmos());
    }
}
