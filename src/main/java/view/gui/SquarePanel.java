package view.gui;

import model.field.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class SquarePanel extends JPanel {

    private Image img;
    private String coordinate;
    private String grabbable;
    private Timer timer;
    private JFrame grabbableFrame;

    public SquarePanel(String url, String coordinate) {
        img = (new ImageIcon(url).getImage());
        this.coordinate = coordinate;
    }

    public SquarePanel(ImageIcon img, String coordinate) {
        this.img = img.getImage();
        timer = null;
        this.coordinate = coordinate;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                timer = new Timer();

                if(grabbable!=null){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        grabbableFrame = new JFrame();
                        grabbableFrame.setTitle("Grabbable");
                        grabbableFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        grabbableFrame.setLayout(new BorderLayout());
                        grabbableFrame.add(new JLabel("In this square"),BorderLayout.NORTH);

                        if(coordinate.split(" ")[0].toUpperCase().equals("C") ||
                                coordinate.split(" ")[0].toUpperCase().equals("D")) {
                            grabbableFrame.setLocation(100,150);
                        }
                        else {
                            grabbableFrame.setLocation(700,150);
                        }

                        JPanel cardPanel = new JPanel(new FlowLayout());

                        for(String s: grabbable.split(";")) {
                            WeaponCard wp = new WeaponCard(s,0);
                            cardPanel.add(wp);
                        }

                        grabbableFrame.add(cardPanel,BorderLayout.CENTER);
                        grabbableFrame.pack();
                        grabbableFrame.setResizable(false);
                        grabbableFrame.toFront();
                        grabbableFrame.setVisible(true);
                    }
                    },1500);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(grabbableFrame == null) {
                    timer.cancel();
                }
                else
                {
                    grabbableFrame.setVisible(false);
                    grabbableFrame.dispose();
                    grabbableFrame = null;
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getGrabbable() {
        return grabbable;
    }

    public void setGrabbable(String g) {
        grabbable = g;
    }
}
