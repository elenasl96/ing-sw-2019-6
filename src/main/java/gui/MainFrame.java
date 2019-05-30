package gui;

import controller.ClientController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;
    private static final String PATH = "C:\\User\\quara\\IdeaProjects\\ing-sw-2019-6\\src\\main\\resources";
    private ClientController controller;
    private JTextArea console;
    private JTextField commandLine;
    private JButton ok;
    private Object lockInput;
    private Object lockMove;
    private MoveButtonActionListener actionListener;
    private JPanel turnLight;


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
        JPanel scrollPanelContainer = new JPanel(new GridLayout(1, 2));

        JPanel weaponPan = new JPanel(new GridLayout(0, 1));
        JPanel powerUpPan = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < 10; i++) {
            weaponPan.add(new JButton("Hello-" + i));
            powerUpPan.add(new JButton("Hello-" + i));
        }

        JScrollPane weapons = new JScrollPane(weaponPan);
        JScrollPane powerUps = new JScrollPane(powerUpPan);

        scrollPanelContainer.add(weapons);
        scrollPanelContainer.add(powerUps);

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
        left.add(scrollPanelContainer);

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

        JPanel turnPanel = new JPanel(new GridLayout(1, 2));
        turnPanel.add(new JLabel("Your turn"));
        turnLight = new JPanel();
        turnLight.setBackground(Color.RED);
        turnPanel.add(turnLight);

        JPanel commandLineBar = new JPanel(new FlowLayout());
        commandLineBar.add(commandLine);
        commandLineBar.add(ok);

        MoveButton grab = new MoveButton("Grab", "grab");
        MoveButton run = new MoveButton("Run", "run");
        MoveButton shoot = new MoveButton("Shoot", "shoot");
        grab.addActionListener(actionListener);
        run.addActionListener(actionListener);
        shoot.addActionListener(actionListener);

        JPanel buttonContainer = new JPanel(new GridLayout(4, 1));
        buttonContainer.add(new JLabel("Actions"));
        buttonContainer.add(run);
        buttonContainer.add(grab);
        buttonContainer.add(shoot);

        JPanel middleRightContainer = new JPanel(new GridLayout(5, 1));
        middleRightContainer.add(new JLabel(""));
        middleRightContainer.add(turnPanel);
        middleRightContainer.add(new JLabel("Insertion bar"));
        middleRightContainer.add(commandLineBar);
        middleRightContainer.add(new JLabel("Updates"));

        JPanel right = new JPanel(new GridLayout(3, 1));
        right.add(buttonContainer);
        right.add(middleRightContainer);
        right.add(new JScrollPane(console));


        //Create central section of GUI
        JPanel centralPanel = new JPanel(new GridLayout(3, 4));

        Image image = null;
        try{
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_A_3.png"))
                    .getScaledInstance(120,120, Image.SCALE_DEFAULT))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_B_3.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_C_3.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT))));
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_D_3_Fixed.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT))));
            //adds blue and yellow spawn point label

            //////End of first row
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT))));
            //adds A3
            centralPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT))));
            //adds A2
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
              centralPanel.add(new JLabel(new ImageIcon(image)));
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            centralPanel.add(new JLabel(new ImageIcon(image)));
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            centralPanel.add(new JLabel(new ImageIcon(image)));
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            centralPanel.add(new JLabel(new ImageIcon(image)));
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            centralPanel.add(new JLabel(new ImageIcon(image)));
            image = ImageIO.read(new File("src/resources/" +
                    "Field1_A_2.png"))
                    .getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            centralPanel.add(new JLabel(new ImageIcon(image)));



      }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }



        //Create bottom section of GUI
        JPanel playerboard = new JPanel();
        playerboard.add(new JLabel("PLAYER BOARD"));


        add(centralPanel, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(playerboard, BorderLayout.SOUTH);

        setSize(1000, 600);
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
}