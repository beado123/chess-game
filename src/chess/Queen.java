package chess;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Queen extends ChessPiece {

	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of queen
	 * @param file file of queen
	 */
	public Queen(Board board, int sets, int rank, int file) {
		super(board,sets,rank,file);
	}
	
	/* (non-Javadoc)
	 * can move any number of squares along rank, file, or diagonal, 
	 * but it may not leap over other pieces.
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		//check not out of bounds
		if(!super.legalMove(board,oldRank, oldFile, newRank, newFile))return false;
		if(super.straightMoveNoLeap(board, oldRank, oldFile, newRank, newFile))return true;
		else if(super.diagonalMoveNoLeap(board, oldRank, oldFile, newRank, newFile))return true;
		return false;
	}
}
