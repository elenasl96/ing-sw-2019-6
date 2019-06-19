package controller;

import model.GameContext;
import model.Player;
import model.decks.Weapon;
import model.enums.WeaponStatus;
import model.exception.InvalidMoveException;
import model.room.Group;
import model.room.User;
import network.Manager;
import network.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShootControllerTest {
    private ArrayList<User> users;

    @BeforeEach
    void start(){
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame(0);
        Group group0 = Manager.get().getGroup(0);
        users = new ArrayList<>();
        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));
        users.add(new User("user4"));
        try {
            Manager.get().createUser("user1");
            Manager.get().createUser("user2");
            Manager.get().createUser("user3");
            Manager.get().createUser("user4");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            group0.join(Manager.get().getUsers().get(i));
        }
        group0.createGame();
        group0.getGame().getCurrentPlayer().setCurrentPosition(group0.getGame().getBoard().getField().getSquares().get(0));
    }

    @Test
    void generateMatrixTest(){
        String[][] matrix = ShootController.get().generateMatrix("C 3:User2:User3;User3:Square1;User1");
        System.out.println(Arrays.deepToString(matrix));
    }

    @Test
    void notExistinPlayerForTarget(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(0));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0) + p1.getAmmos().toString());
        String weaponsEffect = "3 0 1";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        // Choose a non existing player
        final String weaponChosen = "elena,elena";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
            GameController.get().playWeapon(p1, weaponChosen, 0);
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void LockRifleTestWithDuplicateInput(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(0));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0) + p1.getAmmos().toString());
        String weaponsEffect = "3 0 1";
        System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        //A:B:C;D:E   -> first effect 3 fields, second effect 2 fields
        //test effects on lock rifle
        final String weaponChosen = "user2;user2";
        assertThrows(InvalidMoveException.class, ()-> GameController.get().playWeapon(p1, weaponChosen, 0));

        final String weaponChosen2 = "user2;user3";
        GameController.get().playWeapon(p1, weaponChosen2, 0);
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getMarks().size());
        assertThrows(InvalidMoveException.class, () -> p1.getWeapons().get(0).getEffectsList().get(0).getEffects().get(1).getTarget().get(0).getCurrentPosition());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void electoscytheTestWithNoInput(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(1));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on lockrifle
        String weaponChosen = "";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void MachineGunTestWithHalfBasic(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(2));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on machine gun with basic effect working
        String weaponChosen = "user2";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        //assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void PlayWeapon3(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        Player p4 = GameContext.get().getGame(0).getPlayers().get(3);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(3));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 1";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on tractor beam
        String weaponChosen = "user2,C 3";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(3, p2.getPlayerBoard().getDamage().size());
        // assertEquals(1, p3.getPlayerBoard().getDamage().size());
        //assertEquals(2, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }


    @Test
    void PlayWeapon4(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        Player p4 = GameContext.get().getGame(0).getPlayers().get(3);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(4));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0 1 2";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on thor with basic and alternative effect working
        String weaponChosen = "user2;user3;user4";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(2, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void checkDifferentInputTest(){
        String[][] inputMatrix = new String[][]{
                { "user1", "user2", "user3" },
                { "user2", "user5", "user4" }
        };
        assertThrows(InvalidMoveException.class,
                () -> ShootController.get().checkDifferentInputs(inputMatrix));

        String[][] inputMatrix2 = new String[][]{
                { "user1", "", "user3" },
                { "user6", "user5", "user4" }
        };
        assertDoesNotThrow(() -> ShootController.get().checkDifferentInputs(inputMatrix2));
    }

}