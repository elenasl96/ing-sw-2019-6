package view.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveButtonActionListener implements ActionListener {

    private final Object lock;
    private String s;

    MoveButtonActionListener(Object lock) {
        this.lock = lock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (lock) {
            s = ((MoveButton) e.getSource()).getMove();
            lock.notifyAll();
        }

    }

    public String getS() {
        return s;
    }
}
