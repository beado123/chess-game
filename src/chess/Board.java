package chess;
import java.util.Vector;

/**
 * @author Yihan Zhang
 * @since 2-10-2018
 */
public class Board {
	
	public static final int BOARD_SIZE = 8;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	private ChessPiece[][] chessBoard;
	private Vector<ChessPiece> whiteCamp,blackCamp;
	private ChessPiece whiteKing,blackKing;
	
	/**
	 * board constructor
	 */
	public Board() {
		this.chessBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		this.whiteCamp = new Vector<ChessPiece>();
		this.blackCamp = new Vector<ChessPiece>();
		for(int rank=0;rank<BOARD_SIZE;rank++) {
			for(int file=0;file<BOARD_SIZE;file++) {
				chessBoard[rank][file] = null;
			}
		}
	}
	
	/**
	 * This function initializes positions for pieces
	 */
	public void initializePieces() {
		for(int file=0;file<BOARD_SIZE;file++) {
			new Pawn(this,WHITE,6,file);
			new Pawn(this,BLACK,1,file);
		}
		for(int i=0;i<2;i++) {
			new Rook(this,i==BLACK?BLACK:WHITE,i==0?0:7,0);
			new Rook(this,i==BLACK?BLACK:WHITE,i==0?0:7,7);
			new Knight(this,i==BLACK?BLACK:WHITE,i==0?0:7,1);
			new Knight(this,i==BLACK?BLACK:WHITE,i==0?0:7,6);
			new Bishop(this,i==BLACK?BLACK:WHITE,i==0?0:7,2);
			new Bishop(this,i==BLACK?BLACK:WHITE,i==0?0:7,5);
			new Queen(this,i==BLACK?BLACK:WHITE,i==0?0:7,3);
			new King(this,i==BLACK?BLACK:WHITE,i==0?0:7,4);
		}
	}
	
	/**
	 * This function sets the position, rank, and file for whiteKing and blackKing
	 * @param whiteRank rank of the white king
	 * @param whiteFile file of the white king
	 * @param blackRank rank of the black king
	 * @param blackFile file of the black king
	 */
	public void initKings(int whiteRank, int whiteFile, int blackRank, int blackFile) {
		this.whiteKing = this.chessBoard[whiteRank][whiteFile];
		this.blackKing = this.chessBoard[blackRank][blackFile];
		if(this.whiteKing!=null) {
			this.whiteKing.setRank(whiteRank);
			this.whiteKing.setFile(whiteFile);
		}
		if(this.blackKing!=null) {
			this.blackKing.setRank(blackRank);
			this.blackKing.setFile(blackFile);
		}
	}
	
	/**
	 * @param camp(black/white)
	 * @return black king/white king
	 */
	public ChessPiece getKing(int camp) {
		return camp==BLACK?this.blackKing:this.whiteKing;
	}
	
	/**
	 * @param camp(white/black)
	 * @param newRank the new rank we want to set
	 * @param newFile the new file we want to set
	 */
	public void updateKing(int camp, int newRank, int newFile) {
		ChessPiece king = camp==BLACK?blackKing:whiteKing;
		king.setRank(newRank);
		king.setFile(newFile);
	}
	
	/**
	 * @return the piece at given rank and file
	 */
	public ChessPiece getPiece(int rank, int file) {
		return chessBoard[rank][file];
	}
	
	/**
	 * @param rank rank we want to place the piece 
	 * @param file file we want to place the piece 
	 */
	public void setPiece(ChessPiece chess, int rank, int file) {
		this.chessBoard[rank][file] = chess;
	}
	
	/**
	 * @param piece the piece we want to add to its corresponding camp
	 */
	public void addToCamp(ChessPiece piece){
		if(piece.getCamp()==WHITE)whiteCamp.add(piece);
		else blackCamp.add(piece);
	}
	
	/**
	 * @return the camp(vector of pieces)
	 */
	public Vector<ChessPiece> getCamp(int camp){
		return camp==BLACK?blackCamp:whiteCamp;
	}
	
