import java.util.Random;

public class Computer extends Player {
    private final String name;
    private final char playerPiece;
    private int playerType;

    // Constructor
    public Computer(int playerType, String name, char playerPiece) {
        super(name,playerPiece);
        setPlayerType(playerType);
        this.name = name;
        this.playerPiece = playerPiece;
    }

    // Getter
    public String getName() {
        return name;
    }

    public char getPlayerPiece() {
        return playerPiece;
    }

    public int getPlayerType() {
        return playerType;
    }

    // Methods (set player to human/computer)
    private void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public String makeMove(Board board) {
        int[] moves = makeMovePlayerComputer(board);
        String s = Integer.toString(moves[0], board.size()+1) + " " + Integer.toString(moves[1], board.size()+1);
        System.out.println("Computer move: "+s);
        return s;
    }

    public int[] makeMovePlayerComputer(Board board) {
        // Check if the computer can win in the next move
        int[] winningMove = findWinningMove('O', board);
        if (winningMove != null) {
            return winningMove;
        }

        // Check if the human can win in the next move and block them
        int[] blockingMove = findWinningMove('X', board);
        if (blockingMove != null) {
            return blockingMove;
        }

        // If no immediate winning or blocking move, make a random move
        Random random = new Random();
        int x = random.nextInt(board.size())+1;
        int y = random.nextInt(board.size())+1;
        return new int[]{x,y};
    }

    private int[] findWinningMove(char playerSymbol, Board board) {
        // Check rows, columns, and diagonals for winning moves
//        for (int i = 0; i < board.size(); i++) {
//            for (int j = 0; j < board.size(); j++) {
//                if (board.getBoard()[i][j] == '•') {
//                    // Check if placing a piece at this position results in a win
//                    if (checkWinningMove(j, i, playerSymbol, board)) {
//                        return new int[]{j+1, i+1};
//                    }
//                }
//            }
//        }
        return null; // No winning move found
    }

    private boolean checkWinningMove(int row, int col, char playerSymbol, Board board) {
        OmokGame game = new OmokGame(board);
        //board.getBoard()[col][row] = playerSymbol;

        //boolean hasWinningMove = playerSymbol == game.checkForWinner();

        //board.getBoard()[col][row] = '•';

        //return hasWinningMove;
        return false;
    }
}