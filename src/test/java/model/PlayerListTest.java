package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerListTest {
    @Test
    void test(){
        PlayerList list = new PlayerList();
        list.add(new Player());

        list.next().setName("1");

        assertEquals("1",list.next().getName());
    }
}
