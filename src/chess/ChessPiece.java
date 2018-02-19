package chess;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class ChessPiece {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	private int rank;
	private int file; 
	private int camp;
	private boolean isFirstMove;
	
	/**
	 * chess pieces constructor
	 * @param board the chess board
	 * @param camp white/black camp of the piece
	 * @param rank rank of piece
	 * @param file file of piece
	 */
	public ChessPiece(Board board, int camp, int rank, int file) {
		this.camp = camp;
		this.rank = rank;
		this.file = file;
		this.isFirstMove = true;
		board.setPiece(this,rank,file);
		board.addToCamp(this);
	}
	
	/**
	 * @return camp(white/black) of piece
	 */
	public int getCamp() {
		return this.camp;
	}
		
	/**
	 * @return rank of the piece
	 */
	public int getRank() {
		return this.rank;
	}
	
	/**
	 * @return file of the piece
	 */
	public int getFile() {
		return this.file;
	}
	
	/**
	 * @param rank set rank of the piece
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * @param set file of the piece
	 */
	public void setFile(int file) {
		this.file = file;
	}
	
	/**
	 * @return if it's the first move of the piece
	 */
	public boolean getFirstMove() {
		return this.isFirstMove;
	}
	
	/**
	 * set the boolean variable to indicate it's not its first move any more
	 */
	public void setFirstMove() {
		this.isFirstMove = false;
	}
	
	/**
	 * This function checks if new position out of bounds or does not change.
	 * In the sub-classes, it also checks the legal moves associated with the specific type of piece. 
	 * @param board the chess board
	 * @param oldRank rank of the piece we want to move
	 * @param oldFile file of the piece we want to move
	 * @param newRank rank of the new position
	 * @param newFile file of the new position
	 * @return whether it's a legal move from current position to the new position
	 */
	public boolean legalMove(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		
		//out of dimension
		if(newRank>=BOARD_SIZE || newRank<0 ||
		newFile>=BOARD_SIZE || newFile<0)return false;
		
		return true;
	}
	
	/**
	 * This function checks the move is diagonal and there's no piece in the path
	 * @param board the chess board
	 * @param oldRank
	 * @param oldFile
	 * @param newRank
	 * @param newFile
	 * @return true if the move is legal
	 */
	public boolean diagonalMoveNoLeap(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		
		if(Math.abs(newRank-oldRank)==Math.abs(newFile-oldFile)) {
			
			for(int i=1;i<Math.abs(newRank-oldRank);i++) {
				if(newRank>oldRank && newFile>oldFile) {
					if(board.getPiece(oldRank+i,oldFile+i)!=null)return false;
				}
				else if(newRank<oldRank && newFile<oldFile) {
					if(board.getPiece(oldRank-i,oldFile-i)!=null)return false;
				}
				else if(newRank>oldRank && newFile<oldFile) {
					//System.out.println("inter pieces: "+board.getPiece(oldRank+i,oldFile-i));
					if(board.getPiece(oldRank+i,oldFile-i)!=null)return false;
				}
				else {
					if(board.getPiece(oldRank-i,oldFile+i)!=null)return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * This function checks the move is straight and there's no piece in the path
	 * @param board the chess board
	 * @param oldRank
	 * @param oldFile
	 * @param newRank
	 * @param newFile
	 * @return true if the move is legal
	 */
	public boolean straightMoveNoLeap(Board board, int oldRank, int oldFile, int newRank, int newFile) {
		
		if(oldRank==newRank || oldFile==newFile) {
			
			for(int i=1;i<Math.abs(newRank-oldRank);i++) {
				if(newRank>oldRank) {
					if(board.getPiece(oldRank+i,oldFile)!=null)return false;
				}
				else{
					if(board.getPiece(oldRank-i,oldFile)!=null)return false;
				}
			}
			for(int i=1;i<Math.abs(newFile-oldFile);i++) {
				if(newFile>oldFile) {
					if(board.getPiece(oldRank,oldFile+i)!=null)return false;
				}
				else {
					if(board.getPiece(oldRank,oldFile-i)!=null)return false;
				}
			}
			return true;
		}
		return false;
	}
}
