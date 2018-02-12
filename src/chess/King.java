package chess;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class King extends ChessPiece {

	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of king
	 * @param file file of king
	 */
	public King(Board board, int sets, int rank, int file) {
		super(board,sets,rank,file);
	}
	
	/* (non-Javadoc)
	 * moves one square in any direction.
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		//check not out of bounds and not move
		if(!super.legalMove(board,oldRank, oldFile, newRank, newFile))return false;
		//check move one square
		if(Math.abs(newRank-oldRank)<=1 && Math.abs(newRank-oldRank)>=0 && 
		Math.abs(newFile-oldFile)<=1 && Math.abs(newFile-oldFile)>=0 && 
		(board.getPiece(newRank,newFile)==null || 
		board.getPiece(newRank,newFile).getCamp()!=board.getPiece(oldRank,oldFile).getCamp()))
			return true;
		else return false;
	}
}
