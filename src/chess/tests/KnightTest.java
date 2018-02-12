package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class KnightTest extends TestCase {

	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Knight(board,WHITE,0,1);
		board.movePiece(board,0,1,8,8,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Knight");
		board.movePiece(board,0,1,0,1,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Knight");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Knight(board,WHITE,3,3);
		//VHH, ULL
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Knight");
		//VHH, URR
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Knight");
		//VHH, DLL
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Knight");
		//VHH, DRR
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Knight");
		//VVH, UUL
		board.movePiece(board,3,3,5,2,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Knight");
		//VVH, DDL
		board.movePiece(board,5,2,3,1,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Knight");
		//VVH, UUR
		board.movePiece(board,3,1,5,2,WHITE);
		assertNull(board.getPiece(3,1));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Knight");
		//VVH, DDR
		board.movePiece(board,5,2,3,3,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Knight");
	}
	
	/**
	 * test legal moves to empty space, there're pieces in the path between
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Knight(board,WHITE,3,3);
		//VHH, ULL
		new Pawn(board,WHITE,4,3);
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Knight");
		//VHH, URR
		new Pawn(board,WHITE,5,1);
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Knight");
		//VHH, DLL
		new Pawn(board,BLACK,4,2);
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Knight");
		//VHH, DRR
		new Pawn(board,BLACK,3,2);
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Knight");
	}
	
	/**
	 * test Knight cannot move straightly or diagonally
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Knight(board,WHITE,3,3);
		board.movePiece(board,3,3,5,5,WHITE);
		assertNull(board.getPiece(5,5));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Knight");
		board.movePiece(board,3,3,3,0,WHITE);
		assertNull(board.getPiece(3,0));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Knight");
		
	}
}
