package view.gui;

import javax.swing.*;
import java.awt.*;

public class Character {

    private String coordinate;
    private JLabel icon;

    public Character(JLabel img) {
        icon = img;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setIcon(JLabel icon) {
        this.icon = icon;
    }
}
