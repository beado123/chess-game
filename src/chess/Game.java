package chess;
import java.util.Scanner;
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
		board.initKings(0, 4, 7, 4);
	}
	
	/**
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	public static void main(String args[]) {
		
		int camp = WHITE;
		Game newGame = new Game();
		new GUI(newGame);
		
		Scanner input = new Scanner(System.in);
		/*while(true) {
			if(newGame.board.isCheckMate(camp))System.out.println(camp==WHITE?"white":"black"+" wins!");
			if(newGame.board.isStaleMate(camp))System.out.println("Stalemate!");
			
		}*/
	}
}