	/**
	 * This function moves given piece to a new location, 
	 * and captures opponent's pieces if necessary.
	 * @param board current chess board
	 * @param oldRank rank of the piece we want to move
	 * @param oldFile file of the piece we want to move
	 * @param newRank rank of the new position
	 * @param newFile file of the new position
	 * @param camp camp of the piece we want to move
	 */
	public void movePiece(Board board, int oldRank, int oldFile, int newRank, int newFile, int camp) {
		
		if(board.getPiece(oldRank,oldFile).legalMove(board,oldRank, oldFile, newRank, newFile) && 
				(board.getPiece(newRank,newFile)==null || board.getPiece(newRank,newFile).getCamp()!=camp)) {
			
			if(board.getPiece(newRank,newFile)!=null) {
				//delete captured piece
				ChessPiece captured = board.getPiece(newRank,newFile);
				if(captured.getCamp()==BLACK)blackCamp.remove(captured);
				else whiteCamp.remove(captured);
			}
			ChessPiece oldPiece = getPiece(oldRank,oldFile);
			board.setPiece(null,oldRank,oldFile) ;
			board.setPiece(oldPiece,newRank,newFile);
			
		}
	}
	
	/**
	 * This function checks if the king of passed in camp is in check state
	 * @param camp black/white camp
	 * @return if the camp is in check state
	 */
	public boolean inCheck(int camp) {
		Vector<ChessPiece> list;
		if(camp==WHITE) list = blackCamp;
		else list = whiteCamp;
		ChessPiece king = camp==BLACK?blackKing:whiteKing;
		for(int i=0;i<list.size();i++) {
			ChessPiece curr = list.get(i);
			if(curr.legalMove(this, curr.getRank(), curr.getFile(), king.getRank(), king.getFile())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function checks if the passed in camp has no legal moves 
	 * given that king is not in check
	 * @param camp black/white camp
	 * @return if the camp is in StaleMate state 
	 */
	public boolean isStaleMate(int camp) {
		
		boolean moveKing = false;
		boolean result = true;
		Vector<ChessPiece> list = camp==BLACK?blackCamp:whiteCamp;
		ChessPiece king = camp==BLACK?blackKing:whiteKing;
		
		if(!inCheck(camp)) {
			for(int i=0;i<list.size();i++) {
				ChessPiece curr = list.get(i);
				int oldRank = curr.getRank();
				int oldFile = curr.getFile();
				for(int rank=0;rank<BOARD_SIZE;rank++) {
					for(int file=0;file<BOARD_SIZE;file++) {
						
						ChessPiece target = chessBoard[rank][file];
						if(!inCheck(camp) && (target==null || target.getCamp()!=camp) &&
								curr.legalMove(this, oldRank, oldFile, rank, file)) {
							
							this.chessBoard[rank][file] = curr;
							this.chessBoard[oldRank][oldFile]=null;
							//update king's position
							if(curr.getRank() == king.getRank() && curr.getFile() == king.getFile()) {
								moveKing = true;
								this.updateKing(camp, rank, file);
							}
							if(!inCheck(camp)) {
								result = false;
							}
							chessBoard[oldRank][oldFile]=curr;
							if(target!=null)chessBoard[rank][file]=target;
							else chessBoard[rank][file]=null;
							//recover king's position if necessary
							if(moveKing) this.updateKing(camp, oldRank, oldFile);
						}
					}		
				}
			}
		}
		return result;
	}
	
	
	/**
	 * This function checks if current camp has a way to get out of check, 
	 * if not, then is CheckMated
	 * @param camp black/white camp
	 * @return if the camp is in state CheckMate
	 */
	public boolean isCheckMate(int camp) {
		
		boolean result = true;
		boolean moveKing = false;
		Vector<ChessPiece> list = camp==BLACK?blackCamp:whiteCamp;
		ChessPiece king = camp==BLACK?blackKing:whiteKing;
		
		for(int i=0;i<list.size();i++) {
			ChessPiece curr = list.get(i);
			int oldRank = curr.getRank();
			int oldFile = curr.getFile();
			for(int rank=0;rank<BOARD_SIZE;rank++) {
				for(int file=0;file<BOARD_SIZE;file++) {	
					ChessPiece target = chessBoard[rank][file];
					if(inCheck(camp) && (target==null || target.getCamp()!=camp) &&
						curr.legalMove(this, oldRank, oldFile, rank, file)) {
						//move curr to rank&file to see if we can get out of check state						
						this.chessBoard[rank][file] = curr;
						this.chessBoard[oldRank][oldFile]=null;
						//update king's position
						if(curr.getRank() == king.getRank() && curr.getFile() == king.getFile()) {
							moveKing = true;
							this.updateKing(camp, rank, file);
						}
						if(!inCheck(camp)) {
							result = false;
						}
						chessBoard[oldRank][oldFile]=curr;
						if(target!=null)chessBoard[rank][file]=target;
						else chessBoard[rank][file]=null;
						//recover king's position if necessary
						if(moveKing) this.updateKing(camp, oldRank, oldFile);
					}
				}
			}
		}
		return result;
	}
}
