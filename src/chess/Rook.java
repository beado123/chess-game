package chess;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Rook extends ChessPiece{

	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of rook
	 * @param file file of rook
	 */
	public Rook(Board board, int sets, int rank, int file) {
		super(board,sets,rank,file);
	}
	
	/* (non-Javadoc)
	 *  can move any number of squares along any rank or file, but may not leap over other pieces.
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		//check not out of bound
		if(!super.legalMove(board,oldRank, oldFile, newRank, newFile))return false;
		
		if(super.straightMoveNoLeap(board, oldRank, oldFile, newRank, newFile))return true;
		return false;
	}
}
