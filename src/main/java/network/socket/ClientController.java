package network.socket;

import model.enums.Character;
import network.socket.commands.request.*;
import network.socket.commands.response.*;
import network.socket.commands.Response;
import network.socket.commands.ResponseHandler;
import network.networkExceptions.InvalidGroupNumberException;
import model.room.Command;
import model.room.Group;
import model.room.Message;
import model.room.User;
import network.socket.launch.Client;

/**
 * CLIENT-SIDE controller
 *
 * It holds a reference to the view for sending sudden responses.
 * It holds a reference to the networking layer.
 */

public class ClientController implements ResponseHandler {
    // reference to networking layer
    private final Client client;
    private Thread receiver;

    // the view
    private final ViewClient view;

    public ClientController(Client client) {
        this.client = client;
        this.view = new ViewClient(this);
    }

    /**
     *
     * @param username
     * @return the created user or null in case of failure
     */
    public User createUser(String username) {
        client.request(new CreateUserRequest(username));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser();
    }

    public Group chooseGroup(int groupNumber) throws InvalidGroupNumberException {
        client.request(new ChooseGroupRequest(groupNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup();
    }

    public String getSituation(){
        client.request(new SituationViewerRequest());
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentSituation();
    }

    public int createGroup(int skullNumber, int fieldNumber) {
        client.request(new CreateGroupRequest(skullNumber, fieldNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentGroup().getGroupID();
    }

    public Character setCharacter(int characterNumber){
        client.request(new SetCharacterRequest(characterNumber));
        client.nextResponse().handle(this);
        return ClientContext.get().getCurrentUser().getCharacter();
    }

    public void startReceiverThread() {
        // start a receiver thread
        receiver = new Thread(
                () -> {
                    Response response;
                    do {
                        response = client.nextResponse();
                        if (response != null) {
                            response.handle(this);
                        }
                    } while (response != null);
                }
        );
        receiver.start();
    }

    public void sendCommand(String content){
        switch (content){
            case "run":

            case "grab":

            case "shoot":

            case "mark":
        }
        Command command = new Command(ClientContext.get().getCurrentGroup(),
                ClientContext.get().getCurrentUser(), content);
        client.request(new SendCommandRequest(command));

    }

    public void sendMessage(String content) {
        Message m = new Message(ClientContext.get().getCurrentGroup(),
                ClientContext.get().getCurrentUser(), content);
        client.request(new SendMessageRequest(m));
    }


    public void run(){
        view.chooseUsernamePhase();
        view.chooseGroupPhase();
        view.messagingPhase();
        view.preGamingPhase();
        view.gamingPhase();

        receiver.interrupt();
    }

    //Fot test purposes
    public void mockRun(String userName, int groupID){
        User user = createUser(userName);
        user.listenToMessages(view);

        Group group = chooseGroup(groupID);
        group.observe(view);
        view.setWait(false);
        view.messagingPhase();
        view.preGamingPhase();
    }
    // -------------------------- Response handling

    @Override
    public void handle(TextResponse textResponse) {
        view.displayText(textResponse.toString());
    }

    @Override
    public void handle(JoinGroupResponse joinGroupResponse) {
        ClientContext.get().setCurrentGroup(joinGroupResponse.group);
    }

    @Override
    public void handle(UserCreatedResponse userCreatedResponse) {
        ClientContext.get().setCurrentUser(userCreatedResponse.user);
    }

    @Override
    public void handle(SituationViewerResponse situationViewerResponse){
        ClientContext.get().setCurrentSituation(situationViewerResponse.situation);
    }

    @Override
    public void handle(GeneralResponse generalResponse) {
        ClientContext.get().setStatus(generalResponse.status);
    }

    @Override
    public void handle(SetCharacterResponse setCharacterResponse) {
        ClientContext.get().getCurrentUser().setCharacter(setCharacterResponse.character);
    }

}
