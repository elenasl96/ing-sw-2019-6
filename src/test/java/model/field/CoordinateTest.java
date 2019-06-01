package model.field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTest {
    @Test
    void test(){
        Coordinate coordinate = new Coordinate('A', 4);
        int x = coordinate.hashCode();
        int y = coordinate.hashCode();
        assertEquals(x,y);
    }
}
