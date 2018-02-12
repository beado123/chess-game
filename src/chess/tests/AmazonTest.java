/**
 * 
 */
package chess.tests;
import chess.*;
import junit.framework.TestCase;


/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class AmazonTest extends TestCase {

	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Amazon(board,WHITE,0,1);
		board.movePiece(board,0,1,8,8,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Amazon");
		board.movePiece(board,0,1,0,1,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Amazon");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Amazon(board,WHITE,3,3);
		//VHH, ULL
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Amazon");
		//VHH, URR
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Amazon");
		//VHH, DLL
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Amazon");
		//VHH, DRR
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Amazon");
		//VVH, UUL
		board.movePiece(board,3,3,5,2,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Amazon");
		//VVH, DDL
		board.movePiece(board,5,2,3,1,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Amazon");
		//VVH, UUR
		board.movePiece(board,3,1,5,2,WHITE);
		assertNull(board.getPiece(3,1));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Amazon");
		//VVH, DDR
		board.movePiece(board,5,2,3,3,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Amazon");
		
		new Amazon(board,WHITE,4,4);
		//move diagonally
		board.movePiece(board,4,4,5,3,WHITE);
		assertNull(board.getPiece(4,4));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Amazon");
		//move along rank
		board.movePiece(board,5,3,5,6,WHITE);
		assertEquals(board.getPiece(5, 6).getClass().toString().substring(12),"Amazon");
	}
	
	/**
	 * test legal moves to empty space, there're pieces in the path between
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Amazon(board,WHITE,3,3);
		//VHH, ULL
		new Pawn(board,WHITE,4,3);
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Amazon");
		//VHH, URR
		new Pawn(board,WHITE,5,1);
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Amazon");
		//VHH, DLL
		new Pawn(board,BLACK,4,2);
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Amazon");
		//VHH, DRR
		new Pawn(board,BLACK,3,2);
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Amazon");
		
		new Amazon(board,WHITE,2,2);
		new Pawn(board,BLACK,2,3);
		//move along file, cannot leap
		board.movePiece(board,2,2,2,6,WHITE);
		assertNull(board.getPiece(2,6));
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Amazon");
		//move diagonally, cannot leap
		new Rook(board,WHITE,3,3);
		board.movePiece(board,2,2,4,4,WHITE);
		assertNull(board.getPiece(4,4));
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Amazon");
	}
	
	/**
	 * test a legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new Amazon(board,WHITE,2,2);
		new Pawn(board,WHITE,1,3);
		board.movePiece(board,2,2,1,3,WHITE);
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Amazon");
		assertEquals(board.getPiece(1, 3).getClass().toString().substring(12),"Pawn");
	}
	
	/**
	 * test a legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new Amazon(board,WHITE,3,3);
		new Pawn(board,BLACK,4,2);
		board.movePiece(board,3,3,4,2,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 2).getClass().toString().substring(12),"Amazon");
		assertEquals(board.getCamp(BLACK).size(),0);
		assertEquals(board.getCamp(WHITE).size(),1);
	}
	
	/**
	 * test Amazon cannot move in ways other than going straightly or diagonally or L-shape
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Amazon(board,WHITE,3,3);
		board.movePiece(board,3,3,5,6,WHITE);
		assertNull(board.getPiece(5,6));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Amazon");
		
	}
}
