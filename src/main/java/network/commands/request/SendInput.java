package network.commands.request;

import network.commands.Request;
import network.commands.RequestHandler;
import network.commands.Response;

/**
 * Sent when asked for input as a sort of response
 */
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
