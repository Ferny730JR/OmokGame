import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
public class ConsoleUI {
    Board board;
    Scanner scanner = new Scanner(System.in);

    /**
     * Create a new ConsoleUI with the specified board
     *
     */
    public ConsoleUI(Board board) {
        this.board = board;
    }

    /**
     * Print the specified board to the console.
     */
    public void displayBoard(Board board) {
        // Clear console
        System.out.println("\033[H\033[2J");

        // Print Top row
        printTopRow(board.board);

        // Print board
        printBoard(board.board);

        // Print bottom row
        printBottomRow(board.board);
    }

    /**
     * Prints the specified string to the console
     */
    public void displayMessage(String msg) {
        System.out.printf(msg);
    }

    /**
     * Prompts the user to select a specified game mode.
     * The game mode can be either human vs. human, or
     * human vs. computer.
     *
     * @return The selected game mode. 1 is human vs. human,
     * 2 is human vs. computer
     */
    public int selectGameMode() {
        displayMessage("Enter 1 to play against human, or 2 to play against computer: ");

        int userInput=0;
        while(userInput != 1 && userInput !=2 && userInput !=3) {
            try {
                userInput = scanner.nextInt();

                if (userInput == 1 || userInput == 2) {
                    System.out.println("You entered: " + userInput);
                } else {
                    System.out.print("Enter 1 or 2: ");
                }
                scanner.nextLine();
            } catch (Exception e) {
                displayMessage("Enter 1 or 2: ");
                scanner.nextLine();
            }
        }
        return userInput;
    }

    /**
     * Prompts the player to select their next move for
     * the board. If the player is a computer, determine
     * the move using a minimax algorithm.
     *
     * @return The player's move.
     */
    public String selectNextMove(Player player) {
        System.out.printf("%s's turn!\n",player.getName());
        System.out.print("Select your move (x y): ");
        return scanner.nextLine();
    }


    /** Underlines the given text.
     */
    private static void underlineText(String text) {
        // Print an underline for each character in the text
        for (int i = 0; i < text.length(); i++) {
            System.out.print('\u0332'); // Unicode underline character
            System.out.print(text.charAt(i));
        }
    }

    private static void printTopRow(Player[][] board) {
        System.out.print("Y ");
        StringBuilder top_row = new StringBuilder();
        for(int i=0; i< board.length*3; i++) {
            if(i<(board.length*3)/2-3 || i>(board.length*3)/2+3) {
                top_row.append(" ");
            } else if(i == (board.length*3)/2) {
                top_row.append("Omok Game");
            }
        }
        underlineText(top_row.toString());
        System.out.println();
    }

    private static void printBoard(Player[][] board) {
        for(int i=1; i<board.length+1; i++) {
            if(i<10) { System.out.printf("%1s│ ",i); }
            else     { System.out.printf("%1s│ ",(char)(i+55)); }

            for(Player player : board[i-1]) {
                if(player == null) {
                    System.out.printf(" %s ", '•');
                } else {
                    System.out.printf(" %s ", player.getStone());
                }
            }
            System.out.println(" │");
        }
    }

    private static void printBottomRow(Player[][] board) {
        StringBuilder bottom_row = new StringBuilder();
        for(int i=1; i<board.length+1; i++) {
            bottom_row.append(" ").append(Integer.toString(i,board.length+1).toUpperCase()).append(" ");
        }
        System.out.printf(" ╵ %s ╵X\n",bottom_row);
    }
}
