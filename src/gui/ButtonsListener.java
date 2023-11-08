package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonsListener implements MouseListener {
    private final Color color;
    private Color bkg_color;

    public ButtonsListener(Color color) {
        this.color = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();
        c.setBackground(bkg_color);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Component c = e.getComponent();
        bkg_color = c.getBackground();
        c.setBackground(color);
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

