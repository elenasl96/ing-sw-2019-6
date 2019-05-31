package network.socket.commands;

import network.socket.commands.request.ShootRequest;
import network.socket.commands.response.*;

/**
 * A method for every possible Response
 */
public interface ResponseHandler {
    void handle(TextResponse textResponse);

    void handle(JoinGroupResponse joinGroupResponse);

    void handle(UserCreatedResponse userCreatedResponse);

    void handle(SituationViewerResponse situationViewerResponse);

    void handle(SetCharacterResponse setCharacterResponse);

    void handle(MoveUpdateResponse moveUpdateResponse);

    void handle(AskInput askInput);
}
