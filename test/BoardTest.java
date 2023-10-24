import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
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
            Arrays.fill(row,new Player("",' '));
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
            Arrays.fill(row,new Player("",' '));
        }
        board.clear();
        assertTrue(Arrays.deepEquals(new Player[10000][10000],board.board));
    }

    /* Test isFull() */
    @Test
    void testIsFull() {
    }

    /* test placeStone() */
    @Test
    void testPlaceStone() {
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