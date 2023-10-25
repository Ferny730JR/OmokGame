import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Player player;
    @BeforeEach
    void setUp() {
        board = new Board();
        player = new Player("",' ');
    }

    /* Test size() method */
    @Test
    void testSize1() {
        Board board = new Board(0);
        assertEquals(0,board.size());
    }

    @Test
    void testSize2() {
        Board board = new Board(0);
        assertEquals(0,board.size());
    }

    @Test
    void testSize3() {
        Board board = new Board(70/3);
        assertEquals(23,board.size());
    }

    @Test
    void testSize4() {
        assertThrows(NegativeArraySizeException.class, () -> {
            new Board(Integer.MIN_VALUE).size();
        });
    }

    @Test
    void testSize5() {
        assertThrows(NegativeArraySizeException.class, () -> {
            new Board(-1).size();
        });
    }

    /* Test clear() */
    @Test
    void testClear1() {
        board.clear(); // clear on empty board
        assertTrue(Arrays.deepEquals(new Player[15][15],board.board));
    }

    @Test
    void testClear2() {
        // board has one stone
        board.placeStone(0,0,new Player("",' '));
        // Clear board
        board.clear();
        assertTrue(Arrays.deepEquals(new Player[15][15],board.board));
    }

    @Test
    void testClear3() {
        // board is full
        for(Player[] row : board.board) {
            Arrays.fill(row,player);
        }
        // Clear board
        board.clear();
        assertTrue(Arrays.deepEquals(new Player[15][15],board.board));
    }

    @Test
    void testClear4() {
        board = new Board(1); // full board of size 1
        board.placeStone(0,0,new Player("",' '));
        board.clear(); // Clear board
        assertTrue(Arrays.deepEquals(new Player[1][1],board.board));
    }

    @Test
    void testClear5() {
        // large omok board is full
        board = new Board(10000);
        for(Player[] row : board.board) {
            Arrays.fill(row,player);
        }
        board.clear();
        assertTrue(Arrays.deepEquals(new Player[10000][10000],board.board));
    }

    /* Test isFull() */
    @Test
    void testIsFull1() { // board is full
        for(Player[] row : board.board) {
            Arrays.fill(row, player);
        }
        assertTrue(board.isFull());
    }

    @Test
    void testIsFull2() { // test when entire board is empty
        assertFalse(board.isFull());
    }

    @Test
    void testIsFull3() { // Only one spot in board is empty
        for(Player[] row : board.board) {
            Arrays.fill(row, player);
        }
        board.board[14][14]=null;
        assertFalse(board.isFull());
    }

    @Test
    void testIsFull4() { // test full board of size 10,000
        board = new Board(10_000);
        for(Player[] row : board.board) {
            Arrays.fill(row, player);
        }

        assertTrue(board.isFull());
    }

    @Test
    void testIsFull5() { // test board of size 10,000 with only one empty spot
        board = new Board(10_000);
        for(Player[] row : board.board) {
            Arrays.fill(row, player);
        }
        board.board[board.board.length-1][board.board.length-1]=null;

        assertFalse(board.isFull());
    }

    /* test placeStone() */
    @Test
    void testPlaceStone1() {

    }

    @Test
    void testPlaceStone2() {
    }

    @Test
    void testPlaceStone3() {
    }

    /* test isEmpty() */
    @Test
    void testIsEmpty() {
    }

    /* Test isOccupied() */
    @Test
    void testIsOccupied() {
    }

    /* Test isOccupiedBy() */
    @Test
    void isOccupiedBy() {
    }

    /* Test playerAt() */
    @Test
    void testPlayerAt() {
    }

    /* test isWonBy() */
    @Test
    void testIsWonBy() {
    }

    /* test winningRow() */
    @Test
    void testWinningRow() {
    }
}