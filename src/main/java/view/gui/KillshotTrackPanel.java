package view.gui;

import javax.swing.*;
import java.awt.*;

public class KillshotTrackPanel extends JPanel {
    private Image img;
    private int nSkull;
    private int count;

    public KillshotTrackPanel(int nSkull) {
        this.nSkull = nSkull;
        this.img = (new ImageIcon(new ImageIcon(this.getClass().getResource("killshottrack" + this.nSkull +".png")).getImage()
                .getScaledInstance(270,80, Image.SCALE_SMOOTH))).getImage();
        setLayout(new GridLayout(1,9));

        count = 0;
    }

    public void addSkull() {
        removeAll();
        count++;
        int i=0;
        for(i=0;i<count;i++) {
            add(new JLabel((new ImageIcon(new ImageIcon(this.getClass().getResource("xmas.jpg")).getImage()
                    .getScaledInstance(30,30, Image.SCALE_SMOOTH)))));
        }
        for(;i<9;i++)
        {
            JPanel voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            add(voidPanel);
        }
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }


}