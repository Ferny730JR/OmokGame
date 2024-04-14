package omok;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Computer extends Player {
    private Player opponent;
    private final int maxDepth;
    private final Random random = new Random();
    private final HashMap<String, Float> scoreFromPosition = new HashMap<>();

    /**
     * Constructor
     */
    public Computer(String name, char playerPiece, int maxDepth) {
        super(name,playerPiece);
        this.maxDepth = maxDepth;
        initScoreFromPosition();
    }

    public Computer(String name, Color color, int maxDepth) {
        super(name, color);
        this.maxDepth = maxDepth;
        initScoreFromPosition();
    }

    private void initScoreFromPosition() {
        // keys: x,y:score. x represents consecutive stones. y represents open ends
        scoreFromPosition.put("11111",   30000000f);
        scoreFromPosition.put("22222",  -30000000f);
        scoreFromPosition.put("011110",  20000000f);
        scoreFromPosition.put("022220", -20000000f);
        scoreFromPosition.put("011112",  50000f);
        scoreFromPosition.put("211110",  50000f);
        scoreFromPosition.put("022221", -50000f);
        scoreFromPosition.put("122220", -50000f);
        scoreFromPosition.put("01110",   30000f);
        scoreFromPosition.put("02220",  -30000f);
        scoreFromPosition.put("011010",  15000f);
        scoreFromPosition.put("010110",  15000f);
        scoreFromPosition.put("022020",  -15000f);
        scoreFromPosition.put("020220",  -15000f);
        scoreFromPosition.put("001112",  2000f);
        scoreFromPosition.put("211100",  2000f);
        scoreFromPosition.put("002221",  -2000f);
        scoreFromPosition.put("122200",  -2000f);
        scoreFromPosition.put("211010",  2000f);
        scoreFromPosition.put("210110",  2000f);
        scoreFromPosition.put("010112",  2000f);
        scoreFromPosition.put("011012",  2000f);
        scoreFromPosition.put("122020",  -2000f);
        scoreFromPosition.put("120220",  -2000f);
        scoreFromPosition.put("020221",  -2000f);
        scoreFromPosition.put("022021",  -2000f);
        scoreFromPosition.put("01100",   500f);
        scoreFromPosition.put("00110",   500f);
        scoreFromPosition.put("02200",   -500f);
        scoreFromPosition.put("00220",   -500f);
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
        } else if(board.size() > 3 && board.getOccupiedPositions().size()<3) {
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
        float score = evalBoard(board);
        if (currentDepth == 0 || board.isFull() || board.isWonBy(this) || board.isWonBy(opponent) || score>=20000000 || score<=-20000000) {
            //return evalBoard(board);
            return score;
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
                bestScore=30000000f;
                break;
            }

        }

        if(currentDepth == maxDepth) {
            int n = random.nextInt(bestMoves.size());
            System.out.printf("Best Score: %s, n: %s\n",bestScore, n);
            for(Board.Place move : bestMoves)
                System.out.printf("x: %s, y: %s\n",move.x, move.y);
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
                bestScore=-30000000f;
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
    private float evalBoard(Board board) {
        if (board.size() == 3) { // score for tic-tac-toe board
            if (board.isWonBy(this)) {
                return 1f;
            } else if (board.isWonBy(opponent)) {
                return -1f;
            } else {
                return 0f;
            }
        }

        // EVALUATION FOR GOMOKU
        List<String> state = boardToString(board.getBoard());

        float score = 0;
        for(String boardPos : state) {
            for(int i=0; i<boardPos.length()-5; i++) {
                String subState = boardPos.substring(i,i+5);
                if(scoreFromPosition.containsKey(subState)) {
                    score += scoreFromPosition.get(subState);
                }
            }
            for(int i=0; i<boardPos.length()-6; i++) {
                String subState = boardPos.substring(i,i+6);
                if(scoreFromPosition.containsKey(subState)) {
                    score += scoreFromPosition.get(subState);
                }
            }
        }

        return score;
    }

    /**
     * Return a String representation of the board. The string is represented
     * using "0" for a null value, "1" for Computer, and "2" for the opponent.
     *
     * @param board
     * @return
     */
    private List<String> boardToString(Player[][] board) {
        List<String> cList = new ArrayList<>(), rList = new ArrayList<>(), dList = new ArrayList<>();

        for(Player[] row : board) {
            StringBuilder rowVals = new StringBuilder();
            for(Player player : row) {
                if(player == this) rowVals.append("1");
                else if(player == opponent) rowVals.append("2");
                else rowVals.append("0");
            }
            rList.add(rowVals.toString());
        }

        for(int col=0; col<board.length; col++) {
            StringBuilder colVals = new StringBuilder();
            for(int row=0; row<board[0].length; row++) {
                if(board[row][col] == this) colVals.append("1");
                else if(board[row][col] == opponent) colVals.append("2");
                else colVals.append("0");
            }
            cList.add(colVals.toString());
        }

        StringBuilder diagVals;
        for( int k = 0 ; k < board.length * 2 ; k++ ) {
            diagVals = new StringBuilder();
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < board.length && j < board.length ) {
                    if(board[i][j] == this) diagVals.append("1");
                    else if(board[i][j] == opponent) diagVals.append("2");
                    else diagVals.append("0");
                }
            }
            dList.add(diagVals.toString());
        }

        for (int i = board.length - 1; i > 0; i--) {
            diagVals = new StringBuilder();
            for (int j = 0, x = i; x <= board.length - 1; j++, x++) {
                if(board[i][j] == this) diagVals.append("1");
                else if(board[i][j] == opponent) diagVals.append("2");
                else diagVals.append("0");
            }
            dList.add(diagVals.toString());
        }


        for (int i = 0; i <= board.length - 1; i++) {
            diagVals = new StringBuilder();
            for (int j = 0, y = i; y <= board.length - 1; j++, y++) {
                if(board[j][y] == this) diagVals.append("1");
                else if(board[j][y] == opponent) diagVals.append("2");
                else diagVals.append("0");
            }
            dList.add(diagVals.toString());
        }
        List<String> boardString = new ArrayList<>();
        boardString.addAll(rList);
        boardString.addAll(cList);
        boardString.addAll(dList);
        return boardString;
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