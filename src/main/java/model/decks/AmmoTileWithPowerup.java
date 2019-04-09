package model.decks;

import model.Player;
import model.enums.Color;

import java.util.List;

public class AmmoTileWithPowerup extends AmmoTile{

    private Powerup powerup;

    public AmmoTileWithPowerup(Color color1, Color color2, Color color3, Powerup powerup){
        super(color1, color2, color3);
        this.powerup=powerup;
    }

    @Override
    public void useGrabbable(Player player) {
        //TODO
    }
}
