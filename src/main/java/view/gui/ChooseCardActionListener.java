package view.gui;

import javax.sound.sampled.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class ChooseCardActionListener implements MouseListener {
    private final Object lock;
    private String s;

    public ChooseCardActionListener(Object lock) {
        this.lock = lock;
        s = new String("");
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
            s = ((WeaponCard) e.getSource()).getNum()+"";
            lock.notifyAll();
        }
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
}
