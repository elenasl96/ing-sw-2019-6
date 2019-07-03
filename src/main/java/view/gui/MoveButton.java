package view.gui;

import javax.swing.*;

public class MoveButton extends JButton {
    private String move;

    MoveButton(String label,String move) {
        super(label);
        this.move = move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }
}
