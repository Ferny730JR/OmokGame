package gui;

import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final JButton[][] positions;
    private JPanel boardPanel;
    private int buttonSpace;
    private final int dimOfButtons;

    public BoardPanel() {
        this(15);
    }

    public BoardPanel(int boardSize) {
        positions = new JButton[boardSize][boardSize];
        boardPanel = new JPanel(new GridLayout(boardSize,boardSize));
        dimOfButtons = boardSize+1; // add two to account for position data
    }

    private int updateButtonSpace() {
        Dimension d = this.getSize();
        return Math.min(d.width,d.height)/ dimOfButtons;
    }

    /**
     * Draws the board for the BoardPanel using the dimension of the panel.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see JComponent#paintComponent(Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        buttonSpace = updateButtonSpace();
        int offset = buttonSpace;

        g.setColor(new Color(74, 222, 222));
        ((Graphics2D) g).setStroke(new BasicStroke(buttonSpace/dimOfButtons));
        g.fillRoundRect(0,0,buttonSpace*dimOfButtons,buttonSpace*dimOfButtons,buttonSpace,buttonSpace);

        g.setColor(new Color(121, 126, 246, 128));
        for(int row=0; row<positions.length; row++) {
            g.drawLine(offset, buttonSpace*row+offset, buttonSpace*(dimOfButtons -1), buttonSpace*row+offset);
            g.drawLine(buttonSpace*row+offset,offset,buttonSpace*row+offset,buttonSpace*(dimOfButtons -1));
        }
    }

    /**
     * Override the preferred size to return the largest it can, in
     * a square shape.  Must (must, must) be added to a GridBagLayout
     * as the only component (it uses the parent as a guide to size)
     * with no GridBagConstaint (so it is centered).
     */
    @Override
    public final Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Dimension prefSize = null;
        Component c = getParent();
        if (c == null) {
            prefSize = new Dimension(
                    (int)d.getWidth(),(int)d.getHeight());
        } else if (c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
            prefSize = c.getSize();
        } else {
            prefSize = d;
        }
        int w = (int) prefSize.getWidth();
        int h = (int) prefSize.getHeight();
        // the smaller of the two sizes
        int s = Math.min(w, h);
        return new Dimension(s,s);
    }
}
