import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    void testPlayerAt1() { // test when there is no player at position
        assertNull(board.playerAt(0,0));
    }

    @Test
    void testPlayerAt2() { // test when player occupies position
        board.board[0][0] = player;
        assertEquals(player,board.playerAt(0,0));
    }

    @Test
    void testPlayerAt3() { // test when new player occupies position
        Player newplayer = new Player("", ' ');
        board.board[0][0] = newplayer;
        assertEquals(newplayer,board.playerAt(0,0));
    }

    @Test
    void testPlayerAt4() { // test when player gets overwritten with new player
        Player newplayer = new Player("",' ');
        board.board[0][0] = player;
        board.board[0][0] = newplayer;
        assertEquals(newplayer,board.playerAt(0,0));
    }

    /* test isWonBy() */
    @Test
    void testIsWonBy1() { // test when player has won in row direction
        board.board[0][0] = board.board[0][1] = board.board[0][2] = board.board[0][3] = board.board[0][4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy2() { // test when player has won in reverse row direction
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x][y-1] = board.board[0][y-2] = board.board[0][y-3] = board.board[0][y-4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy3() { // test when player has won in diagonal direction
        board.board[0][0] = board.board[1][1] = board.board[2][2] = board.board[3][3] = board.board[4][4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy4() { // test when player has won in reverse diagonal direction
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y] = board.board[x-1][y-1] = board.board[x-2][y-2] = board.board[x-3][y-3] = board.board[x-4][y-4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy5() { // test when player has won in inverted diagonal direction
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y+1] = board.board[x-2][y+2] = board.board[x-3][y+3] = board.board[x-4][y+4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy6() { // test when player has won in reverse inverted diagonal direction
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x+1][y+-1] = board.board[x+2][y-2] = board.board[x+3][y-3] = board.board[x+4][y-4] = player;
        assertTrue(board.isWonBy(player));
    }

    @Test
    void testIsWonBy7() { // test when new player has won in row direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[0][1] = board.board[0][2] = board.board[0][3] = board.board[0][4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy8() { // test when new player has won in reverse row direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x][y-1] = board.board[x][y-2] = board.board[x][y-3] = board.board[x][y-4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy9() { // test when new player has won in diagonal direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[1][1] = board.board[2][2] = board.board[3][3] = board.board[4][4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy10() { // test when new player has won in reverse diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y] = board.board[x-1][y-1] = board.board[x-2][y-2] = board.board[x-3][y-3] = board.board[x-4][y-4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy11() { // test when new player has won in inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y+1] = board.board[x-2][y+2] = board.board[x-3][y+3] = board.board[x-4][y+4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy12() { // test when new player has won in reverse inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x+1][y+-1] = board.board[x+2][y-2] = board.board[x+3][y-3] = board.board[x+4][y-4] = newplayer;
        assertTrue(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy13() { // test player when new player has won in row direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[0][1] = board.board[0][2] = board.board[0][3] = board.board[0][4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy14() { // test player when new player has won in reverse row direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x][y-1] = board.board[x][y-2] = board.board[x][y-3] = board.board[x][y-4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy15() { // test player when new player has won in diagonal direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[1][1] = board.board[2][2] = board.board[3][3] = board.board[4][4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy16() { // test player when new player has won in reverse diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y] = board.board[x-1][y-1] = board.board[x-2][y-2] = board.board[x-3][y-3] = board.board[x-4][y-4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy17() { // test when player new player has won in inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y+1] = board.board[x-2][y+2] = board.board[x-3][y+3] = board.board[x-4][y+4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy18() { // test player when new player has won in reverse inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x+1][y+-1] = board.board[x+2][y-2] = board.board[x+3][y-3] = board.board[x+4][y-4] = newplayer;
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy19() { // test newplayer when player has won in row direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[0][1] = board.board[0][2] = board.board[0][3] = board.board[0][4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy20() { // test new player when player has won in reverse row direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x][y-1] = board.board[0][y-2] = board.board[0][y-3] = board.board[0][y-4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy21() { // test new player when player has won in diagonal direction
        Player newplayer = new Player("", ' ');
        board.board[0][0] = board.board[1][1] = board.board[2][2] = board.board[3][3] = board.board[4][4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy22() { // test new player when player has won in reverse diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y] = board.board[x-1][y-1] = board.board[x-2][y-2] = board.board[x-3][y-3] = board.board[x-4][y-4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy23() { // test new player when player has won in inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y+1] = board.board[x-2][y+2] = board.board[x-3][y+3] = board.board[x-4][y+4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy24() { // test new player when player has won in reverse inverted diagonal direction
        Player newplayer = new Player("", ' ');
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x+1][y+-1] = board.board[x+2][y-2] = board.board[x+3][y-3] = board.board[x+4][y-4] = player;
        assertFalse(board.isWonBy(newplayer));
    }

    @Test
    void testIsWonBy25() { // test player has not won game
        assertFalse(board.isWonBy(player));
    }

    @Test
    void testIsWonBy26() { // test when new player has not won game
        Player newplayer = new Player("", ' ');
        assertFalse(board.isWonBy(newplayer));
    }

    /* test winningRow() */
    @Test
    void testWinningRow1() { // No winning row
        assertEquals(new ArrayList<>(),board.winningRow());
    }

    @Test
    void testWinningRow2() { // Test WinningRow in row direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = 0;
        int y = 0;
        board.board[x][y] = board.board[x][y+1] = board.board[x][y+2] = board.board[x][y+3] = board.board[x][y+4] = player;
        winningRow.add(new Board.Place(x,y));
        winningRow.add(new Board.Place(x,y+1));
        winningRow.add(new Board.Place(x,y+2));
        winningRow.add(new Board.Place(x,y+3));
        winningRow.add(new Board.Place(x,y+4));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow3() { // Test WinningRow in reverse row direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x][y-1] = board.board[x][y-2] = board.board[x][y-3] = board.board[x][y-4] = player;
        winningRow.add(new Board.Place(x,y-4));
        winningRow.add(new Board.Place(x,y-3));
        winningRow.add(new Board.Place(x,y-2));
        winningRow.add(new Board.Place(x,y-1));
        winningRow.add(new Board.Place(x,y));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow4() { // Test WinningRow in column direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = 0;
        int y = 0;
        board.board[x][y] = board.board[x+1][y] = board.board[x+2][y] = board.board[x+3][y] = board.board[x+4][y] = player;
        winningRow.add(new Board.Place(x,y));
        winningRow.add(new Board.Place(x+1,y));
        winningRow.add(new Board.Place(x+2,y));
        winningRow.add(new Board.Place(x+3,y));
        winningRow.add(new Board.Place(x+4,y));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow5() { // Test WinningRow in reverse column direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y] = board.board[x-2][y] = board.board[x-3][y] = board.board[x-4][y] = player;
        winningRow.add(new Board.Place(x-4,y));
        winningRow.add(new Board.Place(x-3,y));
        winningRow.add(new Board.Place(x-2,y));
        winningRow.add(new Board.Place(x-1,y));
        winningRow.add(new Board.Place(x,y));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow6() { // Test WinningRow in diagonal direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = 0;
        int y = 0;
        board.board[x][y] = board.board[x+1][y+1] = board.board[x+2][y+2] = board.board[x+3][y+3] = board.board[x+4][y+4] = player;
        winningRow.add(new Board.Place(x,y));
        winningRow.add(new Board.Place(x+1,y+1));
        winningRow.add(new Board.Place(x+2,y+2));
        winningRow.add(new Board.Place(x+3,y+3));
        winningRow.add(new Board.Place(x+4,y+4));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow7() { // Test WinningRow in reverse direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = board.size()-1;
        int y = board.size()-1;
        board.board[x][y] = board.board[x-1][y-1] = board.board[x-2][y-2] = board.board[x-3][y-3] = board.board[x-4][y-4] = player;
        winningRow.add(new Board.Place(x-4,y-4));
        winningRow.add(new Board.Place(x-3,y-3));
        winningRow.add(new Board.Place(x-2,y-2));
        winningRow.add(new Board.Place(x-1,y-1));
        winningRow.add(new Board.Place(x,y));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow8() { // Test WinningRow in inverted diagonal direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = board.size()-1;
        int y = 0;
        board.board[x][y] = board.board[x-1][y+1] = board.board[x-2][y+2] = board.board[x-3][y+3] = board.board[x-4][y+4] = player;
        winningRow.add(new Board.Place(x,y));
        winningRow.add(new Board.Place(x-1,y+1));
        winningRow.add(new Board.Place(x-2,y+2));
        winningRow.add(new Board.Place(x-3,y+3));
        winningRow.add(new Board.Place(x-4,y+4));

        assertEquals(winningRow,board.winningRow());
    }

    @Test
    void testWinningRow9() { // Test WinningRow in reverse inverted diagonal direction
        List<Board.Place> winningRow = new ArrayList<>();
        int x = 0;
        int y = board.size()-1;
        board.board[x][y] = board.board[x+1][y-1] = board.board[x+2][y-2] = board.board[x+3][y-3] = board.board[x+4][y-4] = player;
        winningRow.add(new Board.Place(x+4,y-4));
        winningRow.add(new Board.Place(x+3,y-3));
        winningRow.add(new Board.Place(x+2,y-2));
        winningRow.add(new Board.Place(x+1,y-1));
        winningRow.add(new Board.Place(x,y));

        assertEquals(winningRow,board.winningRow());
    }
}