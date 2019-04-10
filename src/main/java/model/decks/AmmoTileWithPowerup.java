package model.decks;

import model.Player;
import model.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class AmmoTileWithPowerup extends AmmoTile implements Grabbable{
    List<Powerup> powerupsLeft = new ArrayList<>();

    public AmmoTileWithPowerup(Color color1, Color color2){
        super(color1, color2);
    }

    @Override
    public void useGrabbable(Player player) {
        player.refillAmmo(this);

    }
}
