package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WeaponCard extends JPanel {

    private int num;

    public WeaponCard(String name, int num) {
        try {
            add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/Weapons/" +
                    /*name */ "mp40" + ".png"))
                    .getScaledInstance(140,70, Image.SCALE_SMOOTH))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
