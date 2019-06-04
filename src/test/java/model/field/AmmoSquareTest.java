package model.field;

import model.GameContext;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.Test;

public class AmmoSquareTest {

    @Test
    void test(){
        Manager.get().reset();
        GameContext.get().reset();
        try {
            Manager.get().createGroup(5,1);
            Manager.get().createUser("1");
            Manager.get().createUser("2");
            Manager.get().createUser("3");
            Manager.get().getUsers().forEach(u -> Manager.get().getGroup(0).join(u));
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        Manager.get().getGroup(0).createGame();
        AmmoSquare ammoSquare = new AmmoSquare();
        ammoSquare.addGrabbable(null, 0);

    }
}
