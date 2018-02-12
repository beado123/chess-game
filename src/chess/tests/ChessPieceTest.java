package chess.tests;
import chess.*;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class ChessPieceTest extends TestCase {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;

	/**
	 * This function tests chess piece constructor
	 */
	public void testChessPieceConstructor() {
		Board board = new Board();
		new King(board,WHITE,0,4);
		assertEquals(board.getPiece(0,4).getRank(),0);
		assertEquals(board.getPiece(0,4).getFile(),4);
		assertEquals(board.getPiece(0,4).getCamp(),WHITE);
		assertEquals(board.getCamp(WHITE).size(),1);
		assertEquals(board.getPiece(0,4).getFirstMove(),true);
		assertNotNull(board.getPiece(0,4));
	}
}
