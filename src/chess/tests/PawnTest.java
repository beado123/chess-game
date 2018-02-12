package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class PawnTest extends TestCase {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * test player moves the piece out of bounds or no move
	 */
	public void testOutOfBound () {
		Board board = new Board();
		new Pawn(board,WHITE,1,0);
		board.movePiece(board,1,0,8,8,WHITE);
		assertEquals(board.getPiece(1, 0).getClass().toString().substring(12),"Pawn");
		board.movePiece(board,1,0,1,0,WHITE);
		assertEquals(board.getPiece(1, 0).getClass().toString().substring(12),"Pawn");
	}
	
	/**
	 * test white pawn's legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpaceWhite () {
		Board board = new Board();
		//first move, go up 2 square
		new Pawn(board,WHITE,1,0);
		board.movePiece(board,1,0,3,0,WHITE);
		assertNull(board.getPiece(1,0));
		assertEquals(board.getPiece(3, 0).getClass().toString().substring(12),"Pawn");
		//second move, go up 1 square
		board.movePiece(board,3,0,4,0,WHITE);
		assertEquals(board.getPiece(4, 0).getClass().toString().substring(12),"Pawn");
		//third move, move diagonally, capture opponent
		new Rook(board,BLACK,5,1);
		board.movePiece(board,4,0,5,1,WHITE);
		assertEquals(board.getPiece(5, 1).getClass().toString().substring(12),"Pawn");

	}
	
	/**
	 * test black pawn's legal moves to empty space, no pieces in the path between
	 */
	public void testMoveToEmptySpaceBlack () {
		Board board = new Board();
		//first move, go down 2 square
		new Pawn(board,BLACK,6,0);
		board.movePiece(board,6,0,4,0,BLACK);
		assertNull(board.getPiece(6,0));
		assertEquals(board.getPiece(4, 0).getClass().toString().substring(12),"Pawn");
		//second move, go down 1 square
		board.movePiece(board,4,0,3,0,BLACK);
		assertEquals(board.getPiece(3, 0).getClass().toString().substring(12),"Pawn");
		//third move, move diagonally, capture opponent
		new Rook(board,WHITE,2,1);
		board.movePiece(board,3,0,2,1,BLACK);
		assertEquals(board.getPiece(2, 1).getClass().toString().substring(12),"Pawn");

	}
	
	/**
	 * test illegal moves of white pawn
	 */
	public void testIllegalMoveWhite() {
		Board board = new Board();
		new Pawn(board,WHITE,1,0);
		new Rook(board,WHITE,2,0);
		//move forward two squares, path occupied by piece of own side
		board.movePiece(board,1,0,3,0,WHITE);
		assertEquals(board.getPiece(1, 0).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(2, 0).getClass().toString().substring(12),"Rook");
		//move forward one square to an occupied space
		board.movePiece(board,1,0,2,0,WHITE);
		assertEquals(board.getPiece(1, 0).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(2, 0).getClass().toString().substring(12),"Rook");
		//move one square, then cannot move two squares next time
		new Pawn(board,WHITE,1,5);
		board.movePiece(board,1,5,2,5,WHITE);
		board.movePiece(board,2,5,4,5,WHITE);
		assertEquals(board.getPiece(2, 5).getClass().toString().substring(12),"Pawn");
		assertNull(board.getPiece(4,5));
		//cannot move backwards
		new Pawn(board,WHITE,2,4);
		board.movePiece(board,2,4,1,4,WHITE);
		assertEquals(board.getPiece(2, 4).getClass().toString().substring(12),"Pawn");
		assertNull(board.getPiece(1,4));
		//cannot move down to capture pieces
		new Pawn(board,WHITE,3,3);
		new Knight(board,BLACK,2,2);
		board.movePiece(board,3,3,2,2,BLACK);
		assertEquals(board.getPiece(3, 3).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(2, 2).getClass().toString().substring(12),"Knight");
	}
	
	/**
	 * test illegal moves of black pawn
	 */
	public void testIllegalMoveBlack() {
		Board board = new Board();
		new Pawn(board,BLACK,7,0);
		new Rook(board,BLACK,6,0);
		//move forward two squares, path occupied by piece of own side
		board.movePiece(board,7,0,5,0,BLACK);
		assertEquals(board.getPiece(7, 0).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(6, 0).getClass().toString().substring(12),"Rook");
		//move forward one square to an occupied space
		board.movePiece(board,7,0,6,0,BLACK);
		assertEquals(board.getPiece(7, 0).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(6, 0).getClass().toString().substring(12),"Rook");
		//move one square, then cannot move two squares next time
		new Pawn(board,BLACK,7,5);
		board.movePiece(board,7,5,6,5,BLACK);
		board.movePiece(board,6,5,4,5,BLACK);
		assertEquals(board.getPiece(6, 5).getClass().toString().substring(12),"Pawn");
		assertNull(board.getPiece(4,5));
		//cannot move up
		new Pawn(board,BLACK,6,4);
		board.movePiece(board,6,4,7,4,BLACK);
		assertEquals(board.getPiece(6, 4).getClass().toString().substring(12),"Pawn");
		assertNull(board.getPiece(7,4));
		//cannot move up to capture pieces
		new Pawn(board,BLACK,4,4);
		new Knight(board,WHITE,5,5);
		board.movePiece(board,4,4,5,5,BLACK);
		assertEquals(board.getPiece(4, 4).getClass().toString().substring(12),"Pawn");
		assertEquals(board.getPiece(5, 5).getClass().toString().substring(12),"Knight");
	}
	

}
