package socket.network.commands;

import socket.network.commands.request.*;

/**
 * A method for every possible Request
 */
public interface RequestHandler {
    Response handle(SendMessageRequest request);

    Response handle(CreateUserRequest request);

    Response handle(ChooseGroupRequest chooseGroupRequest);

    Response handle(SituationViewerRequest situationViewerRequest);

    Response handle(CreateGroupRequest createGroupRequest);

    Response handle(SendCommandRequest commandRequest);
}
