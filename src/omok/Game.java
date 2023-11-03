package omok;

import java.util.List;

public class Game {
    Board board;
    List<Player> players;

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
            move = parseMove(userInput);
        } catch (Exception e) {
            System.out.println("Enter a valid move!");
            return false;
        }

        if(move[0] > board.size() || move[1] > board.size()) {
            System.out.println("Enter a valid move!");
            return false;
        } else {
            return board.isEmpty(move[0],move[1]);
        }
    }

    public void makeMove(Player player, String userInput) {
        int[] move = parseMove(userInput);
        board.placeStone(move[0],move[1],player);
    }

    // Helper method
    public int[] parseMove(String userInput) throws ArrayIndexOutOfBoundsException {
        String splitRegex = "[ ,]";
        String[] move = userInput.split(splitRegex);
        return new int[]{Integer.parseInt(move[1], board.size()+1)-1,
                         Integer.parseInt(move[0], board.size()+1)-1};
    }
}

