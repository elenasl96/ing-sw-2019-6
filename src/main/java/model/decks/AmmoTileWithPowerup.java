package model.decks;

import model.Board;
import model.GameContext;
import model.Player;
import model.enums.Color;


public class AmmoTileWithPowerup extends AmmoTile implements Grabbable{

    public AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void pickGrabbable(int groupID) {
        GameContext.get().getGame(groupID).getCurrentPlayer().fillAmmoFromTile(this);
        GameContext.get().getGame(groupID).getCurrentPlayer().getPowerups()
                .add(GameContext.get().getGame(groupID).getBoard().getPowerupsLeft().pickCard());
    }
}
