package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

//TODO javadoc
public class CreateGroupRequest implements Request {
    private final int skullNumber;
    private final int fieldNumber;

    public CreateGroupRequest(int skullNumber, int fieldNumber) {
        this.skullNumber = skullNumber;
        this.fieldNumber = fieldNumber;
    }

    public int getSkullNumber(){return this.skullNumber;}

    public int getFieldNumber(){return this.fieldNumber;}

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
