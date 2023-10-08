import java.util.Arrays;

public class Board {
    char[][] board;
    int boardSize;
    public Board(int boardSize) {
        // Initialize board
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        for (char[] chars : board)
            Arrays.fill(chars, '•');
    }
    // Getter methods
    public char[][] getBoard() {
        return board;
    }
    public int getBoardSize() {
        return boardSize;
    }

    // Board methods
    public void updateBoard(Player player, int x, int y) {
        board[y-1][x-1] = player.getPlayerPiece();
    }

    public boolean isFull() {
        for(char[] row : board)
            for(char element : row)
                if(element == '•') return false;
        return true;
    }
}
