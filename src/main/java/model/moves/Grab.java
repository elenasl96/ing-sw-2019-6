package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import exception.NothingGrabbableException;
import model.Game;
import model.GameContext;
import model.Player;
import model.decks.Grabbable;
import model.field.Square;
import network.socket.commands.Response;

public class Grab implements Move{
    private Square square;

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public void execute(Player p, int groupID) throws InvalidMoveException {
        /*
        Grabbable grabbable=p.getCurrentPosition().getGrabbable();
        if(grabbable==null) { throw new NothingGrabbableException(); }
        grabbable.pickGrabbable(groupID);
        p.getCurrentPosition().addGrabbable(GameContext.get().getGame(groupID).getBoard());
         */
    }

    @Override
    public Response handle(MoveRequestHandler moveRequestHandler, int groupId) throws InvalidMoveException {
        return null;//TODO
    }



}
