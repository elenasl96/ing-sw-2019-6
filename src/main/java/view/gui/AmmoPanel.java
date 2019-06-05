package view.gui;

import javax.swing.*;
import java.awt.*;

public class AmmoPanel extends JPanel {

    ImageIcon img;

    public AmmoPanel (int nAmmo, ImageIcon img){
        super(new FlowLayout());
        this.img = img;
        setAmmo(nAmmo);
    }

    public void setAmmo(int n) {
        removeAll();

        for(int i=0;i<n;i++) {
            add(new JLabel(img));
        }

        repaint();
    }
}
