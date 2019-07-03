package network.commands;

import network.commands.response.*;

/**
 * A method for every possible Response
 * @see controller.ClientController#handle;
 * @see controller.ClientController
 * @see Response
 */
public interface ResponseHandler {
    void handle(TextResponse textResponse);

    void handle(JoinGroupResponse joinGroupResponse);

    void handle(UserCreatedResponse userCreatedResponse);

    void handle(SituationViewerResponse situationViewerResponse);

    void handle(SetCharacterResponse setCharacterResponse);

    void handle(MoveUpdateResponse moveUpdateResponse);

    void handle(AskInput askInput);

    void handle(RejoiningResponse rejoiningResponse);

    void handle(EndGameNotification endGameNotification);
}
