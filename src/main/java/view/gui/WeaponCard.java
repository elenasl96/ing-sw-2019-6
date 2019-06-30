package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A class for the weapons and powerups cards
 * reads them from the resources
 */
class WeaponCard extends JPanel {

    private int num;

    WeaponCard(String name, int num) {
        try {
            add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/Weapons/" + name + ".png"))
                    .getScaledInstance(110,190, Image.SCALE_SMOOTH))));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

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
