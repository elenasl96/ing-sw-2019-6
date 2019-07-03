package view.gui;

import javax.swing.*;
import java.awt.*;

public class AmmoPanel extends JPanel {

    private ImageIcon img;

    AmmoPanel (int nAmmo, ImageIcon img){
        super(new FlowLayout());
        this.img = img;
        setAmmo(nAmmo);
    }

    public void setAmmo(int n) {
        removeAll();

        for(int i=0;i<n;i++) {
            addAmmo();
        }

        repaint();
    }

    void addAmmo() {
        add(new JLabel(img));
    }
}
