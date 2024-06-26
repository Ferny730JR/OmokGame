package console;

import omok.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Controller {
    Game game;
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
        omokConsole = new ConsoleUI(board);

        // Set the game type
        gameType = omokConsole.selectGameMode();
        if(gameType == 1) { // Human vs. Human
            player1 = new Player("player1", 'X');
            player2 = new Player("player2", 'O');
        } else if(gameType == 2) { // Human vs. omok.Computer
            player1 = new Player("player1", 'X');
            player2 = new Computer("computer1",'O',3);
            ((Computer) player2 ).setOpponent(player1);
        } else {
            player1 = new Computer("Computer1",'X',3);
            player2 = new Computer("Computer2",'O',3);
            ((Computer) player1).setOpponent(player2);
            ((Computer) player2).setOpponent(player1);

        }
        game = new Game(board, new ArrayList<>(Arrays.asList(player1, player2)));

        // Create the players
        playerQueue.offer(player1);
        playerQueue.offer(player2);
    }

    public void startGame() {
        while(!game.gameOver()) {
            // Refresh the console UI
            omokConsole.displayBoard(board);

            // dequeue the current player
            currentPlayer = playerQueue.poll();

            // have the current player make their turn
            gameTurn(currentPlayer);

            // enqueue the current player
            playerQueue.offer(currentPlayer);
        }

        if(board.isFull()) {
            omokConsole.displayBoard(board);
            omokConsole.displayMessage("It's a tie!");
        } else {
            omokConsole.displayBoard(board);
            omokConsole.displayMessage(currentPlayer.getName() + " has won the game!");
        }

        board.clear();
        startGame();
    }

    public void gameTurn(Player currentPlayer) {
        if(currentPlayer.getClass() == Computer.class) {
            int[] move = ((Computer) currentPlayer).makeMove(board);
            board.placeStone(move[0],move[1],currentPlayer);
            return;
        }

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
