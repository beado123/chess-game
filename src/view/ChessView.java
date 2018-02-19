package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chess.Board;
import chess.ChessPiece;

public class ChessView {

	private JPanel chessBoard;
	private JLabel message;
	private int whiteScore = 0;
	private int blackScore = 0;
	private JFrame window;
	JLabel whiteScoreLabel;
	JLabel blackScoreLabel;
	JLabel whiteUserName;
	JLabel blackUserName;
	JButton restartButton;
	JButton forfeitButton;
	JButton undoButton;
	JMenuItem classic;
	JMenuItem custom; 
	private String whitePlayer;
	private String blackPlayer;
	
	/**
	 * Constructor of the view
	 * @param board
	 */
	public ChessView(Board board) {
		
		window = new JFrame("Chess Game!");
        window.setSize(500, 600);
        
        JPanel buttonPanel = initializeButtonPanel();
        window.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        
        chessBoard = initializeBoardPanel(board);
        window.getContentPane().add(chessBoard, BorderLayout.CENTER);
        drawPieces(board);
       
        setUpMenu(window);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * This function updates the messages on top of the chess board to
	 * remind the turn.  
	 */
	public void setText() {
		JTextField white = new JTextField("white");
        JTextField black = new JTextField("black");
    	Object [] msg = {
    			"Please enter usernames.",
    			"Black side will start first.",
    			"Black side:", black,
    			"White side:", white
    	};
    	JOptionPane.showMessageDialog(window, msg);
    	this.whitePlayer = white.getText();
    	this.blackPlayer = black.getText();
    	whiteUserName.setText(white.getText());
    	whiteUserName.setName(white.getText());
    	blackUserName.setText(black.getText());
    	blackUserName.setName(black.getText());
    	message.setText(whitePlayer+"'s turn!");
	}
	
	/**
	 * This function gets the chess piece icon specified by the parameters.
	 * @param color white or black
	 * @param className type of the chess piece
	 * @param size size of the icon
	 * @return
	 */
	private JLabel getIcon(String color, String className, int size) {
    	
    	String path = "/home/yihan/eclipse_cs242/chess/images/"+color+className+".png";
		try {
			Image image = ImageIO.read(new File(path)).getScaledInstance(size, size, Image.SCALE_SMOOTH);
			JLabel icon = new JLabel(new ImageIcon(image));
			icon.setName(color+className);
			return icon;
		}catch(IOException e) {
			System.out.println("Cannot find " + path);
		}
		return null;
    }

	/**
	 * This function draws all 32 pieces on the panel
	 * @param board
	 */
	public void drawPieces(Board board) {
        for(int i=0;i<64;i++) {
        	JPanel squarePanel = new JPanel(new BorderLayout());
        	chessBoard.add(squarePanel,JLayeredPane.DEFAULT_LAYER);        	
        	
        	int rank = i/8;
        	int file = i%8;
        	squarePanel.setName("square"+rank+file);
        	if(rank%2 == file%2)squarePanel.setBackground(Color.gray);
        	else squarePanel.setBackground(Color.white);        	
        	ChessPiece piece = board.getPiece(rank, file);
        	if(piece!=null) {
        		String color;
        		int camp = piece.getCamp();
        		if(camp==1)color="WHITE";else color="BLACK";
        		JLabel icon = getIcon(color,piece.getClass().toString().substring(12),30);
        		if(icon!=null)squarePanel.add(icon);
        	}
        }
    }
	
	/**
	 * This function repaints the chess board panel
	 * @param squarePanel
	 */
	public void rePaint(Container squarePanel) {
		squarePanel.revalidate();
		squarePanel.repaint();
	}
	
	/**
     * @param board
     * @return panel for chess board
     */
    private JPanel initializeBoardPanel(Board board) {
        JPanel chessBoard = new JPanel();
        chessBoard.setLayout(new GridLayout(8,8));
        chessBoard.setName("chessBoard");
        return chessBoard;
    }
    
	 /**
     * This function sets up the menu bar
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        menubar.setForeground(Color.MAGENTA);
        JMenu mode = new JMenu("Mode");
        this.classic = new JMenuItem("classic");        
        this.custom = new JMenuItem("custom");
        mode.add(classic);
        mode.add(custom);
        menubar.add(mode);
        window.setJMenuBar(menubar);
    }
    
	 /**
     * This function sets up buttons above the chess board
     * @return panel for buttons
     */
    private JPanel initializeButtonPanel() {
    	
    	JPanel myPanel = new JPanel();
    	myPanel.setPreferredSize(new Dimension(90,90));
    	myPanel.setLayout(null);
    	
    	whiteScoreLabel = new JLabel(Integer.toString(whiteScore));
    	whiteScoreLabel.setSize(40, 40);
    	whiteScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
    	whiteScoreLabel.setLocation(214, 12);
    	
    	JLabel doubleColon = new JLabel(":");
    	doubleColon.setSize(40, 40);
    	doubleColon.setFont(new Font("Arial", Font.BOLD, 30));
    	doubleColon.setLocation(248, 12);
    	
    	blackScoreLabel = new JLabel(Integer.toString(blackScore));
    	blackScoreLabel.setSize(40, 40);
    	blackScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
    	blackScoreLabel.setLocation(270, 12);
    	
    	whiteUserName = new JLabel("white");
    	whiteUserName.setSize(40, 40);
    	whiteUserName.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	whiteUserName.setLocation(150, 12);
    	
    	blackUserName = new JLabel("black");
    	blackUserName.setSize(40, 40);
    	blackUserName.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	blackUserName.setLocation(320, 12);
    	
    	restartButton = new JButton("restart");
    	restartButton.setSize(70, 30);
    	restartButton.setLocation(5, 50);
    	
    	forfeitButton = new JButton("forfeit");
    	forfeitButton.setSize(70, 30);
    	forfeitButton.setLocation(80, 50);
    	
    	undoButton = new JButton("undo");
    	undoButton.setSize(70, 30);
    	undoButton.setLocation(420, 50);
    	
    	message = new JLabel("");
    	message.setSize(200,40);
    	message.setLocation(220, 50);
    	message.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	
    	JLabel blackKing = getIcon("BLACK","King",20);
    	blackKing.setLocation(385, 19);
    	blackKing.setSize(20, 20);
    	
    	JLabel whiteKing = getIcon("WHITE","King",20);
    	whiteKing.setLocation(90, 15);
    	whiteKing.setSize(30, 30);
    	
    	myPanel.add(restartButton);
    	myPanel.add(forfeitButton);
    	myPanel.add(undoButton);
    	myPanel.add(whiteScoreLabel);
    	myPanel.add(blackScoreLabel);
    	myPanel.add(doubleColon);
    	myPanel.add(whiteUserName);
    	myPanel.add(blackUserName);
    	myPanel.add(blackKing);
    	myPanel.add(whiteKing);
    	myPanel.add(message);
    	return myPanel;
    }
    
    /**
     * @return window of the application
     */
    public JFrame getWindow() {
		return this.window;
	}
    
    /**
     * @return the menu item classic
     */
    public JMenuItem getMenuClassic() {
    	return this.classic;
    }
    
    /**
     * @return menu item custom
     */
    public JMenuItem getMenuCustom() {
    	return this.custom;
    }
    
    /**
     * @return name of white player
     */
    public String getWhitePlayer() {
    	return this.whitePlayer;
    }
    
    /**
     * @return name of black player
     */
    public String getBlackPlayer() {
    	return this.blackPlayer;
    }
    
    /**
     * @return JLabel of white side
     */
    public JLabel getWhiteUserName() {
    	return this.whiteUserName;
    }
    
    /**
     * @return JLabel of black side
     */
    public JLabel getBlackUserName() {
    	return this.blackUserName;
    }
    
    /**
     * @return JLabel of black score
     */
    public JLabel getBlackScore() {
    	return this.blackScoreLabel;
    }
    
    /**
     * @return JLabel of white score
     */
    public JLabel getWhiteScore() {
    	return this.whiteScoreLabel;
    }
    
    /**
     * @return JLabel of message
     */
    public JLabel getMessage() {
    	return this.message;
    }
    
    /**
     * @return JPanel of chess board
     */
    public JPanel getChessBoard() {
    	return this.chessBoard;
    }
    
    /**
     * @return JButton of undo 
     */
    public JButton getUndoButton() {
    	return this.undoButton;
    }
    
    /**
     * @return JButton of forfeit
     */
    public JButton getForfeitButton() {
    	return this.forfeitButton;
    }
    
    /**
     * @return JButton of restart
     */
    public JButton getRestartButton() {
    	return this.restartButton;
    }
}
