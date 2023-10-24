public class OmokGame {
    Board board;

    // Constructor
    public OmokGame(Board board) {
        this.board = board;
    }

    // OmokGame methods
    public boolean gameOver() {
        boolean gameState;

        // If there is no winner, continue game
        gameState = '•' != checkForWinner();
        if(gameState) return gameState;

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
        board.placeStone(move[0],move[1],player);
    }

    public char checkForWinner() {
//        int boardSize = board.boardSize;
//
//        // Check rows
//        for (int row = 0; row < boardSize; row++) {
//            for (int col = 0; col <= boardSize - 5; col++) {
//                char current = board.getBoard()[row][col];
//                if (current != '•' &&
//                        current == board.getBoard()[row][col + 1] &&
//                        current == board.getBoard()[row][col + 2] &&
//                        current == board.getBoard()[row][col + 3] &&
//                        current == board.getBoard()[row][col + 4]) {
//                    return current;
//                }
//            }
//        }
//
//        // Check columns
//        for (int col = 0; col < boardSize; col++) {
//            for (int row = 0; row <= boardSize - 5; row++) {
//                char current = board.getBoard()[row][col];
//                if (current != '•' &&
//                        current == board.getBoard()[row + 1][col] &&
//                        current == board.getBoard()[row + 2][col] &&
//                        current == board.getBoard()[row + 3][col]
//                        && current == board.getBoard()[row + 4][col]) {
//                    return current;
//                }
//            }
//        }
//
//        // Check diagonals
//        for (int row = 0; row <= boardSize - 5; row++) {
//            for (int col = 0; col <= boardSize - 5; col++) {
//                char current = board.getBoard()[row][col];
//                if (current != '•' &&
//                        current == board.getBoard()[row + 1][col + 1] &&
//                        current == board.getBoard()[row + 2][col + 2] &&
//                        current == board.getBoard()[row + 3][col + 3] &&
//                        current == board.getBoard()[row + 4][col + 4]) {
//                    return current;
//                }
//                current = board.getBoard()[row + 4][col];
//                if (current != '•' &&
//                        current == board.getBoard()[row + 3][col + 1] &&
//                        current == board.getBoard()[row + 2][col + 2] &&
//                        current == board.getBoard()[row + 1][col + 3] &&
//                        current == board.getBoard()[row][col + 4]) {
//                    return current;
//                }
//            }
//        }
//
//        // No winner found
        return '•';
    }

    // Helper method
    public int[] stringToInt(String userInput) throws ArrayIndexOutOfBoundsException {
        String[] move = userInput.split(" ");
        return new int[]{Integer.parseInt(move[0], board.size()+1),
                         Integer.parseInt(move[1], board.size()+1)};
    }
}

