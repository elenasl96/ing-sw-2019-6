package network.socket.commands.request;

import network.socket.commands.Request;
import network.socket.commands.RequestHandler;
import network.socket.commands.Response;

public class SendInput implements Request {

    private String input;
    private String inputType;

    public SendInput(String askInput, String inputType) {
        this.input = askInput;
        this.inputType = inputType;
    }

    public String getInput(){
        return this.input;
    }

    public String getInputType() {
        return inputType;
    }

    @Override
    public Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
