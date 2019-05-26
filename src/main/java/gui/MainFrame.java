package gui;

import model.room.ModelObserver;
import model.room.Update;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
        JPanel left = new JPanel(new GridLayout(3,1));
        JPanel right = new JPanel(new GridLayout(3,1));
        JPanel playerboard = new JPanel();

        add(field,BorderLayout.CENTER);
        add(left,BorderLayout.WEST);
        add(right,BorderLayout.EAST);
        add(playerboard,BorderLayout.SOUTH);
        
        // https://stackoverflow.com/questions/22982295/what-does-pack-do
        setVisible(true);
    }

}
