package model.moves;

import exception.InvalidMoveException;
import exception.NotExistingFieldException;
import model.Board;
import model.GameContext;
import model.Player;
import model.enums.Character;
import model.enums.Color;
import model.field.*;
import model.room.Group;
import model.room.User;
import network.exceptions.InvalidUsernameException;
import network.socket.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovementTest {
    private Field field;
    private Board board = new Board(2);

    @BeforeEach
    void createField(){
        Manager.get().reset();
        GameContext.get().reset();
        GameContext.get().createGame(0);
        Group group0 = Manager.get().getGroup(0);
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

    @Disabled
    void gettersAndSetters(){
        Movement movement = new Movement(field);
        assertEquals(field, movement.getField());

        movement.setMaxSteps(3);
        assertEquals(3, movement.getMaxSteps());

        Square destination = new SpawnSquare(Color.BLUE, new Coordinate('B',2), board);
        movement.setDestination(destination);
        assertEquals(destination, movement.getDestination());

        Field field2 = new Field(1, board);
        field.getSquares().add(new AmmoSquare(Color.BLUE, new Coordinate('B',2)));
        field.getSquares().add(new AmmoSquare(Color.BLUE, new Coordinate('B',2)));
        field.getSquares().add(new SpawnSquare(Color.BLUE, new Coordinate('B',2), board));
        field.getEdges().add(new Edge(field.getSquares().get(0), field.getSquares().get(1)));
        field.getEdges().add(new Edge(field.getSquares().get(1), field.getSquares().get(2)));
        movement.setField(field2);
        assertEquals(field2, movement.getField());

        movement.setReachList(field.getSquares());
        assertEquals(field.getSquares(), movement.getReachList());

    }
    @Disabled
    void test1(){
        Player p = new Player(new User("mickey"));

        createField();

        Movement movement = new Movement(this.field);
        movement.setMaxSteps(1);
        
        assertEquals(movement.getField(),field);


        p.setCurrentPosition(field.getSquares().get(1));
        movement.setDestination(field.getSquares().get(0));
        try{
            movement.execute(p, 0);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p, 0);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(3), p.getCurrentPosition());

        movement.setMaxSteps(3);
        movement.setDestination(field.getSquares().get(3));

        try{
            movement.execute(p, 0);
        } catch (InvalidMoveException ime) {
            System.out.println(ime.getMessage());
        }

        assertEquals(field.getSquares().get(3), p.getCurrentPosition());

        movement.setMaxSteps(1);
        movement.setDestination(field.getSquares().get(6));

        assertThrows(InvalidMoveException.class, () -> movement.execute(p, 0));
    }
}
