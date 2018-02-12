package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class RookTest extends TestCase {

	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Rook(board,WHITE,0,0);
		board.movePiece(board,0,0,8,8,WHITE);
		assertEquals(board.getPiece(0, 0).getClass().toString().substring(12),"Rook");
		board.movePiece(board,0,0,0,0,WHITE);
		assertEquals(board.getPiece(0, 0).getClass().toString().substring(12),"Rook");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Rook(board,WHITE,3,1);
		//move along rank
		board.movePiece(board,3,1,3,6,WHITE);
		assertNull(board.getPiece(3,1));
		assertEquals(board.getPiece(3, 6).getClass().toString().substring(12),"Rook");
		//move along file
		board.movePiece(board,3,6,4,6,WHITE);
		assertNull(board.getPiece(3,6));
		assertEquals(board.getPiece(4, 6).getClass().toString().substring(12),"Rook");
	}
	
	/**
	 * test Rook's legal moves to empty space, cannot leap over other pieces
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Rook(board,WHITE,3,1);
		new Pawn(board,WHITE,3,2);
		//move along rank
		board.movePiece(board,3,1,3,6,WHITE);
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Rook");
		assertEquals(board.getPiece(3, 2).getClass().toString().substring(12),"Pawn");
		
	}
	
	/**
	 * test legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new Rook(board,WHITE,3,1);
		new Knight(board,WHITE,2,1);
		board.movePiece(board,3,1,2,1,WHITE);
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Rook");
		assertEquals(board.getPiece(2, 1).getClass().toString().substring(12),"Knight");

	}
	
	/**
	 * test legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new Rook(board,WHITE,3,1);
		new Knight(board,BLACK,0,1);
		board.movePiece(board,3,1,0,1,WHITE);
		assertNull(board.getPiece(3,1));
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Rook");
		assertEquals(board.getCamp(BLACK).size(),0);
		assertEquals(board.getCamp(WHITE).size(),1);

	}
	
	/**
	 * test Rook cannot move diagonally
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Rook(board,WHITE,3,1);
		board.movePiece(board,3,1,4,2,WHITE);
		assertNull(board.getPiece(4,2));
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Rook");
	}
}
