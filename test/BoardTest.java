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
        assertThrows(NegativeArraySizeException.class, () -> new Board(Integer.MIN_VALUE).size());
    }

    @Test
    void testSize5() {
        assertThrows(NegativeArraySizeException.class, () -> new Board(-1).size());
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
    void testPlaceStone1() { // board coordinates should be player
        int x = 0;
        int y = 0;
        board.placeStone(x,y,player);
        assertSame(board.board[x][y], player);
    }

    @Test
    void testPlaceStone2() { // player move should be its position in the board
        int x = board.board.length-1;
        int y = board.board.length-1;
        board.placeStone(x,y,player);
        assertSame(board.board[x][y], player);
    }

    @Test
    void testPlaceStone3() { // throw error if move is out of bounds
        int x = board.board.length;
        int y = board.board.length;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new Board().placeStone(x,y,player));
    }

    /* test isEmpty() */
    @Test
    void testIsEmpty1() { // check empty position
        assertTrue(board.isEmpty(0,0));
    }

    @Test
    void testIsEmpty2() { // check empty position at max board length
        int x = board.size()-1;
        int y = board.size()-1;
        assertTrue(board.isEmpty(x,y));
    }
    @Test
    void testIsEmpty3() { // check occupied position at beginning index
        board.placeStone(0,0,player);
        assertFalse(board.isEmpty(0,0));
    }

    @Test
    void testIsEmpty4() { // check occupied position at max index
        int x = board.size()-1;
        int y = board.size()-1;
        board.placeStone(x,y,player);
        assertFalse(board.isEmpty(x,y));
    }

    @Test
    void testIsEmpty5() { // check when using index that is out of range
        int x = board.size();
        int y = board.size();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.isEmpty(x,y));
    }

    /* Test isOccupied() */
    @Test
    void testIsOccupied1() { // test when index is not occupied
        assertFalse(board.isOccupied(0,0));
    }

    @Test
    void testIsOccupied2() { // test when max index is not occupied
        int x = board.size()-1;
        int y = board.size()-1;
        assertFalse(board.isOccupied(x,y));
    }

    @Test
    void testIsOccupied3() { // test when origin index is occupied
        board.board[0][0]=player;
        assertTrue(board.isOccupied(0,0));
    }

    @Test
    void testIsOccupied4() { // test when max index is occupied
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y]=player;
        assertTrue(board.isOccupied(x,y));
    }

    @Test
    void testIsOccupied5() { // test when index is out of bounds
        int x = board.size();
        int y = board.size();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.isOccupied(x,y));
    }

    /* Test isOccupiedBy() */
    @Test
    void isOccupiedBy1() { // test when no player occupies position
        assertFalse(board.isOccupiedBy(0, 0, player));
    }

    @Test
    void isOccupiedBy2() { // test player occupies position
        board.board[0][0] = player;
        assertTrue(board.isOccupiedBy(0, 0, player));
    }

    @Test
    void isOccupiedBy3() { // test when different players occupies position
        board.board[0][0] = new Player("",' ');
        assertFalse(board.isOccupiedBy(0, 0, player));
    }

    @Test
    void isOccupiedBy4() { // test when new same player occupies position
        Player newplayer = new Player("",' ');
        board.board[0][0] = newplayer;
        assertTrue(board.isOccupiedBy(0, 0, newplayer));
    }

    @Test
    void isOccupiedBy5() { // test when new player occupies position of player
        Player newplayer = new Player("",' ');
        board.board[0][0] = player;
        assertFalse(board.isOccupiedBy(0, 0, newplayer));
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