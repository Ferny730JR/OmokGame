package gui;

import omok.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GUI {

    Board board;
    private JButton[][] omokBoardButtons;
    private BoardPanel omokBoard;
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
        omokBoard = new BoardPanel();
        omokBoard.initializeGUI();

        //omokBoard.setPreferredSize(new Dimension(100,100));
//        for(int row = 0; row < omokBoardButtons.length; row++) {
//            for(int col = 0; col < omokBoardButtons[row].length; col++) {
//                JButton b = new JButton("");
//                b.setMargin(new Insets(0,0,0,0));
//                ImageIcon icon = new ImageIcon(
//                        new BufferedImage(omokBoard.getButtonSpace()+1,omokBoard.getButtonSpace()+1, BufferedImage.TYPE_INT_ARGB));
//                b.setIcon(icon);
//                if(col%2==0 && row%2==0 || col%2==1 && row%2==1) {
//                    b.setBackground(Color.BLACK);
//                } else {
//                    b.setBackground(Color.WHITE);
//                }
//                omokBoardButtons[row][col] = b;
//
//                //omokBoard.add(omokBoardButtons[row][col]);
//
//                omokBoardButtons[row][col] = new JButton("");
//                omokBoardButtons[row][col].setBorder( new LineBorder(Color.BLACK));
//                omokBoard.add(omokBoardButtons[row][col]);
//            }
//        }
        return omokBoard;
    }
}
