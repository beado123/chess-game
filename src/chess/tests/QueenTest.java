package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class QueenTest extends TestCase {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Queen(board,WHITE,0,3);
		board.movePiece(board,0,3,8,8,WHITE);
		assertEquals(board.getPiece(0, 3).getClass().toString().substring(12),"Queen");
		//no move
		board.movePiece(board,0,3,0,3,WHITE);
		assertEquals(board.getPiece(0, 3).getClass().toString().substring(12),"Queen");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Queen(board,WHITE,3,3);
		//move diagonally
		board.movePiece(board,3,3,2,4,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(2, 4).getClass().toString().substring(12),"Queen");
		//move along rank
		board.movePiece(board,2,4,1,4,WHITE);
		assertEquals(board.getPiece(1, 4).getClass().toString().substring(12),"Queen");
	}
	
	/**
	 * test legal moves to empty space, there're pieces in the way
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Queen(board,WHITE,2,2);
		new Pawn(board,BLACK,2,3);
		//move along file
		board.movePiece(board,2,2,2,6,WHITE);
		assertNull(board.getPiece(2,6));
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Queen");
		//move diagonally
		new Rook(board,WHITE,3,3);
		board.movePiece(board,2,2,4,4,WHITE);
		assertNull(board.getPiece(4,4));
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Queen");
	}
	
	/**
	 * test a legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new Queen(board,WHITE,2,2);
		new Pawn(board,WHITE,1,3);
		board.movePiece(board,2,2,1,3,WHITE);
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Queen");
		assertEquals(board.getPiece(1, 3).getClass().toString().substring(12),"Pawn");
	}
	
	/**
	 * test a legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		new Queen(board,WHITE,3,3);
		new Pawn(board,BLACK,4,2);
		board.movePiece(board,3,3,4,2,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 2).getClass().toString().substring(12),"Queen");
		assertEquals(board.getCamp(BLACK).size(),1);
		assertEquals(board.getCamp(WHITE).size(),2);
	}
	
	/**
	 * test Queen cannot move in ways other than going straightly or diagonally
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Queen(board,WHITE,3,3);
		board.movePiece(board,3,3,5,6,WHITE);
		assertNull(board.getPiece(5,6));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Queen");
		
	}

}
