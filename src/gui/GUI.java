package gui;

import omok.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GUI {

    Board board;
    private JButton[][] omokBoardButtons;
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
        BoardPanel omokBoard = new BoardPanel();
        omokBoard.initializeGUI();
        return omokBoard;
    }
}
