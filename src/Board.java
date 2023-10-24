import java.util.Arrays;
/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    char[][] board;
    private final int size;

    /** Create a new board of the default size. */
    public Board() {
        this(15);
    }

    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size = size;
        this.board = new char[this.size][this.size];
        for(char[] row : board) {
            Arrays.fill(row,'\u2022');
        }
    }

    /** Return the size of this board. */
    public int size() {
        return size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        for(char[] row : board) {
            Arrays.fill(row, '\u2022');
        }
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(char[] row : board) {
            for (char element : row) {
                if (element == '•') return false; // if it has an empty spot, it is not full
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        board[x][y] = player.getPlayerPiece();
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        for(char[] row : board) {
            for (char element : row) {
                if (element != '•') return false; // if it does not have an empty spot, it is empty
            }
        }
        return true;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y]!='•';
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        return board[x][y]==player.getPlayerPiece();
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        return null;
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
    }

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        /** Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // other methods if needed ...
    }
}
/*
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
 */
