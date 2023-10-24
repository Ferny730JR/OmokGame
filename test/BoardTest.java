import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

//    @BeforeEach
//    void setUp() {
//        board = new Board();
//    }

    /* Test size() method */
    @Test
    void testSize1() {
        Board board = new Board(0);
        assertEquals(0,board.size());
    }

//    @Test
//    void testSize2() {
//        Board board = new Board(Integer.MAX_VALUE);
//        assertEquals(Integer.MAX_VALUE,board.size());
//    }

    @Test
    void testSize3() {
        Board board = new Board(0);
        assertEquals(0,board.size());
    }

//    @Test
//    void testSize4() {
//        Board board = new Board(Integer.MIN_VALUE);
//        assertEquals(Integer.MIN_VALUE,board.size());
//    }

    @Test
    void testSize5() {
        assertThrows(NegativeArraySizeException.class, () -> {
            new Board(-1).size();
        });
    }

    @Test
    void testSize6() {
        Board board = new Board(70/3);
        assertEquals(23,board.size());
    }

    /* Test clear() */
    @Test
    void testClear() {
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