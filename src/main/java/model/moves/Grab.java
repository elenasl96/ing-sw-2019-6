package model.moves;

import controller.MoveRequestHandler;
import exception.InvalidMoveException;
import exception.NothingGrabbableException;
import model.Game;
import model.GameContext;
import model.Player;
import model.decks.Grabbable;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.response.AskInput;

public class Grab implements Move{

    @Override
    public Response execute(Player p, int groupID) throws InvalidMoveException {
        try{
            p.getUser().receiveUpdate(new Update(
                    "Insert the weapon you want to pick:" +  p.getCurrentPosition().getGrabbable().toString()));
            return new AskInput("weapon choose");
        } catch (NothingGrabbableException e){
        }
        /*
        Grabbable grabbable=p.getCurrentPosition().getGrabbable();
        if(grabbable==null) { throw new NothingGrabbableException(); }
        grabbable.pickGrabbable(groupID);
        p.getCurrentPosition().addGrabbable(GameContext.get().getGame(groupID).getBoard());
         */
        return null;
    }




}
