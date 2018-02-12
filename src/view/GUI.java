package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import chess.Board;
import chess.ChessPiece;
import chess.Game;
 
 
public class GUI implements ActionListener{
 
    /**
     * Constructor for chess game GUI
     * @param newGame
     */
    public GUI(Game newGame){
    	Board board = newGame.getBoard();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("New Chess Game");
        window.setSize(500, 600);
        
        JPanel buttonPanel = initializeButtonPanel();
        window.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        
        JPanel boardPanel = initializeBoardPanel(board);
        window.getContentPane().add(boardPanel, BorderLayout.CENTER);
       
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * This function sets up buttons above the chess board
     * @return panel for buttons
     */
    private JPanel initializeButtonPanel() {
    	
    	JPanel myPanel = new JPanel();
    	myPanel.setPreferredSize(new Dimension(90,90));
    	myPanel.setLayout(null);
    	
    	JButton newButton = new JButton("new");
    	newButton.setSize(70, 30);
    	newButton.setLocation(5, 30);
    	
    	JButton resignButton = new JButton("resign");
    	resignButton.setSize(70, 30);
    	resignButton.setLocation(80, 30);
    	
    	JButton restoreButton = new JButton("restore");
    	restoreButton.setSize(70, 30);
    	restoreButton.setLocation(420, 30);
    	
    	myPanel.add(newButton);
    	myPanel.add(resignButton);
    	myPanel.add(restoreButton);
    	return myPanel;
    }
    
 
    /**
     * @param board
     * @return panel for chess board
     */
    private JPanel initializeBoardPanel(Board board) {
        JPanel myPanel = new JPanel();
        //myPanel.setPreferredSize(new Dimension(400,400));
        myPanel.setLayout(new GridLayout(8,8));
        for(int i=0;i<64;i++) {
        	JPanel squarePanel = new JPanel();
        	squarePanel.setLayout(new GridLayout());
        	int rank = i/8;
        	int file = i%8;
        	if(rank%2 == file%2)squarePanel.setBackground(Color.gray);
        	else squarePanel.setBackground(Color.white);
        	ChessPiece piece = board.getPiece(rank, file);
        	if(piece!=null) {
        		String color;
        		int camp = piece.getCamp();
        		if(camp==1)color="WHITE";else color="BLACK";
        		String path = "/home/yihan/eclipse_cs242/chess/images/"+color+piece.getClass().toString().substring(12)+".png";
        		try {
        			Image image = ImageIO.read(new File(path)).
                            getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                   squarePanel.add(new JLabel(new ImageIcon(image)));
        		}catch(IOException e) {
        			System.out.println("Cannot find " + path);
        		}
        	}
        	myPanel.add(squarePanel);
        }
        return myPanel;
    }
 
    
    /**
     * This function sets up the menu bar
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("Mode");
        JMenuItem open = new JMenuItem("Normal");
        open.addActionListener(this);
        file.add(open);
        menubar.add(file);
        window.setJMenuBar(menubar);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                    "I was clicked by "+e.getActionCommand(),
                    "Title here", JOptionPane.INFORMATION_MESSAGE);
    }
 
   
}