package network.socket.commands;

import network.socket.commands.request.*;

/**
 * A method for every possible Request
 */
public interface RequestHandler {

    Response handle(CreateUserRequest request);

    Response handle(ChooseGroupRequest chooseGroupRequest);

    Response handle(SituationViewerRequest situationViewerRequest);

    Response handle(CreateGroupRequest createGroupRequest);

    Response handle(SetCharacterRequest setCharacterRequest);

    Response handle(PossibleMovesRequest possibleMovesRequest);

    Response handle(MovementRequest movementRequest);

    Response handle(MoveRequest moveRequest);

    Response handle(SpawnRequest spawnRequest);

    Response handle(CardRequest cardRequest);

    Response handle(SendInput sendInput);

    Response handle(ShootRequest shootRequest);
}
