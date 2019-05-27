package gui;

import controller.ClientController;
import model.room.ModelObserver;
import model.room.Update;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import static model.enums.Character.PG1;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;
    private static final String PATH = "C:/User/quara/IdeaProjects/ing-sw-2019-6/src/main/resources";
    private ClientController controller;

    private JTextArea console;

    public MainFrame(ClientController controller)
    {
        this.controller = controller;
    }

    public void initGUI() {

        // Try to comment it out
        setTitle("Adrenalina");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel scrollPanelContainer = new JPanel(new GridLayout(1,2));

        JPanel weaponPan = new JPanel(new GridLayout(0,1));
        JPanel powerUpPan = new JPanel(new GridLayout(0,1));
        for (int i = 0; i < 10; i++) {
            weaponPan.add(new JButton("Hello-" + i));
            powerUpPan.add(new JButton("Hello-" + i));
        }

        JScrollPane weapons = new JScrollPane(weaponPan);
        JScrollPane powerUps = new JScrollPane(powerUpPan);

        scrollPanelContainer.add(weapons);
        scrollPanelContainer.add(powerUps);

        console = new JTextArea();
        console.setLineWrap(true);
        console.setEditable(false);
        JTextField commandLine = new JTextField(20);
        JButton ok = new JButton("OK");
        BufferedImage image = null;
        Image newImage = null;
        try{
            newImage = ImageIO.read(new File("C:\\Users\\quara\\IdeaProjects\\ing-sw-2019-6\\src\\main\\resources\\KillshotTrack.png"))
                    .getScaledInstance(200, 40, Image.SCALE_DEFAULT);
            System.out.println(PATH);
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }

        JPanel centralPanel = new JPanel(new GridLayout(2,1));
        JPanel field = new JPanel(new GridLayout(3,4));
        centralPanel.add(new JLabel(new ImageIcon(newImage)));
        JPanel left = new JPanel(new GridLayout(4,1));
        JPanel right = new JPanel(new GridLayout(3,1));
        JPanel playerboard = new JPanel();
        JPanel commandLineBar = new JPanel(new FlowLayout());
        commandLineBar.add(commandLine);
        commandLineBar.add(ok);
        right.add(new JLabel("MOSSE"));
        right.add(commandLineBar);
        right.add(new JScrollPane(console));
        playerboard.add(new JLabel("PLAYER BOARD"));
        JLabel name = new JLabel("NOME GIOCATORE");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel ammos = new JPanel();
        ammos.add(new JLabel("AMMO"));
        JLabel cardlabel = new JLabel("CARTE IN POSSESSO");
        cardlabel.setHorizontalAlignment(SwingConstants.CENTER);

        left.add(name);
        left.add(ammos);
        left.add(cardlabel);
        left.add(scrollPanelContainer);

        add(centralPanel,BorderLayout.CENTER);
        add(left,BorderLayout.WEST);
        add(right,BorderLayout.EAST);
        add(playerboard,BorderLayout.SOUTH);

        setSize(1000,600);
        setResizable(false);

        // https://stackoverflow.com/questions/22982295/what-does-pack-do
        setVisible(true);
    }

    public void setConsole(String message)
    {
        console.append(message+"\n");
    }
}
