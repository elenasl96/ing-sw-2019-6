package gui;

import model.room.ModelObserver;
import model.room.Update;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.*;

import static model.enums.Character.PG1;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;

    public void initGUI() {

        // Try to comment it out
        setTitle("Adrenalina");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setLayout(new BorderLayout());

        JPanel field = new JPanel(new GridLayout(5,5));
        field.setSize(300,350);
        field.add(new JLabel("CAMPO Di GIUGO"));
        JPanel left = new JPanel(new GridLayout(4,1));
        JPanel right = new JPanel(new GridLayout(3,1));
        JPanel playerboard = new JPanel();

        add(field,BorderLayout.CENTER);
        add(left,BorderLayout.WEST);
        add(right,BorderLayout.EAST);
        add(playerboard,BorderLayout.SOUTH);

        JLabel name = new JLabel("NOME GIOCATORE");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel ammos = new JPanel();
        JLabel cardlabel = new JLabel("CARTE IN POSSESSO");
        cardlabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel scrollPanelContainer = new JPanel(new GridLayout(1,2));
        scrollPanelContainer.setSize(100,40);

        left.add(name);
        left.add(ammos);
        ammos.add(new JLabel("AMMO"));
        left.add(cardlabel);
        left.add(scrollPanelContainer);

        JPanel weaponPan = new JPanel();
        weaponPan.setSize(50,40);
        for (int i = 0; i < 10; i++) {
            weaponPan.add(new JButton("Hello-" + i));
        }
        JPanel powerUpPan = new JPanel();
        powerUpPan.setSize(50,40);
        JScrollPane weapons = new JScrollPane(weaponPan);
        JScrollPane powerUps = new JScrollPane(powerUpPan);
        weapons.createHorizontalScrollBar();
        powerUps.createHorizontalScrollBar();

        scrollPanelContainer.add(weapons);
        scrollPanelContainer.add(powerUps);


        // https://stackoverflow.com/questions/22982295/what-does-pack-do
        setVisible(true);
    }

}
