package socket.network.commands;

import socket.network.commands.response.JoinGroupResponse;
import socket.network.commands.response.TextResponse;
import socket.network.commands.response.UserCreatedResponse;

/**
 * A method for every possible Response
 */
public interface ResponseHandler {
    void handle(TextResponse textResponse);

    void handle(JoinGroupResponse joinGroupResponse);

    void handle(UserCreatedResponse userCreatedResponse);

    void handle(SituationViewerResponse situationViewerResponse);
}
