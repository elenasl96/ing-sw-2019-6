package view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerBoardPanel extends JPanel {

    private Image img;
    private JPanel dropPanel;
    private JPanel markerPanel;
    private model.enums.Color[] serie;
    private int sizeSerie;
    private final int numDrops = 12;
    private int numMarker = 0;

    public PlayerBoardPanel() {
        try {
            this.img = (new ImageIcon(ImageIO.read(new File("src/resources/playerboard.png"))
                    .getScaledInstance(700,130, Image.SCALE_SMOOTH))).getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(700,130);
        setPreferredSize(new Dimension(700,130));
        setMaximumSize(new Dimension(700,130));
        setMinimumSize(new Dimension(700,130));

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
        dropPanel.setLayout(new GridLayout(1,numDrops));
        dropPanel.setBackground(new Color(0,0,0,0));

        add(dropPanel,1);
        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        add(voidPanel,2);

        sizeSerie = 0;
        serie = new model.enums.Color[12];

    }

    public void addMarker() {
        JPanel voidPanel;

        markerPanel.removeAll();
        numMarker++;

        for(int i=0;i<numMarker;i++) {
            try {
                markerPanel.add(new JLabel((new ImageIcon(ImageIO.read(new File("src/resources/badoglio.jpg"))
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i=numMarker;i<12;i++) {
            voidPanel = new JPanel();
            voidPanel.setBackground(new Color(0,0,0,0));
            markerPanel.add(voidPanel);
        }

        markerPanel.revalidate();
        markerPanel.repaint();
    }

    public void addDrop(model.enums.Color color) {

        dropPanel.removeAll();

        serie[sizeSerie] = color;
        sizeSerie++;
        JPanel voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);
        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        int  i=0;


        for(i=0;i<(sizeSerie>2?2:sizeSerie);i++){
            try {
                dropPanel.add(new JLabel((new ImageIcon(ImageIO.read(new File("src/resources/veiii.png"))
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(i=2;i<(sizeSerie>5?5:sizeSerie);i++) {
            try {
                dropPanel.add(new JLabel((new ImageIcon(ImageIO.read(new File("src/resources/veiii.png"))
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(i=5;i<(sizeSerie>10?10:sizeSerie);i++) {
            try {
                dropPanel.add(new JLabel((new ImageIcon(ImageIO.read(new File("src/resources/veiii.png"))
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        voidPanel = new JPanel();
        voidPanel.setBackground(new Color(0,0,0,0));
        dropPanel.add(voidPanel);

        for(i=10;i<(sizeSerie);i++) {
            try {
                dropPanel.add(new JLabel((new ImageIcon(ImageIO.read(new File("src/resources/veiii.png"))
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(i=sizeSerie;i<numDrops;i++) {
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
