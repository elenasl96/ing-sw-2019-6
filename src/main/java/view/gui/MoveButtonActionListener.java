package view.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveButtonActionListener implements ActionListener {

    private Object lock;
    private String s;

    public MoveButtonActionListener(Object lock) {
        this.lock = lock;
        s = new String("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (lock) {
            s = ((MoveButton) e.getSource()).getMove();
            lock.notifyAll();
        }
        ((MoveButton)e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());

    }

    public String getS() {
        return s;
    }
}
