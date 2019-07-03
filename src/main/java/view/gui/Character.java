package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Character {

    private String coordinate;
    private CharacterLabel icon;
    private String name;

    public Character(CharacterLabel img) {
        icon = img;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    JLabel getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        icon.setPlayer(name);
    }
}
