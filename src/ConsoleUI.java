import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
public class ConsoleUI {
    Board board;
    InputStream in;
    PrintStream out;
    Scanner scanner = new Scanner(System.in);

    // Constructor
    public ConsoleUI(Board board) {
        this(board,System.in,System.out);
    }

    public ConsoleUI(Board board, InputStream in, PrintStream out) {
        this.board = board;
        this.in = in;
        this.out = out;
    }

    // Methods
    public void displayBoard(char[][] board) {
        // Clear console
        System.out.println("\033[H\033[2J");

        // Print Top row
        printTopRow(board);

        // Print board
        printBoard(board);

        // Print bottom row
        printBottomRow(board);
    }

    public void displayMessage(String msg) {
        System.out.printf(msg);
    }

    public int selectGameMode() {
        displayMessage("Enter 1 to play against human, or 2 to play against computer: ");

        int userInput=0;
        while(userInput != 1 && userInput !=2 ) {
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

    public String selectNextMove(Player player) {
        if(player.getPlayerType() == 2)
            return player.makeMove(board);
        System.out.printf("%s's turn!\n",player.getPlayerName());
        System.out.print("Select your move (x y): ");
        return scanner.nextLine();
    }



    // Helper methods
    private static void underlineText(String text) {
        // Print an underline for each character in the text
        for (int i = 0; i < text.length(); i++) {
            System.out.print('\u0332'); // Unicode underline character
            System.out.print(text.charAt(i));
        }
    }

    private static void printTopRow(char[][] board) {
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

    private static void printBoard(char[][] board) {
        for(int i=1; i<board.length+1; i++) {
            if(i<10) { System.out.printf("%1s│ ",i); }
            else     { System.out.printf("%1s│ ",(char)(i+55)); }

            for(char move : board[i-1]) {
                System.out.printf(" %s ",move);
            }
            System.out.println(" │");
        }
    }

    private static void printBottomRow(char[][] board) {
        StringBuilder bottom_row = new StringBuilder();
        for(int i=1; i<board.length+1; i++) {
            bottom_row.append(" ").append(Integer.toString(i,board.length+1).toUpperCase()).append(" ");
        }
        System.out.printf(" ╵ %s ╵X\n",bottom_row);
    }
}
