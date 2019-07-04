package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerBoardPanel extends JPanel {

    private transient Image img;
    private JPanel dropPanel;
    private JPanel markerPanel;
    private JPanel bottomPanel;
    private int[] serieDamage;
    private int[] serieMarker;
    private int sizeSerie;
    private static final int NUMBER_DROPS = 12;
    private int numMarker = 0;
    private int numSkull = 0;

    PlayerBoardPanel() {
        this.img = (new ImageIcon(new ImageIcon(this.getClass().getResource("playerBoard.png")).getImage()
            .getScaledInstance(540,130, Image.SCALE_SMOOTH))).getImage();
        setSize(540,130);
        setPreferredSize(new Dimension(540,130));
        setMaximumSize(new Dimension(540,130));
        setMinimumSize(new Dimension(540,130));

        setLayout(new GridLayout(3,1));
        JPanel topPanel = new JPanel(new GridLayout(1,3));
        topPanel.setBackground(new Color(0,0,0,0));
        JPanel voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        topPanel.add(voidPanel);
        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        topPanel.add(voidPanel);

        markerPanel = new JPanel(new GridLayout(1,12));
        markerPanel.setBackground(new Color(0,0,0,0));
        topPanel.add(markerPanel);

        add(topPanel,0);

        dropPanel = new JPanel();
        dropPanel.setLayout(new GridLayout(1, NUMBER_DROPS));
        dropPanel.setBackground(new Color(0,0,0,0));

        add(dropPanel,1);
        bottomPanel = new JPanel(new GridLayout(1,11));
        bottomPanel.setBackground(new Color(0,0,0,0));
        add(bottomPanel,2);

        sizeSerie = 0;
        serieDamage = new int[12];
        serieMarker = new int[12];

    }

    void addSkull() {
        JPanel voidPanel;

        bottomPanel.removeAll();
        numSkull++;

        for(int i=0;i<4;i++) {
            voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            bottomPanel.add(voidPanel);
        }

        for(int i=4;i<numSkull+4;i++) {
            try {
                bottomPanel.add(new JLabel(
                        new ImageIcon(ImageIO.read(new File("src/resources/xmas.jpg"))
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH))));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        for(int i=numSkull;i<11;i++) {
            voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            bottomPanel.add(voidPanel);
        }

        markerPanel.revalidate();
        markerPanel.repaint();
    }

    void addMarker(int num) {
        JPanel voidPanel;

        markerPanel.removeAll();
        serieMarker[numMarker] = num;
        numMarker++;

        for(int i=0;i<numMarker;i++) {
            markerPanel.add(new JLabel(new ImageIcon(new ImageIcon(
                    this.getClass().getResource("marker" + serieMarker[i] + ".PNG"))
                    .getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH))));
        }

        for(int i=numMarker;i<12;i++) {
            voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            markerPanel.add(voidPanel);
        }

        markerPanel.revalidate();
        markerPanel.repaint();
    }

    void addDrop(int num) {

        dropPanel.removeAll();

        serieDamage[sizeSerie] = num;
        sizeSerie++;
        JPanel voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);
        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(int i = 0;i<(sizeSerie>2?2:sizeSerie);i++){
            dropPanel.add(new JLabel(new ImageIcon(new ImageIcon(
                this.getClass().getResource("drop" + serieDamage[i] +".PNG")).getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH))));
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(int i = 2;i<(sizeSerie>5?5:sizeSerie);i++) {
            dropPanel.add(new JLabel((new ImageIcon(new ImageIcon(this.getClass().getResource("drop" + serieDamage[i] +".png")).getImage()
                        .getScaledInstance(30, 30, Image.SCALE_SMOOTH)))));
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(int i = 5;i<(sizeSerie>10?10:sizeSerie);i++) {
            dropPanel.add(new JLabel((new ImageIcon(new ImageIcon(this.getClass().getResource("drop" + serieDamage[i] +".png")).getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH)))));
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(int i = 10;i<(sizeSerie);i++) {
            dropPanel.add(new JLabel((new ImageIcon(new ImageIcon(this.getClass().getResource("drop" + serieDamage[i] +".png")).getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH)))));
        }

        for(int i = sizeSerie; i< NUMBER_DROPS; i++) {
            voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            dropPanel.add(voidPanel);
        }

        dropPanel.revalidate();
        dropPanel.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
