package network.commands.response;

import network.commands.Response;
import network.commands.ResponseHandler;

//TODO javadoc
public class AskInput implements Response {
    private String inputType;
    private StringBuilder display;

    public AskInput(String inputType){
        this.inputType = inputType;
        this.display = new StringBuilder();
        display.append(inputType);
    }

    public String getInputType(){
        return this.inputType;
    }

    @Override
    public void handle(ResponseHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString(){
        return this.display.toString();
    }
}
