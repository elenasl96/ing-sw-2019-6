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
    private static final int DIM_AMMO_IMAGE = 25;
    private static final int WIDTH_PAWN = 70;
    private static final int HEIGHT_PAWN = 60;

    private PopUpCards popUp;

    private AmmoPanel ammoRed;
    private AmmoPanel ammoBlue;
    private AmmoPanel ammoYellow;

    private JTextArea console;
    private JTextField commandLine;
    private JButton ok;
    private MoveButton grab;
    private MoveButton run;
    private MoveButton shoot;
    private MoveButton powerup;

    private Object lockInput;
    private Object lockMove;
    private Object lockCoordinate;
    private Object lockChooseCard;
    private Object lockReload;

    private MoveButtonActionListener actionListenerMovement;
    private CoordinateActionListener actionListenerCoordinate;
    private JPanel turnLight;
    private JComboBox weapon;
    private JComboBox powerUp;
    private String yesnochoice;
    private JFrame yesNoFrame;

    private SquarePanel mapGrid[][];
    private Character charactersCoordinates[];

    public MainFrame(ClientController controller) {
        this.controller = controller;
        lockInput = new Object();
        lockMove = new Object();
        lockCoordinate = new Object();
        lockChooseCard = new Object();
        lockReload = new Object();

        actionListenerMovement = new MoveButtonActionListener(lockMove);
        actionListenerCoordinate = new CoordinateActionListener(lockCoordinate);
        mapGrid = new SquarePanel[3][4];

        charactersCoordinates = new Character[5];
        initCharacters();

    }

    private void initCharacters() {
        for(int i=0;i<5;i++) {
            try {
                charactersCoordinates[i] = new Character(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/Pedine/pg" +
                        (i + 1)+".png"))
                        .getScaledInstance(WIDTH_PAWN, HEIGHT_PAWN, Image.SCALE_SMOOTH))));
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

        cardsContainer.add(weapon);
        cardsContainer.add(powerUp);

        JLabel name = new JLabel("NOME GIOCATORE");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel ammos = new JPanel(new GridLayout(4,1));
        try {
            ammoRed = new AmmoPanel(1, new ImageIcon(ImageIO.read(new File("src/resources/ammo1.png"))
                    .getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
            ammoBlue = new AmmoPanel(1,new ImageIcon(ImageIO.read(new File("src/resources/ammo2.png"))
                    .getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
            ammoYellow = new AmmoPanel(1,new ImageIcon(ImageIO.read(new File("src/resources/ammo3.png"))
                    .getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
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
        powerup = new MoveButton("Power Up", "3");
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

            switch(actionListenerMovement.getS()) {
                case "run": run.setBorder(BorderFactory.createLoweredBevelBorder()); break;
                case "grab": grab.setBorder(BorderFactory.createLoweredBevelBorder()); break;
                case "0": case "1": case "2": shoot.setBorder(BorderFactory.createLoweredBevelBorder()); break;
                case "3": case "4": case "5": powerup.setBorder(BorderFactory.createLoweredBevelBorder()); break;
                default: break;
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

        grab.setBorder(UIManager.getBorder("Button.border"));
        run.setBorder(UIManager.getBorder("Button.border"));
        shoot.setBorder(UIManager.getBorder("Button.border"));
        powerup.setBorder(UIManager.getBorder("Button.border"));

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

    public String cardChoose() {

        synchronized (lockChooseCard) {
            try {
                lockChooseCard.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }
        }

        return popUp.close();
    }

    public void createPopUp(String[] nameCard) {
        popUp = new PopUpCards(nameCard, lockChooseCard);
    }

    public void yesNoPopUp() {
        yesNoFrame = new JFrame();

        yesNoFrame.setTitle("Yes/No");
        yesNoFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        yesNoFrame.setLayout(new BorderLayout());
        yesNoFrame.setLocation(300,300);
        yesNoFrame.add(new JLabel("Do you want to reload?"),BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lockReload) {
                    yesnochoice = ((JButton) e.getSource()).getText().toLowerCase();
                    lockReload.notifyAll();
                }
            }
        });

        buttonPanel.add(yes);
        buttonPanel.add(no);
        yesNoFrame.add(buttonPanel);
        yesNoFrame.setVisible(true);
        yesNoFrame.pack();
    }

    public String yesNoChoose() {

        synchronized (lockReload) {
            try {
                lockReload.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }
        }
        yesNoFrame.setVisible(false);
        yesNoFrame.dispose();

        return yesnochoice;
    }
}