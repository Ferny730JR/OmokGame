package gui;

import omok.Player;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Queue;

public class ButtonsListener implements MouseListener {

    public ButtonsListener() {}

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
        BoardButton c = (BoardButton) e.getComponent();
        BoardPanel p = (BoardPanel) c.getParent();
        if( c.getDraw() == 1) {
            c.setStoneColor(p.getPlayers().peek().getColor());
            c.setDraw(2);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        BoardButton c = (BoardButton) e.getComponent();
        BoardPanel parent = (BoardPanel) c.getParent();
        if( c.getDraw() == 2) {
            c.setStoneColor(parent.getPlayers().peek().getColor());
            c.setDraw(1);
        }
    }
}

