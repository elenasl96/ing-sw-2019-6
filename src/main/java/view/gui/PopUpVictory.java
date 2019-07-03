package view.gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PopUpVictory extends JFrame {

    public PopUpVictory() {

        setTitle("Victory");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(300,50);
        setSize(480,400);
        playMusic("vittoria.wav");
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Congratulations, you win!");
        title.setFont(new Font(title.getName(), Font.PLAIN, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel flag = new JLabel(new ImageIcon(new ImageIcon(
                this.getClass().getResource("tricolore.gif"))
                .getImage().getScaledInstance(420, 320, Image.SCALE_DEFAULT)));
        add(title,BorderLayout.NORTH);
        add(flag, BorderLayout.CENTER);
        setResizable(false);
        toFront();
        setVisible(true);
    }

    public void playMusic(String string){
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            stream = AudioSystem.getAudioInputStream(this.getClass().getResource(string));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
