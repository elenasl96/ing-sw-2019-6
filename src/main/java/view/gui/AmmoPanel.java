package view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * LeftPanel indicating the ammos of a single color possessed by the current player
 */
public class AmmoPanel extends JPanel {

    private ImageIcon img;

    AmmoPanel (int nAmmo, ImageIcon img){
        super(new FlowLayout());
        this.img = img;
        setAmmo(nAmmo);
    }

    /**
     * @param n adds n Ammo to the player
     */
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
