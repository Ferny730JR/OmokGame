package omok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {

    private static Board boardInstance;
    private final Player[][] board;
    private final int size;
    private List<Place> occupiedPositions;

    /** Create a new board of the default size. */
    public Board() throws NegativeArraySizeException {
        this(15);
    }

    /** Create a new board of the specified size. */
    public Board(int size) throws NegativeArraySizeException {
        this.size = size;
        this.board = new Player[this.size][this.size];
        this.occupiedPositions = new ArrayList<>();
    }

    public static Board getBoardInstance() {
        if (boardInstance == null) {
            boardInstance = new Board();
        }
        return boardInstance;
    }

    /**
     * Returns the board
     */
    public Player[][] getBoard() {
        return board;
    }

    public List<Place> getOccupiedPositions () {
        return occupiedPositions;
    }

    /** Return the size of this board. */
    public int size() {
        return size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        for(Player[] row : board) {
            Arrays.fill(row, null);
        }
        occupiedPositions.clear();
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(Player[] row : board) {
            for (Player element : row) {
                if (element == null) return false; // if it has an empty spot, it is not full
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
     * @param player omok.Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) throws ArrayIndexOutOfBoundsException {
        board[x][y] = player;

        if(player == null) {
            for(Place position : occupiedPositions) {
                if(position.x == x && position.y == y) {
                    occupiedPositions.remove(position);
                    break;
                }
            }
        } else {
            occupiedPositions.add(new Place(x,y));
        }
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return board[x][y]==null;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y]!=null;
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
        return board[x][y]==player;
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        return board[x][y];
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        List<Place> winner = nInARow(5,player);
        if(!winner.isEmpty()) {
            Place place = winner.get(0);
            return player == board[place.x][place.y];
        }
        return false;
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
        // Create list of winning position
        List<Place> winningRow = new LinkedList<>();

        // Check rows
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size - 5; col++) {
                Player player = board[row][col];
                if (player != null &&
                        player == board[row][col + 1] &&
                        player == board[row][col + 2] &&
                        player == board[row][col + 3] &&
                        player == board[row][col + 4]) {
                    winningRow.add(new Place(row,col));
                    winningRow.add(new Place(row,col+1));
                    winningRow.add(new Place(row,col+2));
                    winningRow.add(new Place(row,col+3));
                    winningRow.add(new Place(row,col+4));
                    return winningRow;
                }
            }
        }

        // Check columns
        for (int col = 0; col < size; col++) {
            for (int row = 0; row <= size - 5; row++) {
                Player player = board[row][col];
                if (player != null &&
                        player == board[row + 1][col] &&
                        player == board[row + 2][col] &&
                        player == board[row + 3][col]
                        && player == board[row + 4][col]) {
                    winningRow.add(new Place(row,col));
                    winningRow.add(new Place(row+1,col));
                    winningRow.add(new Place(row+2,col));
                    winningRow.add(new Place(row+3,col));
                    winningRow.add(new Place(row+4,col));
                    return winningRow;
                }
            }
        }

        // Check diagonals
        for (int row = 0; row <= size - 5; row++) {
            for (int col = 0; col <= size - 5; col++) {
                Player player = board[row][col];
                if (player != null &&
                        player == board[row + 1][col + 1] &&
                        player == board[row + 2][col + 2] &&
                        player == board[row + 3][col + 3] &&
                        player == board[row + 4][col + 4]) {
                    winningRow.add(new Place(row,col));
                    winningRow.add(new Place(row+1,col+1));
                    winningRow.add(new Place(row+2,col+2));
                    winningRow.add(new Place(row+3,col+3));
                    winningRow.add(new Place(row+4,col+4));
                    return winningRow;
                }
                player = board[row + 4][col];
                if (player != null &&
                        player == board[row + 3][col + 1] &&
                        player == board[row + 2][col + 2] &&
                        player == board[row + 1][col + 3] &&
                        player == board[row][col + 4]) {
                    winningRow.add(new Place(row+4,col));
                    winningRow.add(new Place(row+3,col+1));
                    winningRow.add(new Place(row+2,col+2));
                    winningRow.add(new Place(row+1,col+3));
                    winningRow.add(new Place(row,col+4));
                    return winningRow;
                }
            }
        }

        // No winner
        return winningRow;
    }

    /**
     * Return the number of stone in a row the player has
     * on the board.
     *
     * @param numberOfStones Number of consecutive stone.
     * @param player         The player to check their moves.
     *
     * @return A Place iterable containing the moves of numberOfStones consecutive
     * moves in a row.
     *
     * @see Place
     */
    public List<Place> nInARow(int numberOfStones, Player player) {
        List<Place> nInARow = new LinkedList<>();

        // Special Case 1 stone in a row
        if(numberOfStones == 1) {
            for(int row=0; row<size; row++) {
                for(int col=0; col<size; col++) {
                    if(player== board[row][col]) {
                        nInARow.add(new Place(row,col));
                        return nInARow;
                    }
                }
            }
        }

        // Check rows
        for(int row = 0; row<size; row++) {
            for(int col = 0; col<=size-numberOfStones; col++) {
                nInARow.add(new Place(row,col));
                for (int i = col; i < col + numberOfStones - 1; i++) { // O(1)
                    try {
                        if (board[row][i] != board[row][i + 1] || board[row][i] != player) {
                            nInARow.clear();
                            break;
                        } else {
                            nInARow.add(new Place(row, i+1));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        nInARow.clear();
                        break;
                    }
                }
                if (nInARow.size() == numberOfStones) return nInARow;
            }
        }

        // Check columns
        for(int col = 0; col<size; col++) {
            for(int row = 0; row<=size-numberOfStones; row++) {
                nInARow.add(new Place(row, col));
                for (int i = row; i < row + numberOfStones - 1; i++) { // O(1)
                    try {
                        if (board[i][col] != board[i+1][col] ||
                                board[i][col] != player) {
                            nInARow.clear();
                            break;
                        } else {
                            nInARow.add(new Place(i+1, col));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        nInARow.clear();
                        break;
                    }
                }
                if (nInARow.size() == numberOfStones) return nInARow;
            }
        }

        // Check diagonals
        for(int row = 0; row<=size-numberOfStones; row++) {
            for(int col = 0; col<=size-numberOfStones; col++) {
                nInARow.add(new Place(row+numberOfStones-1, col));
                for (int j=row,i=col; i<col+numberOfStones-1; j--,i++) { // O(1)
                    try {
                        if (board[j+numberOfStones-1][i] != board[j+numberOfStones-2][i+1] ||
                                board[j+numberOfStones-1][i] != player) {
                            nInARow.clear();
                            break;
                        } else {
                            nInARow.add(new Place(j+3, i+1));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        nInARow.clear();
                        break;
                    }
                }
                if (nInARow.size() == numberOfStones) return nInARow;
                nInARow.add(new Place(row, col));
                for (int j=row,i=col; i<col+numberOfStones-1; j++,i++) { // O(1)
                    try {
                        if (board[j][i] != board[j+1][i+1] ||
                                board[j][i] != player) {
                            nInARow.clear();
                            break;
                        } else {
                            nInARow.add(new Place(j+1, i+1));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        nInARow.clear();
                        break;
                    }
                }
                if (nInARow.size() == numberOfStones) return nInARow;
            }
        }

        return nInARow;
    }

    /**
     * Return the highest number of stones in a row the player has
     * on the board.
     *
     * @param player         The player to check their moves.
     *
     * @return A Place iterable containing the moves of numberOfStones consecutive
     * moves in a row.
     *
     * @see Place
     */
    public int[] maxStonesInARow(Player player) {
        int[] maxPositionCount = new int[]{0,0};
        int consecutiveCount = 0;
        int openEndsCount = 0;
        boolean isConsecutive=false;

        // Check rows
        for(int row = 0; row<size; row++) {
            for(int col = 0; col<size; col++) {
                if(board[row][col] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[row][col] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[row][col] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;

                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        // Check columns
        for(int col = 0; col<size; col++) {
            for(int row = 0; row<size; row++) {
                if(board[row][col] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[row][col] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[row][col] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;
                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        // Check diagonal 1 top
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0, x = i; x <= size - 1; j++, x++) {
                if(board[x][j] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[x][j] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[x][j] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;
                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        // check diagonal 1 bottom
        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0, y = i; y <= size - 1; j++, y++) {
                if(board[j][y] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[j][y] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[j][y] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;
                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        // check diagonal 2 top half
        for( int k = 0 ; k < size ; k++ ) {
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if(board[i][j] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[i][j] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[i][j] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;
                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        // check diagonal 2 bottom half
        for( int k = size - 2 ; k >= 0 ; k-- ) {
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if(board[size-j-1][size-i-1] == player) {
                    consecutiveCount++;
                    isConsecutive=true;
                }
                else if(board[size-j-1][size-i-1] == null && consecutiveCount > 0 && openEndsCount<2) openEndsCount++;
                else if(board[size-j-1][size-i-1] == null) openEndsCount = 1;
                else if(consecutiveCount > 0) {
                    consecutiveCount = 0;
                    openEndsCount = 0;}
                else openEndsCount = 0;
                if(consecutiveCount+openEndsCount > maxPositionCount[0]+maxPositionCount[1]) {
                    maxPositionCount[0] = consecutiveCount;
                    maxPositionCount[1] = openEndsCount;
                }
                if(!isConsecutive) consecutiveCount = 0;
                isConsecutive = false;
            }
            consecutiveCount = 0;
            openEndsCount = 0;
        }

        return maxPositionCount;
    }

    /**
     * Get a deep copy of the Tic Tac Toe board.
     * @return      an identical copy of the board
     */
    public Board getDeepCopy () {
        Board newBoard = new Board(size);

        for(int row = 0; row < newBoard.size(); row++) {
            if (newBoard.size() >= 0) System.arraycopy(this.board[row], 0, newBoard.board[row], 0, newBoard.size());
        }
        newBoard.occupiedPositions = this.occupiedPositions;

        return newBoard;
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

        @Override
        public boolean equals(Object other) {
            if (this == other) { return true; }
            if (other == null) { return false; }
            if (getClass() != other.getClass()) { return false; }
            return x == ((Place) other).x && y == ((Place) other).y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = result << 8 | y;
            return result;
        }
    }
}