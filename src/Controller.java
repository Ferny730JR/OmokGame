import java.util.LinkedList;
import java.util.Queue;

public class Controller {
    OmokGame game;
    ConsoleUI omokConsole;
    Board board;

    Player player1, player2, currentPlayer;
    Queue<Player> playerQueue = new LinkedList<>();

    int gameType;
    public Controller() {}

    public void run() {
        init();
        startGame();
    }

    public void init() {
        // Creates game, board, and Console UI
        board = new Board(15);
        game = new OmokGame(board);
        omokConsole = new ConsoleUI(board);

        // Set the game type
        gameType = omokConsole.selectGameMode();
        if(gameType == 1) { // Human vs. Human
            player1 = new Player(1, "player1", 'X');
            player2 = new Player(1, "player2", 'O');
        } else { // Human vs. Computer
            player1 = new Player(1, "player1", 'X');
            player2 = new Player(2, "computer1", 'O');
        }

        // Create the players
        playerQueue.offer(player1);
        playerQueue.offer(player2);
    }

    public void startGame() {
        while(!game.gameOver()) {
            // Refresh the console UI
            omokConsole.displayBoard(board.getBoard());

            // dequeue the current player
            currentPlayer = playerQueue.poll();

            // have the current player make their turn
            gameTurn(currentPlayer);

            // enqueue the current player
            playerQueue.offer(currentPlayer);
        }

        if(board.isFull()) {
            omokConsole.displayBoard(board.getBoard());
            omokConsole.displayMessage("It's a tie!");
        } else {
            omokConsole.displayBoard(board.getBoard());
            omokConsole.displayMessage(currentPlayer.getName() + " has won the game!");
        }
    }

    public void gameTurn(Player currentPlayer) {
        // Prompt the player for move
        String move = omokConsole.selectNextMove(currentPlayer);

        // Determine if move is valid; keep prompting for move
        while(!game.moveIsValid(move)) {
            omokConsole.displayMessage("Move is invalid :(\n");
            move = omokConsole.selectNextMove(currentPlayer);
        }

        // Have the player make the move
        game.makeMove(currentPlayer,move);
    }
}
