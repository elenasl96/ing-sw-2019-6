package model.enums;

import java.io.Serializable;

public enum Phase implements Serializable {
    WAIT(0),
    FIRST(1),
    SECOND(2),
    RELOAD(3);

    private int id;

    Phase(int id)
    {
        this.id=id;
    }

    public int getId(){
       return id;
    }

    public static Phase fromInteger(int x) {
        switch(x) {
            case 0:
                return WAIT;
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            case 3:
                return RELOAD;
            default:
                return WAIT;
        }
    }
}
