package controller;

import model.Game;
import model.decks.Weapon;
import model.exception.InvalidMoveException;
import model.Ammo;
import model.GameContext;
import model.Player;
import model.decks.Powerup;
import model.decks.WeaponDeck;
import model.enums.Color;
import model.enums.Phase;
import model.enums.WeaponStatus;
import model.field.Coordinate;
import model.field.Field;
import model.moves.Run;
import model.room.Group;
import model.room.Update;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
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
    void constructorTest(){
        assertEquals(users.get(0), GameContext.get().getGame(0).getCurrentPlayer().getUser());
        assertEquals(Phase.FIRST_SPAWN, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
        assertEquals(Phase.WAIT, GameContext.get().getGame(0).getPlayers().get(1).getPhase());
    }

    @Test
    void runMoveHandleTest(){
        Run invalidRun = new Run(new Coordinate('D', 4));
        assertThrows(InvalidMoveException.class, () ->
                invalidRun.execute(GameContext.get().getGame(0).getCurrentPlayer(), 0) );
        Run run = new Run(new Coordinate('A', 2));
        GameContext.get().getGame(0).getCurrentPlayer().setCurrentPosition(
                GameContext.get().getGame(0).getBoard().getField().getSquares().get(0));
        assertDoesNotThrow(() -> run.execute(GameContext.get().getGame(0).getCurrentPlayer(), 0) );
    }

    @Test
    void isMyTurnTest(){
        assertTrue(GameController.get().isMyTurn(GameContext.get().getGame(0).getCurrentPlayer(), 0));
    }

    @Test
    void possibleMovesTest(){
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.RELOAD);
        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.FIRST);
        GameContext.get().getGame(0).getCurrentPlayer().getPowerups().add(new Powerup("dummy", new Ammo(Color.BLUE)));
        GameController.get().possibleMoves(GameContext.get().getGame(0). getCurrentPlayer(), 0);
        GameContext.get().getGame(0).setFinalFrenzy(true);
        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);
        GameContext.get().getGame(0).setCurrentPlayer(GameContext.get().getGame(0).getPlayers().next());
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.FIRST);
        GameController.get().possibleMoves(GameContext.get().getGame(0).getCurrentPlayer(), 0);

    }

    @Test
    void generateMatrixTest(){
        String[][] matrix = GameController.get().generateMatrix("C 3:User2:User3;User3:Square1;User1");
        System.out.println(Arrays.deepToString(matrix));
    }

    @Test
    void playWeaponTest(){
        WeaponDeck deck = new WeaponDeck();
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(0));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 0 1";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        // Choose a non existing player
        String weaponChosen = "elena:elena";
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect));
            GameController.get().playWeapon(p1, weaponChosen, 0);
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //A:B:C;D:E   -> first effect 3 fields, second effect 2 fields
        //test effects on lock rifle
        weaponChosen = "user2;user3";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getMarks().size());
        assertEquals(1, p3.getPlayerBoard().getMarks().size());
        assertThrows(InvalidMoveException.class, () -> p1.getWeapons().get(0).getEffectsList().get(0).getEffects().get(1).getTarget().get(0).getCurrentPosition());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void PlayWeapon2(){
        WeaponDeck deck = new WeaponDeck();
        Player p1 = GameContext.get().getGame(0).getPlayers().get(0);
        Player p2 = GameContext.get().getGame(0).getPlayers().get(1);
        Player p3 = GameContext.get().getGame(0).getPlayers().get(2);
        for(Player p: GameContext.get().getGame(0).getPlayers()){
            p.setCurrentPosition(GameContext.get().getGame(0).getBoard().getField().getSpawnSquares().get(0));
        }
        p1.getWeapons().add(new Weapon().initializeWeapon(1));
        p1.getWeapons().get(0).setStatus(WeaponStatus.LOADED);
        System.out.println(p1.getWeapons().get(0));
        String weaponsEffect = "3 1";
        System.out.println(GameContext.get().getGame(0).getPlayers().size());
        try {
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on electroscythe with basic and alternative effect working
        String weaponChosen = "C 3";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(2, p2.getPlayerBoard().getDamage().size());
        assertEquals(2, p3.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void PlayWeapon3(){
        WeaponDeck deck = new WeaponDeck();
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
            System.out.println(GameController.get().prepareWeapon(p1, weaponsEffect));
        }catch(IndexOutOfBoundsException | InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        //test effects on electroscythe with basic and alternative effect working
        String weaponChosen = "user2:user3";
        GameController.get().playWeapon(p1, weaponChosen, 0);
        assertEquals(0, p1.getPlayerBoard().getDamage().size());
        assertEquals(1, p2.getPlayerBoard().getDamage().size());
        assertEquals(1, p3.getPlayerBoard().getDamage().size());
        assertEquals(WeaponStatus.UNLOADED,p1.getWeapons().get(0).getStatus());
    }

    @Test
    void SpawnTest(){
        //getFirstTimeSpawn
        Update possibleMovesUpdate = GameController.get().getSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(), 0);
        assertFalse(possibleMovesUpdate.isPlayerChanges());
        assertFalse(GameContext.get().getGame(0).getCurrentPlayer().getPowerups().isEmpty());
        //Not working Spawn
        GameController.get().setFirstSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(),
                5, 0);

        //Working spawn
        GameController.get().setFirstSpawn(
                GameContext.get().getGame(0).getCurrentPlayer(),
                0, 0);
    }

    @Test
    void UpdatePhaseTest() {
        GameContext.get().getGame(0).getCurrentPlayer().setPhase(Phase.FIRST);
        GameController.get().updatePhase(0);
        assertEquals(Phase.SECOND, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
        GameController.get().updatePhase(0);
        assertEquals(Phase.RELOAD, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
        GameController.get().updatePhase(0);
        assertEquals(Phase.FIRST, GameContext.get().getGame(0).getCurrentPlayer().getPhase());
    }

}
