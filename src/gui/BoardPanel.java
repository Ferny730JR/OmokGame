package gui;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardPanel extends JPanel {
    private final JButton[][] positions;
    private JPanel boardPanel;
    private int buttonSpace;
    private final int dimOfButtons;

    public BoardPanel() {
        this(15);
    }

    public BoardPanel(int boardSize) {
        setLayout(new GridLayout(boardSize+1,boardSize+1));
        //setBackground(new Color(255, 0, 0,255));
        setOpaque(false);

        positions = new JButton[boardSize][boardSize];
        boardPanel = new JPanel(new GridLayout(boardSize,boardSize));
        dimOfButtons = boardSize+1; // add two to account for position data

        Thread repainter = getThread();
        repainter.start();
    }

    private Thread getThread() {
        Thread repainter = new Thread(() -> {
            while (true) { // I recommend setting a condition for your panel being open/visible
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {
                }
            }
        });
        repainter.setName("Panel repaint");
        repainter.setPriority(Thread.MIN_PRIORITY);
        return repainter;
    }

    /**
     *
     */
    public void initializeGUI() {

        setPreferredSize(new Dimension(500,500));
        //setBackground(new Color(0, 0, 0, 0));

        for(int row = 0; row < positions.length; row++) {
            for(int col = 0; col < positions[row].length; col++) {
                JButton b = BoardButton.generateBoardButton();
                positions[row][col] = b;
                add(positions[row][col]);
            }
        }
    }

    public JButton[][] getPositions() {
        return positions;
    }

    /**
     * Updates the spacing of buttons based on the dimension of the panel
     * and the number of buttons.
     *
     */
    private int updateButtonSpace() {
        Dimension d = this.getSize();
        return Math.min(d.width,d.height)/dimOfButtons;
    }

    public int getButtonSpace() {
        return buttonSpace;
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
        int width = (int) prefSize.getWidth();
        int height = (int) prefSize.getHeight();
        // the smaller of the two sizes
        int s = Math.min(width, height);
        return new Dimension(s,s);
    }
}
