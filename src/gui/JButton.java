package gui;

import java.awt.*;

public class JButton extends javax.swing.JButton {

    JButton(String s) {
        super(s);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int s = (int)(Math.max(d.getWidth(), d.getHeight()));
        return new Dimension(s,s);
    }
}
