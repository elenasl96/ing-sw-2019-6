package view.gui;

import controller.ClientController;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

public class SpawnTileWeaponActionListener implements MouseListener {

    private ClientController clientController;
    private String s;

    public SpawnTileWeaponActionListener(ClientController clientController) {
        this.clientController = clientController;
    }


    public String getS() {
        return s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        s = ((SquarePanel) e.getSource()).getCoordinate();
        try {
            clientController.askWeapons(s);
        } catch (RemoteException ex) {
            ex.printStackTrace();
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
