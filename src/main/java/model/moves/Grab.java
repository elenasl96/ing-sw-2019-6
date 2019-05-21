package model.moves;

import exception.NothingGrabbableException;
import model.Player;
import model.room.Update;
import network.socket.commands.Response;
import network.socket.commands.response.AskInput;

public class Grab implements Move{
    @Override
    public Response execute(Player p, int groupID) {
        try{
            if(p.getCurrentPosition().getGrabbable()==null) { throw new NothingGrabbableException(); }
            p.getUser().receiveUpdate(new Update(
                    "Insert the weapon you want to pick:" +  p.getCurrentPosition().getGrabbable().toString()));
            return new AskInput("weapon choose");
        } catch (NothingGrabbableException e){

        }
        return null;
    }
}
