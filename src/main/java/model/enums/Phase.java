package model.enums;

public enum Phase {
    WAIT(0),
    FIRST(1),
    SECOND(2),
    RELOAD(3);

    private int id;

    Phase(int id)
    {
        this.id=id;
    }
}
