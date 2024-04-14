package gui;

import javax.swing.*;

import omok.Computer;
import omok.Game;
import omok.Player;
import omok.Board;
import java.awt.*;
import java.util.List;
import java.util.Queue;

public class BoardPanel extends JPanel {
    private final Board board;
    private Game game;
    private Queue<Player> players;
    private final BoardButton[][] positions;
    ButtonsListener listener;

    public BoardPanel(Board board) { // CHANGE TO SINGLETON METHOD
        this.board = board;
        positions = new BoardButton[board.size()][board.size()];

        setLayout(new GridLayout(board.size(),board.size()));
        setPreferredSize(new Dimension(500,500));
        setOpaque(false);

        Thread repainter = getThread();
        repainter.start();
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Board getBoard() {
        return board;
    }

    public Queue<Player> getPlayers() { return players; }

    /**
     *
     */
    public void initializeGUI() {

        listener = new ButtonsListener();

        for(int row = 0; row < positions.length; row++) {
            for(int col = 0; col < positions[row].length; col++) {
                BoardButton b = BoardButton.generateBoardButton(row,col);
                b.addMouseListener(listener);
                b.addActionListener(e -> {
                    BoardPanel bp = (BoardPanel) b.getParent();

                    Player currentPlayer = players.poll();
                    b.setStoneColor( currentPlayer.getColor() );
                    b.setDraw(3);

                    Board board = bp.getBoard();
                    board.placeStone( b.x, b.y, currentPlayer);

                    if(game.gameOver()) {
                        Iterable<Board.Place> winner = board.winningRow();
                        for(Board.Place place : winner) {
                            positions[place.x][place.y].setDraw(4);
                        }
                        JOptionPane.showMessageDialog(bp, currentPlayer.getName() + " has won!");
                        disableButtons();
                        return;
                    }
                    bp.disableButton(b);
                    players.offer(currentPlayer);

                    System.out.println(players.peek().getClass());
                    if(players.peek().getClass() == Computer.class) {
                        currentPlayer = players.poll();
                        bp.computerTurn((Computer) currentPlayer);
                        players.offer(currentPlayer);
                    }
                });
                positions[row][col] = b;
                add(positions[row][col]);
            }
        }
    }

    public void computerTurn(Computer currentPlayer) {
        int[] move = currentPlayer.makeMove(board);
        BoardButton currentButton = positions[move[0]][move[1]];
        currentButton.setStoneColor(currentPlayer.getColor());
        currentButton.setDraw(3);
        disableButton(currentButton);
        board.placeStone(move[0],move[1],currentPlayer);
        if(game.gameOver()) {
            List<Board.Place> winner = board.nInARow(5,currentPlayer);
            for(Board.Place place : winner) {
                positions[place.x][place.y].setDraw(4);
            }
            JOptionPane.showMessageDialog(this, currentPlayer.getName() + " has won!");
            disableButtons();
        }

    }

    private void disableButton(BoardButton button) {
        button.setModel(new DefaultButtonModel() {
            @Override
            public boolean isArmed() { return false; }

            @Override
            public boolean isPressed() { return false; }
        });
    }

    private void disableButtons() {
        for(BoardButton[] row : positions)
            for(BoardButton button : row)
                disableButton(button);
    }

    /**
     * Override the preferred size to return the largest it can, in
     * a SQUARE shape.  Must (must, must) be added to a <Strong>GridBagLayout</Strong>
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
        return new Dimension(s-s/8,s-s/8);
    }

    /**
     * Creates a low priority thread that calls {@link Component#repaint()}
     * on the board every 30 milliseconds. This is to fix the board not
     * refreshing and displaying the most recent changes done (making a
     * move, mouse hovering over position, etc.).
     *
     * @return Low priority thread.
     */
    private Thread getThread() {
        Thread repainter = new Thread( () -> {
            while (this.isVisible()) { // thread only runs when board is visible
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {}
            }
        });
        repainter.setName("Panel repaint");
        repainter.setPriority(Thread.MIN_PRIORITY);
        return repainter;
    }
}
