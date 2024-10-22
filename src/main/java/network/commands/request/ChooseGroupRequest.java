package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent when asking to join a group
 */
public class ChooseGroupRequest implements Request {

    public final int groupId;

    public ChooseGroupRequest(int id){
        this.groupId = id;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
