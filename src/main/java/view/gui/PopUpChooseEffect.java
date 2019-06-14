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

public class PopUpChooseEffect extends JPanel {

    private String effectSerie;
    private Image img;
    private WeaponCard[] effect;

    public PopUpChooseEffect(String weapon, int layout, Object lock) {

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
                                 @Override
                                 public void actionPerformed(ActionEvent e) {
                                     synchronized (lock) {
                                         lock.notifyAll();
                                     }
                                 }
                             }
        );

        try {
            this.img = new ImageIcon(ImageIO.read(new File("src/resources/Weapons/" +
                    weapon +".png"))
                    .getScaledInstance(150,300, Image.SCALE_SMOOTH)).getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }

        switch(layout) {
            case 1: {
                setLayout(new BorderLayout(2,1));
                add(new JPanel());
                add(effect[0]);
                break;
            }
            case 2: {
                setLayout(new BorderLayout(3,1));
                add(new JPanel());
                add(effect[0]);
                add(effect[1]);
                break;
            }
            case 3: {
                setLayout(new BorderLayout(3,1));
                add(new JPanel());
                add(effect[0]);
                JPanel lower = new JPanel(new BorderLayout(1,2));
                lower.add(effect[1]);
                lower.add(effect[2]);
                break;
            }

        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public String getEffectSerie() {
        if(effectSerie != "") {
            return effectSerie.substring(0,effectSerie.length()-1);
        }
        return "";
    }
}
