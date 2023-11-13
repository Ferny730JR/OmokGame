package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import omok.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
    // GUI attributes
    BoardPanel boardGUI;
    JPanel boardConstrain;

    // Game attributes
    Game game;
    Board board;
    int boardSize=15; // default
    Player player1,player2;
    Queue<Player> playerQueue = new LinkedList<>();
    int gameType=1; // default player vs. player
    int computerDifficulty=2;

    // Menu attributes
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem, pvp;
    JCheckBoxMenuItem cbMenuItem;
    public GUI()  {
        FlatDarkLaf.setup();
        board = new Board(boardSize);

        JFrame frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup main gui
        JPanel gui = new JPanel();
        gui.setLayout(new BorderLayout(3,3));
        gui.setBorder(new EmptyBorder(5,5,5,5));

        this.initializeMenu();

        this.initNewGame();

        boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(new Color(42, 55, 59, 255));
        getOmokBoardGUI();
        boardConstrain.add(boardGUI);

        gui.add(boardConstrain);

        frame.setMinimumSize(new Dimension(500,500));
        frame.setJMenuBar(menuBar);
        frame.setContentPane(gui);
        frame.pack();
        frame.setVisible(true);
    }

    public void getOmokBoardGUI() {
        boardGUI = new BoardPanel(board);
        boardGUI.setPlayers(playerQueue);
        boardGUI.setGame(game);
        boardGUI.initializeGUI();
    }

    private void initNewGame() {
        playerQueue.clear();
        board = new Board(boardSize);

        player1 = new Player("Bazinga", new Color(25, 187, 156));
        if(gameType == 1) { player2 = new Player("Ahooga", new Color(173, 78, 213)); }
        else {
            player2 = new Computer("Ahooga", new Color(173, 78, 213), computerDifficulty);
            ((Computer)player2).setOpponent(player1);
        }
        game = new Game(board, new ArrayList<>(Arrays.asList(player1, player2)));

        System.out.printf("gametype: %s\n",gameType);
        System.out.printf("player1: %s\n",player1.getClass());
        System.out.printf("player2: %s\n",player2.getClass());
        playerQueue.offer(player1);
        playerQueue.offer(player2);
    }

    private void initializeMenu() {
        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a submenu
        submenu = new JMenu("New Game");
        submenu.setMnemonic(KeyEvent.VK_S);

        ButtonGroup group = new ButtonGroup();
        pvp = new JRadioButtonMenuItem("Player vs. Player",true);
        pvp.setMnemonic(KeyEvent.VK_R);
        pvp.addActionListener(e -> gameType = 1);
        group.add(pvp);
        submenu.add(pvp);

        rbMenuItem = new JRadioButtonMenuItem("Computer AI");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        rbMenuItem.addActionListener(e -> gameType = 2);
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);

        submenu.addSeparator();

        menuItem = new JMenuItem("Customize Board Size");
        String[] boardSizeOptions = {
                "3x3", "8x8", "15x15", "17x17", "19x19", "21x21" };
        JComboBox jcBoardSizeOptions = new JComboBox(boardSizeOptions);
        menuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(GUI.this, jcBoardSizeOptions, "Choose Board Size:", JOptionPane.PLAIN_MESSAGE);
            boardSize = Integer.parseInt( ((String) jcBoardSizeOptions.getSelectedItem()).split("x")[0] );
        });
        submenu.add(menuItem);
        submenu.addSeparator();

        menuItem = new JMenuItem("Create New Game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(e -> {
            int user_choice = JOptionPane.showConfirmDialog(GUI.this, "Are you sure you want to start a new game?", "New Game!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (user_choice == 0) { // 0 is yes, 1 is no
                boardConstrain.remove(boardGUI);
                initNewGame();
                getOmokBoardGUI();
                boardConstrain.add(boardGUI);
                boardConstrain.revalidate();
                boardConstrain.repaint();
            }
        });
        submenu.add(menuItem);
        menu.add(submenu);

        //a group of JMenuItems
        menu.addSeparator();
        menuItem = new JMenuItem("Instructions",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("Allow hints");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("???");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);
    }
}
