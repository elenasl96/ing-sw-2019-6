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
    private final int DIMAMMOIMAGE = 25;
    private final int WIDTHPAWN = 70;
    private final int HEIGHTPAWN = 60;

    private AmmoPanel ammoRed;
    private AmmoPanel ammoBlue;
    private AmmoPanel ammoYellow;

    private volatile JTextArea console;
    private JTextField commandLine;
    private JButton ok;
    private MoveButton grab;
    private MoveButton run;
    private MoveButton shoot;
    private MoveButton powerup;

    private Object lockInput;
    private Object lockMove;
    private Object lockCoordinate;

    private MoveButtonActionListener actionListenerMovement;
    private CoordinateActionListener actionListenerCoordinate;
    private JPanel turnLight;
    private JComboBox weapon;
    private JComboBox powerUp;

    private SquarePanel mapGrid[][];
    private Character charactersCoordinates[];

    public MainFrame(ClientController controller) {
        this.controller = controller;
        lockInput = new Object();
        lockMove = new Object();
        lockCoordinate = new Object();
        actionListenerMovement = new MoveButtonActionListener(lockMove);
        actionListenerCoordinate = new CoordinateActionListener(lockCoordinate);
        mapGrid = new SquarePanel[3][4];

        charactersCoordinates = new Character[5];
        initCharacters();

    }

    private void initCharacters() {
        for(int i=0;i<5;i++) {
            try {
                charactersCoordinates[i] = new Character(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/pedina" +
                        i+".jpg"))
                        .getScaledInstance(WIDTHPAWN, HEIGHTPAWN, Image.SCALE_SMOOTH))));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void initGUI() {
        System.out.println("Go");

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
        JPanel ammos = new JPanel(new GridLayout(4,1));
        try {
            ammoRed = new AmmoPanel(3, new ImageIcon(ImageIO.read(new File("src/resources/ammo1.png"))
                    .getScaledInstance(DIMAMMOIMAGE, DIMAMMOIMAGE, Image.SCALE_SMOOTH)));
            ammoBlue = new AmmoPanel(1,new ImageIcon(ImageIO.read(new File("src/resources/ammo2.png"))
                    .getScaledInstance(DIMAMMOIMAGE, DIMAMMOIMAGE, Image.SCALE_SMOOTH)));
            ammoYellow = new AmmoPanel(2,new ImageIcon(ImageIO.read(new File("src/resources/ammo3.png"))
                    .getScaledInstance(DIMAMMOIMAGE, DIMAMMOIMAGE, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ammos.add(new JLabel("AMMO"));
        ammos.add(ammoRed);
        ammos.add(ammoBlue);
        ammos.add(ammoYellow);
        JLabel cardLabel = new JLabel("CARTE IN POSSESSO");
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel left = new JPanel(new GridLayout(4, 1));
        left.add(name);
        left.add(ammos);
        left.add(cardLabel);
        left.add(cardsContainer);

        //Create right section of GUI
        console = new JTextArea("");
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

        grab = new MoveButton("Grab", "grab");
        run = new MoveButton("Run", "run");
        shoot = new MoveButton("Shoot", "0");
        powerup = new MoveButton("Power Up", "0");
        grab.addActionListener(actionListenerMovement);
        run.addActionListener(actionListenerMovement);
        shoot.addActionListener(actionListenerMovement);
        powerup.addActionListener(actionListenerMovement);
        grab.setEnabled(false);
        run.setEnabled(false);
        shoot.setEnabled(false);
        powerup.setEnabled(false);

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
        JPanel playerBoard = new JPanel();
        playerBoard.add(new JLabel("PLAYER BOARD"));


        add(centralPanel, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(playerBoard, BorderLayout.SOUTH);

        setSize(1000, 485);
        setResizable(false);

        weapon.addActionListener(e -> shoot.setMove(weapon.getSelectedIndex()+""));
        powerup.addActionListener(e -> powerup.setMove((powerUp.getSelectedIndex()+3)+""));
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
                System.out.println(e.getMessage());
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
                System.err.println(e.getMessage());
            }

            return actionListenerMovement.getS();
        }
    }

    public String getCoordinate() {
        synchronized (lockCoordinate) {
            try {
                lockCoordinate.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }

            return actionListenerCoordinate.getS();
        }
    }

    public void setBackGroundTurn(boolean val) {
        if (val) {
            turnLight.setBackground(Color.GREEN);
        } else {
            turnLight.setBackground(Color.RED);
        }
    }

    private void printField(JPanel centralPanel){
        try{
            Integer cont = 1;

            for(int i=0;i<3;i++) {

                for(int j=0;j<4;j++) {

                    mapGrid[i][j]=new SquarePanel(new ImageIcon(ImageIO.read(new File("src/resources/HandMade/" +
                            "image_part_0"+String.format("%02d",cont) +".png"))
                            .getScaledInstance(140,140, Image.SCALE_SMOOTH)), (char)(j+65)+" "+(3-i));
                    mapGrid[i][j].setLayout(new GridLayout(3,2));
                    mapGrid[i][j].addMouseListener(actionListenerCoordinate);

                    centralPanel.add(mapGrid[i][j]);

                    cont++;
                }
            }
        }
        catch(IOException exception)
        {
            System.out.println("Error");
        }
    }

    public void updateMap(int character, String coordinates) {
        String[] newCoord = coordinates.split(" ");

        if(charactersCoordinates[character].getCoordinate()!=null) {
            String[] oldCoord = charactersCoordinates[character].getCoordinate().split(" ");
            mapGrid[3-Integer.parseInt(oldCoord[1])][oldCoord[0].charAt(0) - 65]
                    .remove(charactersCoordinates[character].getIcon());
            mapGrid[3-Integer.parseInt(oldCoord[1])][oldCoord[0].charAt(0) - 65]
                    .revalidate();
        }

        mapGrid[3-Integer.parseInt(newCoord[1])][newCoord[0].charAt(0) - 65]
                .add(charactersCoordinates[character].getIcon());
        mapGrid[3-Integer.parseInt(newCoord[1])][newCoord[0].charAt(0) - 65].revalidate();

        charactersCoordinates[character].setCoordinate(coordinates);
    }

    public void changeAmmoPanel(String s) {
        switch(s)
        {
            case "red": ammoRed.addAmmo(); break;
            case "blue": ammoBlue.addAmmo(); break;
            case "yellow": ammoYellow.addAmmo(); break;
        }
    }

    public void clearAmmoPanels() {
        ammoYellow.removeAll();
        ammoBlue.removeAll();
        ammoRed.removeAll();
    }

    public SquarePanel[][] getMapGrid() {
        return mapGrid;
    }

    public void clearWeaponBox() {
        weapon.removeAllItems();
    }

    public void clearPuBox() {
        powerUp.removeAllItems();
    }

    public void addWeaponBox(String s) {
        weapon.addItem(s);
    }

    public void addPuBox(String s) {
        powerUp.addItem(s);
    }

    public void disableButtons(String[] data) {

        grab.setEnabled(false);
        run.setEnabled(false);
        shoot.setEnabled(false);
        powerup.setEnabled(false);

        for(String s: data) {
            switch(s) {
                case "RUN": run.setEnabled(true); break;
                case "GRAB": grab.setEnabled(true); break;
                case "SHOOT": shoot.setEnabled(true); break;
                case "POWERUPS": powerup.setEnabled(true); break;
                default: break;
            }
        }
    }
}