package model.decks;

import model.GameContext;
import model.enums.WeaponStatus;
import model.exception.NotEnoughAmmoException;
import model.exception.NotExistingFieldException;
import model.room.Group;
import network.Manager;
import network.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class WeaponTileTest {

    @BeforeEach
    void init() throws NotExistingFieldException {
        Manager.get().reset();
        GameContext.get().reset();
        Manager.get().createGroup(5, 1);
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
        for (int i = 0; i < 4; i++) {
            group1.join(Manager.get().getUsers().get(i));
        }
        group1.createGame();
        group1.getGame().getCurrentPlayer().setCurrentPosition(group1.getGame().getBoard().getField().getSquares().get(0));
    }

    @Test
    void test() throws NotEnoughAmmoException {
        WeaponTile weaponTile = new WeaponTile();
        List<Weapon> weapon = new ArrayList<>();
        weapon.add(new Weapon(0, "1", "2", WeaponStatus.LOADED));
        weaponTile.setWeapons(weapon);
        weaponTile.pickGrabbable(1, 0);
    }
}
