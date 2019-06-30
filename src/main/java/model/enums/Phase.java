package model.enums;

import network.commands.response.MoveUpdateResponse;

/**
 * The phase of the player's turn
 */
public enum Phase{

    WAIT(0),
    FIRST(1),
    SECOND(2),
    RELOAD(3),
    SPAWN (4),
    POWERUP1(5),
    POWERUP2(6),
    POWERUP3(7),
    DISCONNECTED(8);
    /**
     * Used for first time spawn: different from the normal spawn because the player
     * can't perform his turn after the spawn.
    FIRST_SPAWN(6);*/
    private int id;

    Phase(int id) {
        this.id=id;
    }

    public int getId(){
       return id;
    }
    /**
     * Returns the phase object corresponding to the integer.
     * It's used client side to pass the a number instead of a phase type object, for serialization.
     * @see controller.ClientController#handle(MoveUpdateResponse)
     * @param x   the integer
     * @return    the phase
     */
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
            case 5:
                return POWERUP1;
            case 6:
                return POWERUP2;
            case 7:
                return POWERUP3;
            case 8:
                return DISCONNECTED;
            default:
                return null;
        }
    }

    public boolean equalsTo(Phase phase){
        return this.id == phase.id;
    }
}
