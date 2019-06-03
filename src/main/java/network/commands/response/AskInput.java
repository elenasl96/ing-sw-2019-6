package network.commands.response;

import model.moves.Effect;
import network.commands.Response;
import network.commands.ResponseHandler;

public class AskInput implements Response {
    String inputType;
    private StringBuilder display;
    Effect effect;

    public AskInput(String inputType){
        this.inputType = inputType;
        this.display = new StringBuilder();
        display.append(inputType);
    }

    public void append(String string){
        this.display.append(string);
    }

    public void setEffect(boolean target) {
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
