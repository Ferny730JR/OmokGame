import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    List<Player> players = new ArrayList<>();

    // Constructor
    public Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
    }

    // OmokGame methods
    public boolean gameOver() {
        boolean gameState;

        // If there is a winner, end game
        for(Player player : players) {
            if(board.isWonBy(player)) return true;
        }

        // if board is full, end the game
        gameState = board.isFull();
        return gameState;
    }

    public boolean moveIsValid(String userInput) {
        int[] move;
        try {
            move = stringToInt(userInput);
        } catch (Exception e) {
            System.out.println("Enter a valid move!");
            return false;
        }

        if(move[0] > board.size() || move[1] > board.size()) {
            System.out.println("Enter a valid move!");
            return false;
        } else {
            //return board.getBoard()[move[1]-1][move[0]-1] == '•';
            return true;
        }
    }

    public void makeMove(Computer player, String userInput) {
        int[] move = stringToInt(userInput);
        //board.updateBoard(player, move[0], move[1]);
        board.placeStone(move[1]-1,move[0]-1,player);
    }

    // Helper method
    public int[] stringToInt(String userInput) throws ArrayIndexOutOfBoundsException {
        String[] move = userInput.split(" ");
        return new int[]{Integer.parseInt(move[0], board.size()+1),
                         Integer.parseInt(move[1], board.size()+1)};
    }
}

