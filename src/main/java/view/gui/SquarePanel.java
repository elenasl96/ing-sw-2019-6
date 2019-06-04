package view.gui;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {

    private Image img;

    public SquarePanel(String url) {
        img = (new ImageIcon(url).getImage());
    }

    public SquarePanel(ImageIcon img) {
        this.img = img.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
