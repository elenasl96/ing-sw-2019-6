package view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * A class for the weapons and powerups cards
 * reads them from the resources
 */
class WeaponCard extends JPanel {

    private int num;

    WeaponCard(String name, int num) {
        add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getResource("Weapons/" + name + ".png")).getImage()
            .getScaledInstance(110,190, Image.SCALE_SMOOTH))));
        this.num = num;
    }

    WeaponCard(int num) {
        this.num = num;
        setBackground(new Color(0,0,0,0));
    }

    int getNum() {
        return num;
    }
}
