package view.gui;

import controller.ClientController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;
    private ClientController controller;
    private JTextArea console;
    private JTextField commandLine;
    private JButton ok;
    private Object lockInput;
    private Object lockMove;
    private MoveButtonActionListener actionListener;
    private JPanel turnLight;
    JComboBox weapon;
    JComboBox powerUp;


    public MainFrame(ClientController controller) {
        this.controller = controller;
        lockInput = new Object();
        lockMove = new Object();
        actionListener = new MoveButtonActionListener(lockMove);
    }

    public void initGUI() {

        // Try to comment it out
        setTitle("Adrenalina");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Create left section of GUI
        JPanel cardsContainer = new JPanel(new GridLayout(1, 2));

        weapon = new JComboBox();
        powerUp = new JComboBox();
        for (int i = 0; i < 10; i++) {
            weapon.addItem(new String("Africa" + i));
            powerUp.addItem(new String("nostra" + i));
        }

        cardsContainer.add(weapon);
        cardsContainer.add(powerUp);

        JLabel name = new JLabel("NOME GIOCATORE");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel ammos = new JPanel();
        ammos.add(new JLabel("AMMO"));
        JLabel cardLabel = new JLabel("CARTE IN POSSESSO");
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel left = new JPanel(new GridLayout(4, 1));
        left.add(name);
        left.add(ammos);
        left.add(cardLabel);
        left.add(cardsContainer);

        //Create right section of GUI
        console = new JTextArea();
        console.setLineWrap(true);
        console.setEditable(false);
        commandLine = new JTextField(20);
        ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lockInput) {
                    lockInput.notifyAll();
                }
            }
        });

        turnLight = new JPanel();
        turnLight.setBackground(Color.RED);

        JPanel commandLineBar = new JPanel(new FlowLayout());
        commandLineBar.add(commandLine);
        commandLineBar.add(ok);

        MoveButton grab = new MoveButton("Grab", "grab");
        MoveButton run = new MoveButton("Run", "run");
        MoveButton shoot = new MoveButton("Shoot", "shoot");
        MoveButton powerup = new MoveButton("Power Up", "powerup");
        grab.addActionListener(actionListener);
        run.addActionListener(actionListener);
        shoot.addActionListener(actionListener);

        JPanel buttonContainer = new JPanel(new GridLayout(5, 1));
        buttonContainer.add(new JLabel("Actions"));
        buttonContainer.add(run);
        buttonContainer.add(grab);
        buttonContainer.add(shoot);
        buttonContainer.add(powerup);

        JPanel middleRightContainer = new JPanel(new GridLayout(5, 1));
        middleRightContainer.add(new JLabel("Your turn"));
        middleRightContainer.add(turnLight);
        middleRightContainer.add(new JLabel("Insertion bar"));
        middleRightContainer.add(commandLineBar);
        middleRightContainer.add(new JLabel("Updates"));

        JPanel right = new JPanel(new GridLayout(3, 1));
        right.add(buttonContainer);
        right.add(middleRightContainer);
        right.add(new JScrollPane(console));


        //Create central section of GUI
        JPanel centralPanel = new JPanel(new GridLayout(3, 4));

        printField(centralPanel);

        //Create bottom section of GUI
        JPanel playerboard = new JPanel();
        playerboard.add(new JLabel("PLAYER BOARD"));


        add(centralPanel, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(playerboard, BorderLayout.SOUTH);

        setSize(1000, 485);
        setResizable(false);

        // https://stackoverflow.com/questions/22982295/what-does-pack-do
        setVisible(true);
    }

    public void setConsole(String message) {
        console.append(message + "\n");
    }

    public String getJLabelText() {
        String string;
        synchronized (lockInput) {
            try {
                lockInput.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            string = commandLine.getText();
            commandLine.setText("");
        }
        return string;
    }

    public String getMove() {
        synchronized (lockMove) {
            try {
                lockMove.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            return actionListener.getS();
        }
    }

    public void toggleBackGroundTurn() {
        if (turnLight.getBackground().equals(Color.RED)) {
            turnLight.setBackground(Color.GREEN);
        } else {
            turnLight.setBackground(Color.RED);
        }
    }

    public void createMap() {

    }
    private void printField(JPanel centralPanel){
        try{
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_001.png"))
                    .getScaledInstance(140,140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_002.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_003.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_004.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            //////End of first row
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_005.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_006.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_007.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_008.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_009.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_010.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_011.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "image_part_012.png"))
                    .getScaledInstance(140, 140, Image.SCALE_SMOOTH))));
        }
        catch(IOException exception)
        {
            System.out.println("Error");
        }
    }
}