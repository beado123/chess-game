package view;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import chess.Board;
import chess.ChessPiece;
import chess.Game;
 
 
public class GUI implements MouseListener, MouseMotionListener, ActionListener{
 
	private ChessView view;
	private JLabel chessPiece;
	private JLabel targetPiece;
	private Component squarePanel;
	private int oldRank=-1;
	private int oldFile=-1;
	private int newRank=-1;
	private int newFile=-1;
	
	private int whiteScore = 0;
	private int blackScore = 0;	
	
	private Board board;
	private Game game;
	private ChessPiece target;
	private int turn = WHITE;
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	static volatile GameState state;
	static enum GameState {
	      TURNCHANGED, PLAYING, CHECK, RESTART, FORFEIT
	   }
	
	
    /**
     * Constructor for chess game GUI
     * @param newGame
     */
    public GUI(Game newGame){
    	this.game = newGame;
    	this.board = newGame.getBoard();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        view = new ChessView(this.board);
        view.getChessBoard().addMouseListener(this);
        view.getChessBoard().addMouseMotionListener(this);
        view.getUndoButton().addActionListener(this);
        view.getForfeitButton().addActionListener(this);
        view.getRestartButton().addActionListener(this);
        view.getMenuClassic().addActionListener(this);
        view.getMenuCustom().addActionListener(this);
        view.setText();
        state = GameState.PLAYING;
        gameLoop(view.getWindow());
    }
   
    /**
     * This function is used to detect state change and display the message on top of board.
     * @param window
     */
    private void gameLoop(JFrame window) {
    	while(true) {
    		if(state==GameState.TURNCHANGED) {
    			turn = 1-turn;
    			if(turn==BLACK)view.getMessage().setText(view.getBlackPlayer()+"'s turn!");
        		else view.getMessage().setText(view.getWhitePlayer()+"'s turn!");
    			state = GameState.PLAYING;
    		}
    		
    		if(state==GameState.RESTART || state==GameState.FORFEIT) {
    			view.getMessage().setText(view.getWhitePlayer()+"'s turn!");
    			state = GameState.PLAYING;
    		}
		}
    }
    
