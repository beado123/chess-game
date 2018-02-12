/**
 * 
 */
package chess;

/**
 * Princess combines moves of Bishop and Knight
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Princess extends ChessPiece {
	
	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of knight
	 * @param file file of knight
	 */
	public Princess(Board board,int sets, int rank, int file) {
		super(board,sets,rank,file);
	}
	
	/* (non-Javadoc)
	 * the move forms an "L"-shape, can leap over other pieces.
	 * or move diagonally, cannot leap.
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		
		//check not out of bounds
		if(!super.legalMove(board,oldRank, oldFile, newRank, newFile))return false;
		
		//check L-shape move 
		if((Math.abs(newRank-oldRank)==1 && Math.abs(newFile-oldFile)==2) || 
				(Math.abs(newRank-oldRank)==2 && Math.abs(newFile-oldFile)==1))return true;
		
		if(super.diagonalMoveNoLeap(board, oldRank, oldFile, newRank, newFile))return true;
				
		return false;
	}

}
