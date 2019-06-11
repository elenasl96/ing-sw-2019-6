package view.gui;

import javax.swing.*;
import java.awt.*;

public class PopUpCards extends JFrame {

    public PopUpCards() {
        setTitle("Cards");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JLabel("Choose a card"),BorderLayout.NORTH);
        JPanel cardPanel = new JPanel(new FlowLayout());
        setSize(400,200);
        setLocation(300,300);


    }
}
