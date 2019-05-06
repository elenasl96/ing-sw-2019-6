package model.decks;

import model.Game;
import model.Player;
import model.enums.Color;
import model.room.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecksTest {
    @Test
    void PickTest(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("1"));
        Game game = new Game(5,2, users);
        Player pg1 = new Player();
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        pg1.getPowerups().add(game.getBoard().getPowerupsLeft().pickCard());
        System.out.println(pg1.getPowerups().get(0).getAmmo().getColor());
        System.out.println(pg1.getPowerups().get(1).getAmmo().getColor());
        System.out.println(pg1.getPowerups().get(2).getAmmo().getColor());
        System.out.println( pg1.getPowerups().get(3).getAmmo().getColor().toString());
        System.out.println(pg1.getPowerups().get(4).getAmmo().getColor());
        System.out.println(pg1.getPowerups().get(5).getAmmo().getColor());
    }
}
