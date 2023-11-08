package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonsListener implements MouseListener {
    public ButtonsListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Component c = e.getComponent();
        if( ((BoardButton) c).getDraw() == 1)
            ((BoardButton) c).setDraw(2);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Component c = e.getComponent();
        if( ((BoardButton) c).getDraw() == 2)
            ((BoardButton) c).setDraw(1);
    }
}

