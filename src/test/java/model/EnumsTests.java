package model;

import model.enums.Character;
import model.enums.Phase;
import org.junit.jupiter.api.Test;

import static model.enums.Character.*;
import static model.enums.Phase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumsTests {

    @Test
    void CharacterTest(){
        assertEquals(PG3, Character.fromInteger(3));
        assertEquals(PG1, Character.fromInteger(1));
        assertEquals(PG2, Character.fromInteger(2));
        assertEquals(PG4, Character.fromInteger(4));
        assertEquals(PG5, Character.fromInteger(5));
        assertEquals(NOT_ASSIGNED, Character.fromInteger(11));
    }

    @Test
    void PhaseTest(){
        assertEquals(WAIT, Phase.fromInteger(0));
        assertEquals(FIRST, Phase.fromInteger(1));
        assertEquals(SECOND, Phase.fromInteger(2));
        assertEquals(RELOAD, Phase.fromInteger(3));
        assertEquals(SPAWN, Phase.fromInteger(4));

        Phase phase = SPAWN;
        assertTrue(phase.equalsTo(SPAWN));
    }

}
