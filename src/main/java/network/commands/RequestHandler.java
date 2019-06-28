package network.commands;

import network.commands.request.*;

/**
 * A method for every possible Request
 * @see Request
 * @see controller.ServerController#handle
 * @see controller.ServerController
 */
public interface RequestHandler {

    Response handle(CreateUserRequest request);

    Response handle(ChooseGroupRequest chooseGroupRequest);

    Response handle(SituationViewerRequest situationViewerRequest);

    Response handle(CreateGroupRequest createGroupRequest);

    Response handle(SetCharacterRequest setCharacterRequest);

    Response handle(PossibleMovesRequest possibleMovesRequest);

    Response handle(MoveRequest moveRequest);

    Response handle(SpawnRequest spawnRequest);

    Response handle(CardRequest cardRequest);

    Response handle(SendInput sendInput);

    Response handle(ShootRequest shootRequest);

    Response handle(AskWeaponsOnTileRequest askWeaponsOnTileRequest);
}
