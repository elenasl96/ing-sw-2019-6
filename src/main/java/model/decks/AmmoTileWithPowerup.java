package model.decks;

import model.Player;
import model.enums.Color;


public class AmmoTileWithPowerup extends AmmoTile implements Grabbable{

    public AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void useGrabbable(Player player) {
        player.fillAmmoFromTile(this);

    }
}
