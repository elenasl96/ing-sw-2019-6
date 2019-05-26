package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanelLeft1 extends JPanel implements Panel{

    private BufferedImage image;

    public BoardPanelLeft1() {
        try {
            image = ImageIO.read(new File("./src/main/resources/Field_1_left"));
        } catch (IOException ex) {
            // handle exception
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
