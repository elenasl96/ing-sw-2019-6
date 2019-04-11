package socket.network.commands;

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
