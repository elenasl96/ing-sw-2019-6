package view.gui;

import javax.swing.*;
import java.awt.*;

class PopUpCards extends JFrame {

    private transient ChooseCardActionListener actionListenerClick;

    PopUpCards(String[] cardNames, Object lock) {
        actionListenerClick = new ChooseCardActionListener(lock);

        setTitle("Cards");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new JLabel("Choose a card"),BorderLayout.NORTH);
        JPanel cardPanel = new JPanel(new FlowLayout());
        setLocation(300,300);

        int cont = 0;
        for(String s: cardNames) {
            System.out.println(s);
            WeaponCard wp = new WeaponCard(s,cont);
            wp.addMouseListener(actionListenerClick);
            cardPanel.add(wp);
            cont++;
        }

        add(cardPanel,BorderLayout.CENTER);
        pack();
        setResizable(false);
        toFront();
        setVisible(true);
    }

    String close() {
        setVisible(false);
        dispose();
        return actionListenerClick.getS();
    }
}
