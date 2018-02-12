package chess;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Bishop extends ChessPiece {
	
	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of bishop
	 * @param file file of bishop
	 */
	public Bishop(Board board, int camp, int rank, int file) {
		super(board,camp,rank,file);
	}
	
	/* (non-Javadoc)
	 * can move any number of squares diagonally, but may not leap over other pieces.
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		
		//check not out of bound
		if(!super.legalMove(board,oldRank, oldFile, newRank, newFile))return false;
		
		if(super.diagonalMoveNoLeap(board, oldRank, oldFile, newRank, newFile))return true;
		return false;
	}
}