    /**
	 * This function calls movePiece in board class and update pieces in the JPanel
	 * @param oldPanel
	 * @param newPanel
	 * @param targetPiece
	 * @param capture
	 */
	private void movePiece(Container oldPanel,Container newPanel, Component targetPiece, boolean capture) {
		
		if(board.movePiece(board, oldRank, oldFile, newRank, newFile, turn)) {
			state = GameState.TURNCHANGED;
			if(capture)newPanel.remove(targetPiece);
			newPanel.add(chessPiece);
			view.rePaint(newPanel);
		}
		else {
			newPanel.remove(chessPiece);
			view.rePaint(newPanel);
			oldPanel.add(chessPiece);
			view.rePaint(oldPanel);
			JOptionPane.showMessageDialog(null, "Illegal move! Please try again.", 
					"Warning", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * This function checks if the turn is correct
	 * @return
	 */
	private boolean isInTurn() {
		if(chessPiece.getName().substring(0, 5).equals("WHITE")) {
			if(turn==BLACK) {
				JOptionPane.showMessageDialog(null, "Sorry! Not your turn.","Warning", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		if(chessPiece.getName().substring(0, 5).equals("BLACK")) {
			if(turn==WHITE) {
				JOptionPane.showMessageDialog(null, "Sorry! Not your turn.","Warning", JOptionPane.INFORMATION_MESSAGE);
				//return false;
			}
		}
		return true;
	}

    /**
     * This function resets the game. Test case is in GameTest.java
     * @param custom
     */
    public void resetGame(Boolean custom) {
		this.turn = WHITE;
    	view.getChessBoard().removeAll();
    	this.game = new Game();
    	this.board = this.game.getBoard();
    	if(custom)this.board.addCustomPieces();
    	else this.board.removeCustomPieces();
    	view.drawPieces(this.board);
    	view.getChessBoard().revalidate();
    	view.getChessBoard().repaint();
    }
    
    /**
	 * This function checks the state of the game and update scores of each player
	 */
	private void checkState() {
    	
		if(this.board.inCheck(turn) && state!=GameState.CHECK) {
			state = GameState.CHECK;
			String color = turn==1?"white":"black";
			JOptionPane.showMessageDialog(null, color +" king is in check!", 
					"Warning", JOptionPane.INFORMATION_MESSAGE);
		}
		if(this.board.isCheckMate(turn)) {
			String color = turn==1?"white":"black";
			JOptionPane.showMessageDialog(null, color+ " king is CheckMated! Game ends.", 
					"Warning", JOptionPane.INFORMATION_MESSAGE);
			if(turn==WHITE) {
				blackScore++;
				view.getBlackScore().setText(Integer.toString(blackScore));
			}
			else {
				whiteScore++;
				view.getWhiteScore().setText(Integer.toString(whiteScore));
			}
			resetGame(false);
			state = GameState.PLAYING;
			
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		squarePanel = view.getChessBoard().findComponentAt(e.getX(),e.getY());
		if(squarePanel instanceof JPanel) {
			return;
		}
		chessPiece = (JLabel)squarePanel;
		if(!isInTurn())return;
		oldRank = Integer.parseInt(chessPiece.getParent().getName().substring(6, 7));
		oldFile = Integer.parseInt(chessPiece.getParent().getName().substring(7));	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if(chessPiece==null) {
			return;
		}
		chessPiece.setLocation(e.getX(),e.getY());
		Component c = view.getChessBoard().findComponentAt(e.getX(),e.getY());
		Container parent = (Container) c;
		parent.add(chessPiece);
		view.rePaint(parent);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(squarePanel instanceof JPanel) return;
		if(!isInTurn())return;
		if(chessPiece==null) return;
		
		chessPiece.setVisible(true);
		targetPiece = (JLabel)view.getChessBoard().findComponentAt(e.getX(),e.getY());
		Container newPanel = targetPiece.getParent();
		newRank = Integer.parseInt(newPanel.getName().substring(6, 7));
		newFile = Integer.parseInt(newPanel.getName().substring(7));
		target = this.board.getPiece(newRank, newFile);
		JPanel oldPanel = (JPanel) view.getChessBoard().getComponent(8*oldRank+oldFile);
		
		if(newRank==oldRank && newFile==oldFile)return;
		boolean capture = target==null?false:true;
		movePiece(oldPanel,newPanel,targetPiece,capture);
		checkState();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	       
        if(e.getActionCommand().equals("forfeit")) {
        	if(turn==WHITE) {
        		this.blackScore++;
        		view.getBlackScore().setText(Integer.toString(blackScore));
        	}
        	else {
        		this.whiteScore++;
        		view.getWhiteScore().setText(Integer.toString(whiteScore));
        	}
        	state = GameState.FORFEIT;
        	resetGame(false);
        }
        if(e.getActionCommand().equals("restart")) {
        	String player;
        	if(turn==WHITE)player = view.getWhiteUserName().getName();
        	else player = view.getBlackUserName().getName();
        	int result = JOptionPane.showConfirmDialog(
        	    null,
        	    "Player "+player+" wants to restart the game, do you agree?",
        	    "Message",
        	    JOptionPane.YES_NO_OPTION);
        	if(result == JOptionPane.YES_OPTION) {
        		state = GameState.RESTART;
        		resetGame(false);
        	}
        }
        if(e.getActionCommand().equals("undo")) {
        	
        	//restore internal data structure
        	this.board.undoMove(oldRank,oldFile,newRank,newFile,target);
        	//draw chessBoard with undo move
        	JPanel oldSquare = (JPanel)view.getChessBoard().getComponent(8*oldRank+oldFile);
        	JPanel newSquare = (JPanel)view.getChessBoard().getComponent(8*newRank+newFile);
        	oldSquare.add(chessPiece);
        	newSquare.remove(chessPiece);
        	if(target!=null)newSquare.add(targetPiece);
        	view.rePaint(view.getChessBoard());
        	state = GameState.TURNCHANGED;
        }
        if(e.getActionCommand().equals("custom")) {
        	resetGame(true);
        }
        if(e.getActionCommand().equals("classic")) {
        	resetGame(false);
        }
    }
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
   
}