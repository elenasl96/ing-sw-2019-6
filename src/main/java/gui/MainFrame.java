package gui;

import model.room.ModelObserver;
import model.room.Update;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import static model.enums.Character.PG1;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -1946117194064716902L;

    private JTextField textField = new JTextField();
    private JPanel panel1 = new JPanel();
    private JPanel[] board = {
            new BoardPanelLeft1(),
            new BoardPanelRight1()
    } ;

    public void initGUI() {
        panel1.setLayout(new GridLayout(1, 1));
        panel1.add(textField);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        board[0] = new BoardPanelLeft1();
        board[1] = new BoardPanelRight1();
        
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.PAGE_END);
        // Try to comment it out
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // https://stackoverflow.com/questions/22982295/what-does-pack-do
        pack();
        setVisible(true);
    }

    public void onUpdate(Update update){
        textField.setText(update.toString());
    }
}
