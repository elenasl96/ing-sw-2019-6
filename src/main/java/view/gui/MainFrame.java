package view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;
    private static final int DIM_AMMO_IMAGE = 30;
    private static final int WIDTH_PAWN = 70;
    private static final int HEIGHT_PAWN = 60;

    private PopUpCards popUp;
    private PopUpChooseEffect popUpEffect;

    private AmmoPanel ammoRed;
    private AmmoPanel ammoBlue;
    private AmmoPanel ammoYellow;

    private JTextArea console;
    private MoveButton grab;
    private MoveButton run;
    private MoveButton shoot;
    private JLabel playerNameLabel;

    private final transient Object lockMove;
    private final transient Object lockCoordinate;
    private final transient Object lockChooseCard;
    private final transient Object lockReload;
    private final transient Object lockEffect;
    private final transient Object lockCharacter;

    private transient MoveButtonActionListener actionListenerMovement;
    private transient CoordinateActionListener actionListenerCoordinate;
    private JPanel turnLight;
    private JPanel centralPanel;
    private JComboBox weapon;
    private JComboBox powerUp;
    private String yesnochoice;
    private JFrame yesNoFrame;
    private JFrame effectFrame;
    private PlayerBoardPanel playerBoard;
    private JLabel time;
    private KillshotTrackPanel killshotTrack;

    private SquarePanel[][] mapGrid;
    private transient Character[] charactersCoordinates;

    private String command;
    private String playerSelected;
    private String stringFields;
    private int typeMap;
    private int nSkull;
    private javax.swing.Timer timer;
    private Timer refresh;

    MainFrame() {
        lockMove = new Object();
        lockCoordinate = new Object();
        lockChooseCard = new Object();
        lockReload = new Object();
        lockEffect = new Object();
        lockCharacter = new Object();

        actionListenerMovement = new MoveButtonActionListener(lockMove);
        actionListenerCoordinate = new CoordinateActionListener(lockCoordinate);
        mapGrid = new SquarePanel[3][4];
        command = "";
        playerSelected = "";
        typeMap = 2;
        nSkull = 8;
        charactersCoordinates = new Character[5];
        initCharacters();

    }

    private void initCharacters() {
        for(int i=0;i<5;i++) {
            charactersCoordinates[i] = new Character(new CharacterLabel(
                    new ImageIcon(new ImageIcon(this.getClass().getResource("Pedine/pg" +
                    (i + 1)+".png")).getImage()
                    .getScaledInstance(WIDTH_PAWN, HEIGHT_PAWN, Image.SCALE_SMOOTH))));
            charactersCoordinates[i].getIcon().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    synchronized (lockCharacter) {
                        playerSelected = ((CharacterLabel) e.getSource()).getPlayer();
                        lockCharacter.notifyAll();
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
            });
        }
    }

    void initGUI() {
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

        playerNameLabel = new JLabel("NOME GIOCATORE",SwingConstants.CENTER);
        JPanel writesPanel = new JPanel(new GridLayout(2,1));
        writesPanel.add(playerNameLabel);
        writesPanel.add(new JLabel("AMMO",SwingConstants.CENTER));
        JPanel ammos = new JPanel(new GridLayout(3,1));

        ammoRed = new AmmoPanel(1, new ImageIcon(new ImageIcon(
                this.getClass().getResource("Weapons/red.png"))
                .getImage().getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
        ammoBlue = new AmmoPanel(1,new ImageIcon(new ImageIcon(
                this.getClass().getResource("Weapons/blue.png"))
                .getImage().getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
        ammoYellow = new AmmoPanel(1,new ImageIcon(new ImageIcon(
                this.getClass().getResource("Weapons/yellow.png"))
                .getImage().getScaledInstance(DIM_AMMO_IMAGE, DIM_AMMO_IMAGE, Image.SCALE_SMOOTH)));
        ammos.add(ammoRed);
        ammos.add(ammoBlue);
        ammos.add(ammoYellow);
        JLabel cardLabel = new JLabel("CARTE IN POSSESSO");
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel left = new JPanel(new GridLayout(4, 1));
        left.add(writesPanel);
        left.add(ammos);
        left.add(cardLabel);
        left.add(cardsContainer);

        left.setSize(300,500);
        left.setPreferredSize(new Dimension(300,500));
        left.setMaximumSize(new Dimension(300,500));
        left.setMinimumSize(new Dimension(300,500));

        //Create right section of GUI
        console = new JTextArea("");
        console.setLineWrap(true);
        console.setEditable(false);
        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        turnLight = new JPanel();
        turnLight.setBackground(Color.RED);

        grab = new MoveButton("Grab", "grab");
        run = new MoveButton("Run", "run");
        shoot = new MoveButton("Shoot", "shoot");
        grab.addActionListener(actionListenerMovement);
        run.addActionListener(actionListenerMovement);
        shoot.addActionListener(actionListenerMovement);
        grab.setEnabled(false);
        run.setEnabled(false);
        shoot.setEnabled(false);

        JPanel buttonContainer = new JPanel(new GridLayout(5, 1));
        buttonContainer.add(new JLabel("Actions"));
        buttonContainer.add(run);
        buttonContainer.add(grab);
        buttonContainer.add(shoot);
        buttonContainer.add(new JLabel("Your turn"));

        JPanel middleRightContainer = new JPanel(new GridLayout(2, 1));
        JPanel middletop = new JPanel(new GridLayout(2,1));
        middletop.add(turnLight);
        JPanel timerPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(this.getClass().getResource(
                "clessidra.gif")).getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        time = new JLabel("02:00");
        time.setIcon(imageIcon);
        timerPanel.add(time);
        middletop.add(timerPanel);
        killshotTrack = new KillshotTrackPanel();

        middleRightContainer.add(middletop);
        middleRightContainer.add(killshotTrack);

        JPanel right = new JPanel(new GridLayout(3, 1));
        right.add(buttonContainer);
        right.add(middleRightContainer);
        right.add(new JScrollPane(console));
        right.setSize(270,500);
        right.setPreferredSize(new Dimension(270,500));
        right.setMaximumSize(new Dimension(270,500));
        right.setMinimumSize(new Dimension(270,500));

        //Create central section of GUI
        centralPanel = new JPanel(new GridLayout(3, 4));

        //Create bottom section of GUI
        JPanel voidPanel = new JPanel();
        playerBoard = new PlayerBoardPanel();
        voidPanel.add(playerBoard);
        JButton voidButton = new JButton("Void");
        voidButton.addActionListener(e -> {
            synchronized (lockCharacter) {
                actionListenerCoordinate.setS("");
                playerSelected = "";
                lockCharacter.notifyAll();
            }
            synchronized (lockCoordinate) {
                lockCoordinate.notifyAll();
            }
        });
        voidPanel.add(voidButton);

        timer = new Timer(1000, new ActionListener() {
            int seconds = 59;
            int minutes = 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(minutes>=0) {
                    if (seconds >= 10) {
                        time.setText("0" + minutes + ":" + seconds);
                    } else if(seconds == 0){
                        minutes--;
                        seconds = 59;
                    }else {
                        time.setText("0" + minutes + ":0" + seconds);
                    }
                    seconds--;
                }
            }
        });

        createField();

        add(centralPanel, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(voidPanel, BorderLayout.SOUTH);
        setSize(1150, 590);
        setResizable(false);
        //timer.start();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    void setConsole(String message) {
        console.append(message + "\n");
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

    void setBackGroundTurn(boolean val) {
        if (val) {
            turnLight.setBackground(Color.GREEN);
        } else {
            turnLight.setBackground(Color.RED);
        }
    }

    private void createField(){
            int cont = 1;
            for(int i=0;i<3;i++) {
                for(int j=0;j<4;j++) {
                    mapGrid[i][j]=new SquarePanel((char)(j+65)+" "+(3-i));
                    mapGrid[i][j].setLayout(new GridLayout(3,2));
                    mapGrid[i][j].addMouseListener(actionListenerCoordinate);

                    centralPanel.add(mapGrid[i][j]);

                    cont++;
                }
            }

        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void printField() {
        Integer cont = 1;
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                mapGrid[i][j].setImg(new ImageIcon(new ImageIcon(this.getClass().getResource("Field" + typeMap + "/" +
                        "image_part_0"+String.format("%02d",cont) +".png")).getImage()
                        .getScaledInstance(140,140, Image.SCALE_SMOOTH)));
                mapGrid[i][j].setLayout(new GridLayout(3,2));
                mapGrid[i][j].addMouseListener(actionListenerCoordinate);

                centralPanel.add(mapGrid[i][j]);

                cont++;
            }
        }

        killshotTrack.setnSkull(nSkull);
        killshotTrack.revalidate();
        killshotTrack.repaint();
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    public void updateMap(int character, String coordinates) {
        String[] newCoord = coordinates.split(" ");

        if(charactersCoordinates[character].getCoordinate()!=null) {
            String[] oldCoord = charactersCoordinates[character].getCoordinate().split(" ");
            mapGrid[3-Integer.parseInt(oldCoord[1])][oldCoord[0].charAt(0) - 65]
                    .remove(charactersCoordinates[character].getIcon());
            mapGrid[3-Integer.parseInt(oldCoord[1])][oldCoord[0].charAt(0) - 65]
                    .revalidate();
            mapGrid[3-Integer.parseInt(oldCoord[1])][oldCoord[0].charAt(0) - 65]
                    .repaint();
        }

        mapGrid[3-Integer.parseInt(newCoord[1])][newCoord[0].charAt(0) - 65]
                .add(charactersCoordinates[character].getIcon());
        mapGrid[3-Integer.parseInt(newCoord[1])][newCoord[0].charAt(0) - 65].revalidate();
        mapGrid[3-Integer.parseInt(newCoord[1])][newCoord[0].charAt(0) - 65].repaint();

        charactersCoordinates[character].setCoordinate(coordinates);

        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void changeAmmoPanel(String s) {
        switch(s) {
            case "red":
                ammoRed.addAmmo();
                break;
            case "blue":
                ammoBlue.addAmmo();
                break;
            case "yellow":
                ammoYellow.addAmmo();
                break;
            default:
                break;
        }

        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void clearAmmoPanels() {
        ammoYellow.removeAll();
        ammoBlue.removeAll();
        ammoRed.removeAll();
    }

    void updateAmmoPanels() {
        ammoYellow.revalidate();
        ammoYellow.repaint();
        ammoBlue.revalidate();
        ammoBlue.repaint();
        ammoRed.revalidate();
        ammoRed.repaint();
    }

    SquarePanel[][] getMapGrid() {
        return mapGrid;
    }

    public void clearWeaponBox() {
        weapon.removeAllItems();
    }

    public void clearPuBox() {
        powerUp.removeAllItems();
    }

    void addWeaponBox(String s) {
        weapon.addItem(s);
    }

    void addPuBox(String s) {
        powerUp.addItem(s);
    }

    void disableButtons(String[] data) {

        grab.setEnabled(false);
        run.setEnabled(false);
        shoot.setEnabled(false);
        String buttonBorder = "Button.border";
        grab.setBorder(UIManager.getBorder(buttonBorder));
        run.setBorder(UIManager.getBorder(buttonBorder));
        shoot.setBorder(UIManager.getBorder(buttonBorder));

        for(String s: data) {
            switch(s) {
                case "RUN": run.setEnabled(true); break;
                case "GRAB": grab.setEnabled(true); break;
                case "SHOOT": shoot.setEnabled(true); break;
                default: break;
            }
        }
    }

    String cardChoose() {

        synchronized (lockChooseCard) {
            try {
                lockChooseCard.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }
        }
        String aaa = popUp.close();
        System.out.println(aaa);
        return aaa;
    }

    void createPopUp(String[] nameCard) {
        popUp = new PopUpCards(nameCard, lockChooseCard);
    }

    void yesNoPopUp(String title) {
        yesNoFrame = new JFrame();

        yesNoFrame.setTitle("Yes/No");
        yesNoFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        yesNoFrame.setLayout(new BorderLayout());
        yesNoFrame.setLocation(500,400);
        yesNoFrame.add(new JLabel(title),BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        yes.addActionListener(e -> {
            synchronized (lockReload) {
                yesnochoice = ((JButton) e.getSource()).getText().toLowerCase();
                lockReload.notifyAll();
            }
        });
        no.addActionListener(e -> {
            synchronized (lockReload) {
                yesnochoice = ((JButton) e.getSource()).getText().toLowerCase();
                lockReload.notifyAll();
            }
        });

        buttonPanel.add(yes);
        buttonPanel.add(no);
        yesNoFrame.add(buttonPanel);
        yesNoFrame.pack();
        yesNoFrame.setVisible(true);
        yesNoFrame.toFront();
        yesNoFrame.setResizable(false);

    }

    String yesNoChoose() {

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

    void chooseEffectPopUp(String weapon, int layout) {
        effectFrame = new JFrame();
        effectFrame.setLayout(new BorderLayout());
        effectFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        popUpEffect = new PopUpChooseEffect(weapon, layout);
        effectFrame.add(popUpEffect,BorderLayout.CENTER);
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            synchronized (lockEffect) {
                lockEffect.notifyAll();
            }
        }
        );
        effectFrame.add(ok,BorderLayout.SOUTH);

        effectFrame.setLocation(500,300);
        effectFrame.setSize(150,300);
        effectFrame.setResizable(false);
        effectFrame.toFront();
        effectFrame.setVisible(true);
        //effectFrame.pack();

        refresh = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectFrame.setState(Frame.ICONIFIED);
                effectFrame.setState(Frame.NORMAL);
            }
        });
        refresh.start();
    }

    String askEffects() {
        synchronized (lockEffect) {
            try {
                lockEffect.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }
        }

        Timer refresh = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectFrame.setState(Frame.ICONIFIED);
                effectFrame.setState(Frame.NORMAL);
            }
        });

        refresh.stop();

        String s = popUpEffect.getEffectSerie();
        effectFrame.setVisible(false);
        effectFrame.dispose();
        return s;
    }

    private String selectPlayer() {
        try {
            synchronized (lockCharacter){
                lockCharacter.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        }
        return playerSelected;
    }

    private String getColorRoom (){
        String[] coord = getCoordinate().split(" ");

        switch(typeMap) {
            case 1:
                switch(Integer.parseInt(coord[1])) {
                case 1:
                    switch (coord[0]) {
                        case "B":
                        case "C":
                        case "D":
                            return "yellow";
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch (coord[0]) {
                        case "A":
                        case "B":
                            return "red";
                        case "C":
                        case "D":
                            return "yellow";
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (coord[0]) {
                        case "A":
                        case "B":
                        case "C":
                            return "blue";
                        case "D":
                            return "green";
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
                break;
            case 2:
                switch(Integer.parseInt(coord[1])) {
                    case 1:
                        switch (coord[0]) {
                            case "B":
                            case "C":
                                return "grey";
                            case "D":
                                return "yellow";
                            default:
                                break;
                        }
                        break;
                    case 2:
                        switch (coord[0]) {
                            case "A":
                            case "B":
                                return "red";
                            case "C":
                                return "purple";
                            case "D":
                                return "yellow";
                            default:
                                break;
                        }
                        break;
                    case 3:
                        switch (coord[0]) {
                            case "A":
                            case "B":
                            case "C":
                                return "blue";
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch(Integer.parseInt(coord[1])) {
                case 1:
                    switch (coord[0]) {
                        case "A":
                        case "B":
                            return "grey";
                        case "C":
                        case "D":
                            return "yellow";
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch (coord[0]) {
                        case "A":
                            return "red";
                        case "B":
                            return "purple";
                        case "C":
                        case "D":
                            return "yellow";
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (coord[0]) {
                        case "A":
                            return "red";
                        case "B":
                        case "C":
                            return "blue";
                        case "D":
                            return "green";
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
                break;
            default:
                break;
        }
        return "";
    }

    void addDropPlayerBoard(int num) {
        playerBoard.addDrop(num);
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void addMarkerPlayerBoard(int num){
        playerBoard.addMarker(num);
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    public void addSkullPlayerBoard() {
        playerBoard.addSkull();
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void setPlayerNameLabel(String name) {
        playerNameLabel.setText(name);
    }

    void setTypeMap(int typeMap) {
        this.typeMap = typeMap;
    }

    public void setnSkull(int nSkull) {
        this.nSkull = nSkull;
    }

    public void fillFields(String s) {
        command = "";
        String[] data = s.split(";");
        String[] serie;
        StringBuilder commandBuild;
        for (String effect : data) {
            serie = effect.split(",");
            commandBuild = new StringBuilder();
            for (String field : serie) {
                switch (field) {
                    case "player":
                        setConsole("Select a player (press VOID if optional)");
                        commandBuild.append(selectPlayer()).append(",");
                        break;
                    case "square":
                        setConsole("Select a square (press VOID if optional)");
                        commandBuild.append(getCoordinate()).append(",");
                        break;
                    case "room":
                        setConsole("Select a room (press VOID if optional)");
                        commandBuild.append(getColorRoom()).append(",");
                        break;
                    default:
                        break;
                }
            }
            if(!commandBuild.toString().equals("")) {
                command = command + commandBuild.toString().substring(0,commandBuild.length()-1);
            }
             command = command + ";";
        }

        System.out.println(command);
    }

    void setStringFields(String stringFields) {
        this.stringFields = stringFields;
    }

    String getCommand() {

        fillFields(stringFields);
        return command.substring(0,command.length()-1);
    }

    void setCharacterMatch(String name, int num) {
        charactersCoordinates[num].setName(name);
    }

    Timer getTimer() {
        return timer;
    }

    void setTime(String time) {
        this.time.setText(time);
    }

    void addSkullTrackShot() {
        killshotTrack.addSkull();
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    void popUpVictory() {
        setVisible(false);
        new PopUpVictory();
        dispose();
    }

    void deathPlayerBoard() {
        playerBoard.resetPlayerBoard();
        playerBoard.addSkull();

        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }

    public void removeMarks(int num) {
        playerBoard.removeMarker(num);
        setState(Frame.ICONIFIED);
        setState(Frame.NORMAL);
    }
}