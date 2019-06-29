package model.decks;

import model.GameContext;
import model.enums.Color;
import model.exception.NotEnoughAmmoException;
import model.exception.NotExistingFieldException;
import model.room.Group;
import network.Manager;
import network.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmmoTileTest {

    @BeforeEach
    void init() throws NotExistingFieldException {
        Manager.get().reset();
        GameContext.get().reset();
        Manager.get().createGroup(5,1);
        GameContext.get().createGame(1);
        Group group1 = Manager.get().getGroup(1);
        try {
            Manager.get().createUser("user1");
            Manager.get().createUser("user2");
            Manager.get().createUser("user3");
            Manager.get().createUser("user4");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            group1.join(Manager.get().getUsers().get(i));
        }
        group1.createGame();
        group1.getGame().getCurrentPlayer().setCurrentPosition(group1.getGame().getBoard().getField().getSquares().get(0));
    }

    @Test
    void test() throws NotEnoughAmmoException {
        AmmoTile wPowerup = new AmmoTileWithPowerup(Color.BLUE, Color.YELLOW);
        AmmoTile wAmmo = new AmmoTileWithAmmo(Color.BLUE, Color.YELLOW, Color.YELLOW);
        wPowerup.pickGrabbable(1,1);
        wAmmo.pickGrabbable(1,1);
    }
}
