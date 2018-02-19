package chess.tests;
import chess.Board;
import chess.Game;
import junit.framework.TestCase;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class GameTest extends TestCase {

	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	/**
	 * This function tests reseting the game and board
	 */
	public void testResetGame() {
		Game newGame = new Game();
		Board board = newGame.getBoard();
		board.movePiece(board, 6, 4, 5, 4, WHITE);
		//System.out.println(board.getPiece(6, 4));
		assertEquals(board.getPiece(5, 4).getClass().toString().substring(12),"Pawn");
		newGame = new Game();
		assertEquals(newGame.getBoard().getPiece(5, 4),null);
	}
}
