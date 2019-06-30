package view.gui;

import javax.sound.sampled.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * Reacts when choosing a coordinate
 */
public class CoordinateActionListener implements MouseListener {
    private final Object lock;
    private String s;

    CoordinateActionListener(Object lock) {
        this.lock = lock;
    }

    public String getS() {
        return s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        synchronized (lock) {
            Clip clip;
            try {
                File yourFile = new File("src/resources/Music/Selezione.wav");
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                stream = AudioSystem.getAudioInputStream(yourFile);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                System.err.println(ex.getMessage());
            }
            s = ((SquarePanel) e.getSource()).getCoordinate();
            lock.notifyAll();
        }
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
}
