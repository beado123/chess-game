package chess.tests;
import java.util.Vector;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class BoardTest extends TestCase{

	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	private Vector<String> pieces;
	
	/**
	 * This functions tests behavior of board constructor and chess pieces initialization
	 */
	public void testBoardConstructorAndInit() {
		
		this.pieces = new Vector<String>();
		pieces.add("Rook");
		pieces.add("Knight");
		pieces.add("Bishop");
		Board board = new Board();
		board.initializePieces();
		for(int i=0;i<3;i++) {		
			assertEquals(board.getPiece(0, i).getClass().toString().substring(12),pieces.get(i));
			assertEquals(board.getPiece(7, i).getClass().toString().substring(12),pieces.get(i));
			assertEquals(board.getPiece(0, BOARD_SIZE-i-1).getClass().toString().substring(12),pieces.get(i));
			assertEquals(board.getPiece(7, BOARD_SIZE-i-1).getClass().toString().substring(12),pieces.get(i));
		}
		for(int i=0;i<BOARD_SIZE;i++) {
			assertEquals(board.getPiece(1, i).getClass().toString().substring(12),"Pawn");
			assertEquals(board.getPiece(6, i).getClass().toString().substring(12),"Pawn");
			
		}
		assertEquals(board.getPiece(0, 3).getClass().toString().substring(12),"Queen");
		assertEquals(board.getPiece(0, 4).getClass().toString().substring(12),"King");
		assertEquals(board.getPiece(7, 3).getClass().toString().substring(12),"Queen");
		assertEquals(board.getPiece(7, 4).getClass().toString().substring(12),"King");
		//test black and white camp
		assertEquals(board.getCamp(WHITE).size(),16);
		assertEquals(board.getCamp(BLACK).size(),16);
	}
	
	/**
	 * This function tests the initialization of Amazon and Princess piece
	 */
	public void testAddCustomPieces() {
		Board board = new Board();
		board.addCustomPieces();
		assertEquals(board.getPiece(5, 3).getClass().toString().substring(12),"Amazon");
		assertEquals(board.getPiece(2, 4).getClass().toString().substring(12),"Princess");
	}
	
	/**
	 * This function tests removing custom pieces
	 */
	public void testRemoveCustom() {
		Board board = new Board();
		board.addCustomPieces();
		board.removeCustomPieces();
		assertEquals(board.getPiece(5, 3),null);
		assertEquals(board.getPiece(2, 4),null);
	}
	
	/**
	 * This function tests the initialization of king
	 */
	public void testInitKing() {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		assertEquals(board.getKing(WHITE).getRank(),7);
		assertEquals(board.getKing(WHITE).getFile(),4);
		assertEquals(board.getKing(BLACK).getRank(),0);
		assertEquals(board.getKing(BLACK).getFile(),4);
	}
	
	/**
	 * This function tests updating king's rank and file
	 */
	public void testUpdateKing() {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		board.updateKing(WHITE, 5, 4);
		assertEquals(board.getKing(WHITE).getRank(),5);
		assertEquals(board.getKing(WHITE).getFile(),4);
	}
	
	/**
	 * This function tests a move is not legal if it causes the king in check
	 */
	public void testProtectKing() {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		new Rook(board,BLACK,5,4);
		new Pawn(board,WHITE,6,5);
		assertEquals(board.movePiece(board, 6, 5, 5, 5, WHITE),false);
		assertEquals(board.movePiece(board, 7, 4, 6, 3, WHITE),true);
	}
	
	/**
	 * This function tests undo operation.
	 */
	public void testUndoMove() {
		Board board = new Board();
		new King(board,WHITE,7,4);
		new King(board,BLACK,0,4);
		board.initKings(7, 4, 0, 4);
		Rook myRook = new Rook(board,WHITE,7,0);
		board.movePiece(board, 7, 0, 6, 0, WHITE);
		board.undoMove(7, 0, 6, 0, null);
		assertEquals(board.getPiece(7, 0).getClass().toString().substring(12),"Rook");
		assertEquals(board.getPiece(6, 0),null);
		assertEquals(myRook.getRank(),7);
		assertEquals(myRook.getFile(),0);
	}
	
	/**
	 * check state test 1
	 */
	public void testInCheck1() {
		Board board = new Board();
		new King(board,BLACK,5,2);
		new King(board,WHITE,0,4);
		new Rook(board,WHITE,1,2);
		board.initKings(0,4,5,2);
		assertEquals(board.inCheck(BLACK),true);
	}
	
	/**
	 * check state test 2
	 */
	public void testInCheck2() {
		Board board = new Board();
		new King(board,BLACK,7,7);
		new King(board,WHITE,5,4);
		new Bishop(board,BLACK,1,0);
		board.initKings(5,4,7,7);
		assertEquals(board.inCheck(WHITE),true);
	}
	
	/**
	 * stale mate test 1
	 */
	public void testStaleMate1() {
		Board board = new Board();
		new King(board,BLACK,4,0);
		new King(board,WHITE,3,2);
		new Queen(board,WHITE,5,2);
		board.initKings(4,0,3,2);
		assertEquals(board.isStaleMate(BLACK),true);
	}
	
	/**
	 * stale mate test 2
	 */
	public void testStaleMate2() {
		Board board = new Board();
		new King(board,BLACK,7,0);
		new King(board,WHITE,5,0);
		new Bishop(board,WHITE,3,5);
		new Pawn(board,WHITE,6,0);
		board.initKings(5,0,7,0);
		assertEquals(board.isStaleMate(BLACK),true);	
	}
	
	/**
	 * stale mate test 3
	 */
	public void testStaleMate3() {
		Board board = new Board();
		new King(board,BLACK,0,0);
		new King(board,WHITE,2,2);
		new Rook(board,WHITE,1,1);
		board.initKings(2,2,0,0);
		assertEquals(board.isStaleMate(BLACK),true);	
	}
	
	/**
	 * stale mate test 4
	 */
	public void testStaleMate4() {
		Board board = new Board();
		new King(board,BLACK,0,0);
		new King(board,WHITE,2,2);
		new Rook(board,WHITE,1,1);
		board.initKings(2,2,0,0);
		assertEquals(board.isStaleMate(BLACK),true);	
	}
	
	/**
	 * check mate test 1
	 */
	public void testCheckMate1() {
		Board board = new Board();
		new King(board,BLACK,7,4);
		new King(board,WHITE,0,4);
		board.initKings(0,4,7,4);
		new Queen(board,BLACK,3,7);
		new Pawn(board,WHITE,3,6);
		new Pawn(board,WHITE,1,4);
		new Pawn(board,WHITE,1,3);
		new Queen(board,WHITE,0,3);
		new Bishop(board,WHITE,0,5);
		assertEquals(board.isCheckMate(WHITE),true);
	}
	
	/**
	 * check mate test 2
	 */
	public void testCheckMate2() {
		Board board = new Board();
		new King(board,WHITE,0,2);
		new King(board,BLACK,6,6);
		new Rook(board,BLACK,1,2);
		new Knight(board,BLACK,2,2);
		new Bishop(board,BLACK,2,1);
		board.initKings(0,2,6,6);
		assertEquals(board.isCheckMate(WHITE),true);
	}
	
	/**
	 * check mate test 3
	 */
	public void testCheckMate3() {
		Board board = new Board();
		new King(board,WHITE,4,5);
		new King(board,BLACK,4,7);
		new Rook(board,WHITE,0,7);
		board.initKings(4,5,4,7);
		assertEquals(board.isCheckMate(BLACK),true);
	}
	
	
}
