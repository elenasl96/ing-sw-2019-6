package model.field;

import model.exception.NotExistingFieldException;
import org.junit.jupiter.api.Test;

public class FieldTest {

    @Test
    void test() throws NotExistingFieldException {
        Field field1 = new Field(1);
        Field field2 = new Field(2);
        Field field3 = new Field(3);
        //TODO asserts
    }
}
