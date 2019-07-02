package view.gui;

import javax.swing.*;
import java.awt.*;

public class KillshotTrackPanel extends JPanel {
    private Image img;
    private int nSkull;

    public KillshotTrackPanel(int nSkull) {
        this.nSkull = nSkull;
        this.img = (new ImageIcon(new ImageIcon(this.getClass().getResource("killshottrack" + this.nSkull +".png")).getImage()
                .getScaledInstance(300,100, Image.SCALE_SMOOTH))).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
