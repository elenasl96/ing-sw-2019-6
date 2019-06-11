package view.gui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChooseCardActionListener implements MouseListener {
    private Object lock;
    private String s;

    public ChooseCardActionListener(Object lock) {
        this.lock = lock;
        s = new String("");
    }

    public String getS() {
        return s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        synchronized (lock) {
            s = ((WeaponCard) e.getSource()).getNum()+"";
            lock.notifyAll();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
