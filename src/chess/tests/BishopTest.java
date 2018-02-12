package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class BishopTest extends TestCase {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Bishop(board,WHITE,0,2);
		board.movePiece(board,0,2,8,8,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Bishop");
		board.movePiece(board,0,2,0,2,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Bishop");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Bishop(board,WHITE,0,2);
		board.movePiece(board,0,2,2,4,WHITE);
		assertNull(board.getPiece(0,2));
		assertEquals(board.getPiece(2, 4).getClass().toString().substring(12),"Bishop");
		board.movePiece(board,2,4,0,2,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Bishop");

	}
	
	/**
	 * test legal moves to empty space, cannot leap over other pieces
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Bishop(board,WHITE,0,2);
		new Pawn(board,BLACK,1,3);
		board.movePiece(board,0,2,2,4,WHITE);
		assertNull(board.getPiece(2,4));
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Bishop");
		
	}
	
	/**
	 * test a legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new Bishop(board,WHITE,0,2);
		new Pawn(board,WHITE,1,3);
		board.movePiece(board,0,2,1,3,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Bishop");
		assertEquals(board.getPiece(1, 3).getClass().toString().substring(12),"Pawn");

	}
	
	/**
	 * test a legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new Bishop(board,WHITE,3,3);
		new Pawn(board,BLACK,1,1);
		board.movePiece(board,3,3,1,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(1, 1).getClass().toString().substring(12),"Bishop");
		assertEquals(board.getCamp(BLACK).size(),0);
		assertEquals(board.getCamp(WHITE).size(),1);
		new Rook(board,BLACK,2,2);
		board.movePiece(board,1,1,2,2,WHITE);
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Bishop");

	}
	
	/**
	 * test Bishop cannot move straightly
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Bishop(board,WHITE,3,3);
		//right move
		board.movePiece(board,3,3,3,4,WHITE);
		assertNull(board.getPiece(3,4));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Bishop");
		//left move
		board.movePiece(board,3,3,3,2,WHITE);
		assertNull(board.getPiece(3,2));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Bishop");
		//move up
		board.movePiece(board,3,3,5,3,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Bishop");
		//move down
		board.movePiece(board,3,3,0,3,WHITE);
		assertNull(board.getPiece(0,3));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Bishop");
	}

}
