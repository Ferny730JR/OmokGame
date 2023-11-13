package omok;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Computer extends Player {
    private Player opponent;
    private final int maxDepth;
    private final Random random = new Random();

    /**
     * Constructor
     */
    public Computer(String name, char playerPiece, int maxDepth) {
        super(name,playerPiece);
        this.maxDepth = maxDepth;
    }

    public Computer(String name, Color color, int maxDepth) {
        super(name, color);
        this.maxDepth = maxDepth;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * Compute the most optimal move the computer can make based
     * on the current state of the board.
     *
     * @return The row and col position in the board of the optimal move calculated.
     */
    public int[] makeMove(Board board) {
        if(board.size()>3 && board.getOccupiedPositions().isEmpty()) {
            return new int[]{(board.size()-1)/2, (board.size()-1)/2};
        } else if(board.size() > 3 && board.getOccupiedPositions().size()==1) {
            return makeRandomMove(board);
        }
        float bestMoveInfo = miniMax(board.getDeepCopy(), maxDepth);
        int row = ((int)bestMoveInfo) / board.size();
        int col = ((int)bestMoveInfo) % board.size();
        return new int[] { row, col };
    }

    /**
     * The meat of the algorithm.
     *
     * @param board        the Omok board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private float miniMax(Board board, int currentDepth) {
        return miniMax(true, board, currentDepth, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    private float miniMax(Boolean isMaximizingPlayer, Board board, int currentDepth, float alpha, float beta) {
        if (currentDepth == 0 || board.isFull() || board.isWonBy(this) || board.isWonBy(opponent)) {
            Player curPlayer = isMaximizingPlayer ? this:opponent;
            return evalBoard(board, curPlayer);
        }

        if (isMaximizingPlayer) {
            return getMax(board, currentDepth, alpha, beta);
        } else {
            return getMin(board, currentDepth, alpha, beta);
        }
    }

    /**
     * Play the move with the highest score.
     *
     * @param board      the board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private float getMax (Board board, int currentDepth, float alpha, float beta) {
        float bestScore = -Float.MAX_VALUE;
        List<Board.Place> bestMoves = new ArrayList<>();
        //List<Board.Place> emptyCells = getEmptyCellsIndexes(board);
        Set<Board.Place> adjacentCells = getAdjacentIndexes(board);

        for (Board.Place position : adjacentCells) {

            board.placeStone(position.x, position.y, this);
            float score = miniMax(false, board, currentDepth-1, alpha, beta);
            board.placeStone(position.x, position.y, null);

            if (score >= bestScore) {
                if(score == bestScore) {
                    bestMoves.add(position);
                } else {
                    bestMoves.clear();
                    bestMoves.add(position);
                }
                bestScore = score;
            }

            alpha = Math.max(alpha,score);

            if(beta<=alpha) { // change to <= to improve performance, keep it as < so AI has variety of moves
                bestScore=1;
                break;
            }

        }

        if(currentDepth == maxDepth) {
            int n = random.nextInt(bestMoves.size());
            System.out.println(n);
            //int n = 0;
            return bestMoves.get(n).x * board.size() + bestMoves.get(n).y; // returns the best move found
        } else {
            return bestScore;
        }
    }

    /**
     * Play the move with the lowest score.
     * @param board         The Omok board to play on
     * @param currentDepth  The current depth
     * @return              The score of the board
     */
    private float getMin (Board board, int currentDepth, float alpha, float beta) {
        float bestScore = Float.MAX_VALUE;
        //List<Board.Place> emptyCells = getEmptyCellsIndexes(board);
        Set<Board.Place> adjacentCells = getAdjacentIndexes(board);

        for (Board.Place position : adjacentCells) {

            board.placeStone(position.x, position.y, opponent);
            float score = miniMax(true, board, currentDepth-1, alpha, beta);
            board.placeStone(position.x, position.y, null);

            bestScore = Math.min(score, bestScore);

            beta = Math.min(beta,score);

            if(beta<=alpha) { // change to <= to improve performance, keep it as < so AI has variety of moves
                bestScore=-1;
                break;
            }
        }

        return bestScore;
    }

    /**
     * Get the score of the board.
     * @param board         The Omok board to play on
     * @return              the evaluation score of the board
     */
    private float evalBoard(Board board, Player curPlayer) {
        if (board.size() == 3) { // score for tic-tac-toe board
            if (board.isWonBy(this)) {
                return 1f;
            } else if (board.isWonBy(opponent)) {
                return -1f;
            } else {
                return 0f;
            }
        }

        // EVALUATION FOR GOMOKU WIP
        return 0f;
    }

    /**
     *
     * @param board The Omok board to play on
     * @return      A {@link Board.Place} list containing the empty positions
     *              on the board.
     */
    public List<Board.Place> getEmptyCellsIndexes(Board board) {
        List<Board.Place> availableCellsList = new ArrayList<>();
        for(int row = 0; row<board.size(); row++) {
            for(int col = 0; col<board.size(); col++) {
                if(board.isEmpty(row,col)) availableCellsList.add(new Board.Place(row,col));
            }
        }
        return availableCellsList;
    }

    private Set<Board.Place> getAdjacentIndexes(Board board) {
        Set<Board.Place> adjacentIndexesSet = new HashSet<>();

        for(Board.Place occupied : board.getOccupiedPositions()) {
            adjacentIndex(board, occupied.x, occupied.y, adjacentIndexesSet);
        }

        return adjacentIndexesSet;
    }

    private void adjacentIndex(Board board, int x, int y, Set<Board.Place> setToAdd) {
        // Size of given 2d array
        int n = board.size();
        int m = board.size();

        // Checking for all the possible adjacent positions
        if (isValidPos(board, x - 1, y - 1, n, m)) {
            setToAdd.add(new Board.Place(x - 1,y - 1));
        }
        if (isValidPos(board, x - 1, y, n, m)) {
            setToAdd.add(new Board.Place(x - 1,y));
        }
        if (isValidPos(board, x - 1, y + 1, n, m)) {
            setToAdd.add(new Board.Place(x - 1,y + 1));
        }
        if (isValidPos(board, x, y - 1, n, m)) {
            setToAdd.add(new Board.Place(x,y - 1));
        }
        if (isValidPos(board, x, y + 1, n, m)) {
            setToAdd.add(new Board.Place(x,y + 1));
        }
        if (isValidPos(board, x + 1, y - 1, n, m)) {
            setToAdd.add(new Board.Place(x + 1,y - 1));
        }
        if (isValidPos(board, x + 1, y, n, m)) {
            setToAdd.add(new Board.Place(x + 1,y));
        }
        if (isValidPos(board, x + 1, y + 1, n, m)) {
            setToAdd.add(new Board.Place(x + 1,y + 1));
        }
    }

    public static boolean isValidPos(Board board, int x, int y, int rowSize, int colSize) {
        return x >= 0 && y >= 0 && x <= rowSize - 1 && y <= colSize - 1 && board.isEmpty(x,y);
    }

    private int[] makeRandomMove(Board board) {
        Set<Board.Place> moveSet = new HashSet<>();
        adjacentIndex(board, (board.size()-1)/2, (board.size()-1)/2, moveSet);
        if(board.isEmpty((board.size()-1)/2,(board.size()-1)/2))
            return new int[]{(board.size()-1)/2,(board.size()-1)/2};

        int n = random.nextInt(moveSet.size());

        int i =0;
        for(Board.Place move : moveSet) {
            if(i==n) return new int[]{move.x, move.y};
            i++;
        }

        Board.Place move = moveSet.iterator().next();
        return new int[]{move.x, move.y};
    }

}