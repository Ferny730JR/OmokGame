package gui;

import omok.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GUI {

    Board board;
    private JButton[][] omokBoardButtons;
    private JPanel omokBoard;
    public GUI() {
        board = new Board();

        JFrame frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main gui
        JPanel gui = new JPanel();
        gui.setLayout(new BorderLayout(3,3));

        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(new Color(42, 55, 59, 255));
        boardConstrain.add(getOmokBoardGUI());

        gui.add(boardConstrain);


        frame.setContentPane(gui);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getOmokBoardGUI() {
        omokBoardButtons = new JButton[board.size()][board.size()];
//        omokBoard = new JPanel(new GridLayout(15,15)) {
//            /**
//             * Override the preferred size to return the largest it can, in
//             * a square shape.  Must (must, must) be added to a GridBagLayout
//             * as the only component (it uses the parent as a guide to size)
//             * with no GridBagConstaint (so it is centered).
//             */
//            @Override
//            public final Dimension getPreferredSize() {
//                Dimension d = super.getPreferredSize();
//                Dimension prefSize = null;
//                Component c = getParent();
//                if (c == null) {
//                    prefSize = new Dimension(
//                            (int)d.getWidth(),(int)d.getHeight());
//                } else if (c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
//                    prefSize = c.getSize();
//                } else {
//                    prefSize = d;
//                }
//                int w = (int) prefSize.getWidth();
//                int h = (int) prefSize.getHeight();
//                // the smaller of the two sizes
//                int s = Math.min(w, h);
//                return new Dimension(s,s);
//            }
//        };

        omokBoard = new BoardPanel();
        omokBoard.setBackground(new Color(0,0,0,0));
        omokBoard.setPreferredSize(new Dimension(500,500));
        for(int row = 0; row< omokBoardButtons.length; row++) {
            for(int col = 0; col< omokBoardButtons[row].length; col++) {
                JButton b = new JButton("");
                b.setMargin(new Insets(0,0,0,0));
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(128,128, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if(col%2==0 && row%2==0 || col%2==1 && row%2==1) {
                    b.setBackground(Color.BLACK);
                } else {
                    b.setBackground(Color.WHITE);
                }
                omokBoardButtons[row][col] = b;

                //omokBoard.add(omokBoardButtons[row][col]);

                //omokBoardButtons[row][col] = new JButton("");
                //omokBoardButtons[row][col].setBorder( new LineBorder(Color.BLACK));
                //omokBoard.add(omokBoardButtons[row][col]);
            }
        }
        return omokBoard;
    }
}
