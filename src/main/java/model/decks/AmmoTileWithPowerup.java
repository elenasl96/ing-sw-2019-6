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
    public void pickGrabbable(Player player, Board board) {
        player.fillAmmoFromTile(this);
        player.getPowerups().add(board.getPowerupsLeft().pickCard());

    }
}
