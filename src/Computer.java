import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer extends Player {
    private final Player opponent;
    private final int maxDepth = 8;
    private long total_calls = 0;
    private final Random random = new Random();

    /**
     * Constructor
     */
    public Computer(String name, char playerPiece, Board board, Player opponent) {
        super(name,playerPiece);
        this.opponent = opponent;
    }

    /**
     * Compute the most optimal move the computer can make based
     * on the current state of the board.
     *
     * @return The computer move.
     */
    public void makeMove(Board board) {
        total_calls = 0;
        Board currentBoardState = board.getDeepCopy();
        float bestMoveInfo = miniMax(true, currentBoardState, maxDepth);
        System.out.println(total_calls);
        board.placeStone(((int)(bestMoveInfo))/ board.size(), ((int)(bestMoveInfo))% board.size(), this);
    }

    /**
     * The meat of the algorithm.
     * @param isMaximizingPlayer If the AI is making its turn
     * @param board         the Tic Tac Toe board to play on
     * @param currentDepth    the current depth
     * @return              the score of the board
     */
    private float miniMax(Boolean isMaximizingPlayer, Board board, int currentDepth) {
        return miniMax(isMaximizingPlayer, board, currentDepth, -Float.MAX_VALUE, Float.MAX_VALUE);
    }

    private float miniMax(Boolean isMaximizingPlayer, Board board, int currentDepth, float alpha, float beta) {
        total_calls+=1;
        if (currentDepth-- == 0 || board.isFull() || board.isWonBy(this) || board.isWonBy(opponent)) {
            return score(board);
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
     * @param board      the Tic Tac Toe board to play on
     * @param currentDepth the current depth
     * @return the score of the board
     */
    private float getMax (Board board, int currentDepth, float alpha, float beta) {
        float bestScore = -Float.MAX_VALUE;
        List<Board.Place> bestMoves = new ArrayList<>();

        for (Board.Place position : getEmptyCellsIndexes(board)) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.placeStone(position.x, position.y, this);

            float score = miniMax(false, modifiedBoard, currentDepth, alpha, beta);

            if (score >= bestScore) {
                if(score == bestScore) {
                    bestMoves.add(position);
                } else {
                    bestMoves.clear();
                    bestMoves.add(position);
                }
                bestScore = score;
            }

            alpha = Math.max(alpha, bestScore);
            if(beta<alpha) break;

        }

        int n = random.nextInt(bestMoves.size());
        board.placeStone(bestMoves.get(n).x, bestMoves.get(n).y, this);

        if(currentDepth == maxDepth-1) {
            return bestMoves.get(n).x * board.size() + bestMoves.get(n).y;
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
        List<Board.Place> bestScores = new ArrayList<>();

        for (Board.Place position : getEmptyCellsIndexes(board)) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.placeStone(position.x, position.y, opponent);

            float score = miniMax(true, modifiedBoard, currentDepth, alpha, beta);

            if (score <= bestScore) {
                if(score == bestScore) {
                    bestScores.add(position);
                } else {
                    bestScores.clear();
                    bestScores.add(position);
                    }
                bestScore = score;
            }

            beta = Math.min(beta, bestScore);
            if(beta<alpha) break;

        }

        int n = random.nextInt(bestScores.size());
        board.placeStone(bestScores.get(n).x, bestScores.get(n).y, opponent);

        if(currentDepth == maxDepth) {
            return bestScores.get(n).x * board.size() + bestScores.get(n).y;
        } else {
            return bestScore;
        }
    }

    /**
     * Get the score of the board.
     * @param board         The Omok board to play on
     * @return              the evaluation score of the board
     */
    private float score(Board board) {
        if (board.isWonBy(this)) {
            return 1f;
        } else if (board.isWonBy(opponent)) {
            return -1f;
        } else if (board.isFull()) {
            return 0f;
        }
        return 0f;
    }

    public List<Board.Place> getEmptyCellsIndexes(Board board) {
        List<Board.Place> availableCellsList = new ArrayList<>();
        for(int row = 0; row<board.size(); row++) {
            for(int col = 0; col<board.size(); col++) {
                if(board.board[row][col] == null) availableCellsList.add(new Board.Place(row,col));
            }
        }
        return availableCellsList;
    }
}