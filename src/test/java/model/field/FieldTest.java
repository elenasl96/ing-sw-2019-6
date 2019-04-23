package model.field;

import exception.NotExistingFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldTest {

    @Test
    void FieldConstructorTest(){
        Field field1 = new Field(1);
        Field field2 = new Field(2);
        Field field3 = new Field(3);
    }
}
