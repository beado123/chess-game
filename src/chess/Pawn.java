package chess;

/**
 * @author Yihan ZHang
 * @since 2-10-2018
 */
public class Pawn extends ChessPiece{

	/**
	 * @param board the chess board
	 * @param camp white/black camp 
	 * @param rank rank of pawn
	 * @param file file of pawn
	 */
	public Pawn(Board board, int sets, int rank, int file) {
		super(board,sets,rank,file);
	}
	
	/* (non-Javadoc)
	 * @see chess.ChessPiece#legalMove(chess.Board, int, int, int, int)
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		//check not out of bounds
		if(!super.legalMove(board, oldRank, oldFile, newRank, newFile))return false;
		if(newRank==oldRank && newFile==oldFile)return true;
		
		//if it's pawn's first move 
		if(this.getFirstMove()) {
			
			if(this.getCamp()==WHITE) {
				//move to empty space
				if((newRank == oldRank-1 && newFile==oldFile && board.getPiece(newRank,newFile)==null ) 
					|| (newRank == oldRank-2 && newFile==oldFile && board.getPiece(newRank+1,newFile)==null && board.getPiece(newRank,newFile)==null )) {
					return true;
				}
				//capture opponent's piece
				if(newRank==oldRank-1 && Math.abs(newFile-oldFile)==1 && 
						board.getPiece(newRank,newFile)!=null && 
						board.getPiece(newRank,newFile).getCamp() != board.getPiece(oldRank,oldFile).getCamp()) {
					return true;
				}
				return false;
			}
			else if(this.getCamp()==BLACK) {
				if((newRank == oldRank+1 && newFile==oldFile && board.getPiece(newRank,newFile)==null ) 
					|| (newRank == oldRank+2 && newFile==oldFile && board.getPiece(newRank-1,newFile)==null && board.getPiece(newRank,newFile)==null )) {
					return true;
				}
				//capture opponent's piece
				if(newRank==oldRank+1 && Math.abs(newFile-oldFile)==1 && 
						board.getPiece(newRank,newFile)!=null && 
						board.getPiece(newRank,newFile).getCamp() != board.getPiece(oldRank,oldFile).getCamp()) {
					return true;
				}
				return false;
			}
			return false;
		}
		 
		//not it's first move
		else {
			if(this.getCamp()==WHITE) {
				
				//move forward one step
				if((newRank == oldRank-1 && newFile==oldFile && board.getPiece(newRank,newFile)==null) )return true;
				//move forward diagonally one step
				else if( (newRank==oldRank-1 && board.getPiece(newRank,newFile)!=null && board.getPiece(newRank,newFile).getCamp()==BLACK ) &&
						(newFile==oldFile+1 || newFile==oldFile-1)	)return true;
				return false;
				
			}
			else if(this.getCamp()==BLACK) {
				
				//move forward one step
				if((newRank == oldRank+1 && newFile==oldFile && board.getPiece(newRank,newFile)==null) )return true;
				//move forward diagonally one step
				else if( (newRank==oldRank+1 && board.getPiece(newRank,newFile)!=null && board.getPiece(newRank,newFile).getCamp()==WHITE ) &&
						(newFile==oldFile-1 || newFile==oldFile+1) )return true;
				return false;
				
			}
			return false;
		}
	}
}
