package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PopUpCards extends JFrame {

    private ChooseCardActionListener actionListenerClick;
    private Object lock;

    public PopUpCards(String[] cardNames, Object lock) {
        this.lock = lock;
        actionListenerClick = new ChooseCardActionListener(lock);

        setTitle("Cards");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JLabel("Choose a card"),BorderLayout.NORTH);
        JPanel cardPanel = new JPanel(new FlowLayout());
        setSize(500,300);
        setLocation(300,300);

        int cont = 0;
        for(String s: cardNames) {
            System.out.println(s);
            WeaponCard wp = new WeaponCard(s,cont);
            wp.addMouseListener(actionListenerClick);
            cardPanel.add(wp);
            cont++;
        }

        add(cardPanel);
        setVisible(true);
    }

    public String close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        return actionListenerClick.getS();
    }
}
