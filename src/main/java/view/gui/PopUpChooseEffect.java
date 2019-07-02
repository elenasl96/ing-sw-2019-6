package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * A PopUp that opens up when shooting with a weapon to chose the effect
 */
public class PopUpChooseEffect extends JPanel {

    private String effectSerie;
    private transient Image img;
    private WeaponCard[] effect;

    PopUpChooseEffect(String weapon, int layout) {

        this.img = (new ImageIcon(new ImageIcon(this.getClass().getResource("Weapons/" + weapon +".png")).getImage()
            .getScaledInstance(180,240, Image.SCALE_SMOOTH))).getImage();

        effectSerie = "";
        effect = new WeaponCard[3];

        for(int i=0;i<layout;i++) {
            effect[i] = new WeaponCard(i);
            effect[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    effectSerie = effectSerie + ((WeaponCard)e.getSource()).getNum() + " ";
                    ((WeaponCard)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
                    ((WeaponCard)e.getSource()).removeMouseListener(this);
                    ((WeaponCard)e.getSource()).revalidate();
                    ((WeaponCard)e.getSource()).repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //do nothing
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //do nothing
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //do nothing
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //do nothing
                }
            });
        }


        switch(layout) {
            case 1: {
                setLayout(new GridLayout(2,1));
                JPanel first = new JPanel();
                first.setBackground(new Color(0,0,0,0));
                add(first);
                add(effect[0]);
                break;
            }
            case 2: {
                setLayout(new GridLayout(3,1));
                JPanel first = new JPanel();
                first.setBackground(new Color(0,0,0,0));
                add(first);
                add(effect[0]);
                add(effect[1]);
                break;
            }
            case 3: {
                setLayout(new GridLayout(3,1));
                JPanel first = new JPanel();
                first.setBackground(new Color(0,0,0,0));
                add(first);
                add(effect[0]);
                JPanel lower = new JPanel(new GridLayout(1,2));
                lower.setBackground(new Color(0,0,0,0));
                lower.add(effect[1]);
                lower.add(effect[2]);
                add(lower);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

    String getEffectSerie() {
        if(!effectSerie.equals("")) {
            return effectSerie.substring(0,effectSerie.length()-1);
        }
        return "";
    }

    public void refresh() {
        for(WeaponCard w: effect) {
            w.revalidate();
            w.repaint();
        }
    }
}