package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerBoardPanel extends JPanel {

    private Image img;

    public PlayerBoardPanel() {
        try {
            this.img = (new ImageIcon(ImageIO.read(new File("src/resources/playerboard.png"))
                    .getScaledInstance(700,130, Image.SCALE_SMOOTH))).getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(700,130);
        setPreferredSize(new Dimension(700,130));
        setMaximumSize(new Dimension(700,130));
        setMinimumSize(new Dimension(700,130));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
