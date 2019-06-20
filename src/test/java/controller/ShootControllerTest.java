package controller;

import model.Game;
import model.GameContext;
import model.Player;
import model.decks.Weapon;
import model.enums.WeaponStatus;
import model.exception.InvalidMoveException;
import model.field.Field;
import model.field.Square;
import model.moves.Target;
import model.room.Group;
import model.room.User;
import network.Manager;
import network.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static model.enums.TargetType.NONE;
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
        }

    @Test
    void generateMatrixTest(){
        String[][] matrix = ShootController.get().generateMatrix("C 3:User2:User3;User3:Square1;User1");
        System.out.println(Arrays.deepToString(matrix));
    }

    @Test
    void notExistinPlayerForTarget(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
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
    void TractorBeamTest(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(6));
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(3));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //TODO test from multiple squares of p2.
        // If destination is different from C 3, returns "You can't move there"
        //test effects on tractor beam
        String weaponChosen = "user2,C 3";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        // assertEquals(1, p3.getPlayerBoard().getDamage().size());
        //assertEquals(2, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }


    @Test
    void ThorTestWithBasicVisible(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(1));
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        p3.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(4));
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
        //TODO why it also accepts players not basic_visible?
        String weaponChosen = "user2;user3;user4";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(2, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void PlasmaGunTestWithMovementMe(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(5));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0 1 2";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        String weaponChosen = "user2;C 2";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals("C 2", p1.getCurrentPosition().toString());
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(3, p2.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void WhisperTest(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        }
        //TODO DO more tests with different positions
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(2));
        System.out.println("Positions: " + p1.getCurrentPosition().toString() + p2.getCurrentPosition().toString());
        p1.getWeapons().add(new Weapon().initializeWeapon(6));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        String weaponChosen = "user2";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(3, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getMarks().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void VortexCannonWithTargetMovement(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        Player p4 = GameContext.get().getGame(0).getPlayers().get(3);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        }
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(2));
        p3.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        p4.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        System.out.println("Positions: " + p1.getCurrentPosition().toString() + p2.getCurrentPosition().toString());
        p1.getWeapons().add(new Weapon().initializeWeapon(7));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0 1";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }
        //TODO more tests with other positions
        String weaponChosen = "user2,b 3;user3;user4";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals("B 3", p2.getCurrentPosition().toString());
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(1, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void FurnaceTestWithRoomTarget(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        Player p4 = GameContext.get().getGame(0).getPlayers().get(3);
        p1.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        p3.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(1));
        p4.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(2));
        System.out.println("Positions: " + p1.getCurrentPosition().toString() + p2.getCurrentPosition().toString());
        p1.getWeapons().add(new Weapon().initializeWeapon(8));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }
        String weaponChosen = "blue";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(1, p4.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void HeatSeekerTestWithNOT_VISIBLE(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        p1.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(1));
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(3));
        System.out.println("Positions: " + p1.getCurrentPosition().toString() + p2.getCurrentPosition().toString());
        p1.getWeapons().add(new Weapon().initializeWeapon(9));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }
        String weaponChosen = "user2";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(3, p2.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void HellionTestWithSquareBASIC_EQUALS(){
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        Player p4 = GameContext.get().getGame(0).getPlayers().get(3);
        p1.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(2));
        p2.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(3));
        p3.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(3));
        p4.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSquares().get(4));
        p1.getWeapons().add(new Weapon().initializeWeapon(10));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0";
        assertEquals(0, p4.getPlayerBoard().getMarks().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect, 0));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }
        System.out.println("marks4: " +
                p4.getPlayerBoard().getMarks().size());
        String weaponChosen = "user2";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        System.out.println("Positions: " +
                p1.getCurrentPosition().toString() +
                p2.getCurrentPosition().toString() +
                p3.getCurrentPosition().toString() +
                p4.getCurrentPosition().toString()
        );
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getMarks().size());
        assertEquals(0, p4.getPlayerBoard().getMarks().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void checkMinDistanceTest(){
        Player target = new Player(NONE, NONE, 2,2);
        GameContext.get().getGame(0).setCurrentPlayer(target);
        Field field = GameContext.get().getGame(0).getBoard().getField();
        target.setCurrentPosition(field.getSquares().get(3));
        Square square = field.getSquares().get(1);
        ShootController.get().checkMinDistance(target.getMinDistance(), square, 0);
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