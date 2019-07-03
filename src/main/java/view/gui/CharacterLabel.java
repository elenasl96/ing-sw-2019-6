package view.gui;

import javax.swing.*;

public class CharacterLabel extends JLabel {

    private String player;

    CharacterLabel(ImageIcon img){
        super(img);
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
