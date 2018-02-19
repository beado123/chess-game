package chess;
import view.GUI;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Game {
	
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	private Board board;
	
	/**
	 * constructor for Game object
	 */
	public Game() {
		board = new Board();
		board.initializePieces();
		board.initKings(7, 4, 0, 4);
	}
	
	/**
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * This function starts the chess game application
	 * @param args
	 */
	public static void main(String args[]) {
		Game game = new Game();
		new GUI(game);
	}
}
