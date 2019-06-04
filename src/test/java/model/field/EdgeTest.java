package model.field;

import model.enums.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EdgeTest {

    @Test
    void test(){
        Edge edge = new Edge(new AmmoSquare(Color.BLUE, new Coordinate('A', 4)), new AmmoSquare(Color.BLUE, new Coordinate('A', 3)));
        Square sq1 = edge.getSq2();
        assertNotEquals(sq1,edge.getSq1());
    }
}
