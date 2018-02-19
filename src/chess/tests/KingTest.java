package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class KingTest extends TestCase{
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new King(board,WHITE,0,4);
		board.movePiece(board,0,4,8,8,WHITE);
		assertEquals(board.getPiece(0, 4).getClass().toString().substring(12),"King");
		board.movePiece(board,0,4,0,4,WHITE);
		assertEquals(board.getPiece(0, 4).getClass().toString().substring(12),"King");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new King(board,WHITE,0,4);
		board.movePiece(board,0,4,1,4,WHITE);
		assertNull(board.getPiece(0,4));
		assertEquals(board.getPiece(1, 4).getClass().toString().substring(12),"King");
	}
	
	/**
	 * test a legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new King(board,WHITE,1,4);
		new Knight(board,WHITE,1,5);
		board.movePiece(board,1,4,1,5,WHITE);
		assertEquals(board.getPiece(1, 4).getClass().toString().substring(12),"King");
		assertEquals(board.getPiece(1, 5).getClass().toString().substring(12),"Knight");

	}
	
	/**
	 * test a legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		new Knight(board,BLACK,6,5);
		board.movePiece(board,7,4,6,5,WHITE);
		assertNull(board.getPiece(7,4));
		assertEquals(board.getPiece(6, 5).getClass().toString().substring(12),"King");
		assertEquals(board.getCamp(BLACK).size(),1);
		assertEquals(board.getCamp(WHITE).size(),1);

	}
	
	/**
	 * test king cannot move more than one square
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new King(board,WHITE,0,4);
		board.movePiece(board,0,4,6,6,WHITE);
		assertNull(board.getPiece(6,6));
		assertEquals(board.getPiece(0, 4).getClass().toString().substring(12),"King");
	}

}
