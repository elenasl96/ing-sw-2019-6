package model.enums;

public enum Phase{

    WAIT(0),
    FIRST(1),
    SECOND(2),
    RELOAD(3),
    SPAWN (4);

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
            case 4:
                return SPAWN;
            default:
                return WAIT;
        }
    }

    public boolean equalsTo(Phase phase){
        return this.id == phase.id;
    }
}
