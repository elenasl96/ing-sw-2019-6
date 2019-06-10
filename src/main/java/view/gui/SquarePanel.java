package view.gui;

import model.field.Coordinate;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {

    private Image img;
    private String coordinate;

    public SquarePanel(String url, String coordinate) {
        img = (new ImageIcon(url).getImage());
        this.coordinate = coordinate;
    }

    public SquarePanel(ImageIcon img, String coordinate) {
        this.img = img.getImage();
        this.coordinate = coordinate;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public String getCoordinate() {
        return coordinate;
    }
}
