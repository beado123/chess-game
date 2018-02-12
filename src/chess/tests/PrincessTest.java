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
public class PrincessTest extends TestCase{
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Princess(board,WHITE,0,1);
		board.movePiece(board,0,1,8,8,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Princess");
		board.movePiece(board,0,1,0,1,WHITE);
		assertEquals(board.getPiece(0, 1).getClass().toString().substring(12),"Princess");
	}
	
	/**
	 * test legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpace () {
		Board board = new Board();
		new Princess(board,WHITE,3,3);
		//VHH, ULL
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Princess");
		//VHH, URR
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Princess");
		//VHH, DLL
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Princess");
		//VHH, DRR
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		//VVH, UUL
		board.movePiece(board,3,3,5,2,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Princess");
		//VVH, DDL
		board.movePiece(board,5,2,3,1,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 1).getClass().toString().substring(12),"Princess");
		//VVH, UUR
		board.movePiece(board,3,1,5,2,WHITE);
		assertNull(board.getPiece(3,1));
		assertEquals(board.getPiece(5, 2).getClass().toString().substring(12),"Princess");
		//VVH, DDR
		board.movePiece(board,5,2,3,3,WHITE);
		assertNull(board.getPiece(5,2));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		
		//move diagonally
		new Princess(board,WHITE,0,2);
		board.movePiece(board,0,2,2,4,WHITE);
		assertNull(board.getPiece(0,2));
		assertEquals(board.getPiece(2, 4).getClass().toString().substring(12),"Princess");
		board.movePiece(board,2,4,0,2,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Princess");
	}
	
	/**
	 * test legal moves to empty space, there're pieces in the path between
	 */
	public void testMoveToEmptySpaceWithLeap () {
		Board board = new Board();
		new Princess(board,WHITE,3,3);
		//VHH, ULL
		new Pawn(board,WHITE,4,3);
		board.movePiece(board,3,3,4,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Princess");
		//VHH, URR
		new Pawn(board,WHITE,5,1);
		board.movePiece(board,4,1,5,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Princess");
		//VHH, DLL
		new Pawn(board,BLACK,4,2);
		board.movePiece(board,5,3,4,1,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(4, 1).getClass().toString().substring(12),"Princess");
		//VHH, DRR
		new Pawn(board,BLACK,3,2);
		board.movePiece(board,4,1,3,3,WHITE);
		assertNull(board.getPiece(4,1));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		
		//move diagonally, cannot leap
		new Princess(board,WHITE,0,2);
		new Pawn(board,BLACK,1,3);
		board.movePiece(board,0,2,2,4,WHITE);
		assertNull(board.getPiece(2,4));
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Princess");
	}
	
	/**
	 * test a legal move to a space already containing piece of its own side
	 */
	public void testMoveToOccupied() {
		Board board = new Board();
		new Princess(board,WHITE,0,2);
		new Pawn(board,WHITE,1,3);
		board.movePiece(board,0,2,1,3,WHITE);
		assertEquals(board.getPiece(0, 2).getClass().toString().substring(12),"Princess");
		assertEquals(board.getPiece(1, 3).getClass().toString().substring(12),"Pawn");

	}
	
	/**
	 * test a legal move to a space already containing piece of another player
	 */
	public void testCapture () {
		Board board = new Board();
		new Princess(board,WHITE,3,3);
		new Pawn(board,BLACK,1,1);
		board.movePiece(board,3,3,1,1,WHITE);
		assertNull(board.getPiece(3,3));
		assertEquals(board.getPiece(1, 1).getClass().toString().substring(12),"Princess");
		assertEquals(board.getCamp(BLACK).size(),0);
		assertEquals(board.getCamp(WHITE).size(),1);
		new Rook(board,BLACK,2,2);
		board.movePiece(board,1,1,2,2,WHITE);
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Princess");

	}
	
	/**
	 * test Princess cannot move straightly
	 */
	public void testIllegalMove () {
		Board board = new Board();
		new Princess(board,WHITE,3,3);
		//right move
		board.movePiece(board,3,3,3,4,WHITE);
		assertNull(board.getPiece(3,4));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		//left move
		board.movePiece(board,3,3,3,2,WHITE);
		assertNull(board.getPiece(3,2));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		//move up
		board.movePiece(board,3,3,5,3,WHITE);
		assertNull(board.getPiece(5,3));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
		//move down
		board.movePiece(board,3,3,0,3,WHITE);
		assertNull(board.getPiece(0,3));
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Princess");
	}

}
