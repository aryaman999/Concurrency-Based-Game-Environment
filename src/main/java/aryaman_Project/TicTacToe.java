package aryaman_Project;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;

// Changes made to GUI is completely according to my preferences.
// Only GUI framework inspired from the tutorial for which the link has been provided.
// To complete this game documentations at Oracle Help Center was used.Link can be found-->(https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html).
/**
 * This class represents the logic and design of tic-tac-toe game.
 * 
 * The GUI frame work is inspired by a tutorial and changes have been made.
 * to suit my preferences as a user play the game.
 * Tutorial can be found at :(https://www.youtube.com/watch?v=n8RWUoBcn3E&ab_channel=TechProjects).
 * GUI design inspired from:https://chortle.ccsu.edu/java5/Notes/chap36new/ch36_3.html#:~:text=The%20setVisible(true)%20method%20makes,will%20appear%20on%20the%20screen.

 */
public class TicTacToe {

	// Private fields for GUI and game logic.

	private JFrame frame;
	private JTextField xScore;
	private JTextField oScore;
	private int xScore1=0;
	private int oScore1=0;
	private String startGame; // Represents current player "X" or "O"
	private int[] moves = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1};
	private JButton[] buttons = new JButton[9]; // arrays used to reference the nine buttons in my game

	/**
	 * Main method to launch the application.
	 * 
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe window = new TicTacToe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for the TicTacToe class.
	 */
	public TicTacToe() {
		initialize();
		showTicTacToeInstructions()
;	}
	
	
	/**
	 * This class represents a thread which perfroms background tasks when TicTacToe game is being played.
	 * The threads main objective is to handle player moves and determine winning player.
	 * ID of current thread is printed for debugging & testing purposes.
	 */
	private class GameThread extends SwingWorker<Void ,Void>{
		
		@Override
	    protected Void doInBackground() throws Exception {
			winningPlayer();
	        choosePlayer();
			return null;
	        
		}
		
	}

	/**
	 * Private method which is a very important method switches between "X" and "O" accordingly for the next move.
	 */
	private void choosePlayer() {
		startGame = (startGame== null || startGame.equalsIgnoreCase("X")) ? "O" : "X"; // ternary conditional operator used.
	}

	/**
	 * Method checks for various winning combinations for "X" and "O". 
	 * If "X" is the winner "X" score board is incremented and displays winning message. 
	 * If "O" is the winner "O" score board is incremented and displays winning message.
	 * If game ends in a draw, score is not incremented and draw message is displayed. 
	 */
	void winningPlayer() {
		boolean isDraw = true;

		// Player X. 
		if ((moves[0] == 1 && moves[1] == 1 && moves[2] == 1) ||
				(moves[3] == 1 && moves[4] == 1 && moves[5] == 1) ||
				(moves[6] == 1 && moves[7] == 1 && moves[8] == 1) ||
				(moves[0] == 1 && moves[3] == 1 && moves[6] == 1) ||
				(moves[1] == 1 && moves[4] == 1 && moves[7] == 1) ||
				(moves[2] == 1 && moves[5] == 1 && moves[8] == 1) ||
				(moves[0] == 1 && moves[4] == 1 && moves[8] == 1) ||
				(moves[2] == 1 && moves[4] == 1 && moves[6] == 1)) {
			JOptionPane.showMessageDialog(frame, "PLAYER X WINS", "Tic Tac Toe", JOptionPane.INFORMATION_MESSAGE);
			xScore1++;
			getxScore().setText(String.valueOf(xScore1));
			isDraw = false;
			disableButtons(List.of(buttons));
		}

		// Player O.
		else if ((moves[0] == 0 && moves[1] == 0 && moves[2] == 0) ||
				(moves[3] == 0 && moves[4] == 0 && moves[5] == 0) ||
				(moves[6] == 0 && moves[7] == 0 && moves[8] == 0) ||
				(moves[0] == 0 && moves[3] == 0 && moves[6] == 0) ||
				(moves[1] == 0 && moves[4] == 0 && moves[7] == 0) ||
				(moves[2] == 0 && moves[5] == 0 && moves[8] == 0) ||
				(moves[0] == 0 && moves[4] == 0 && moves[8] == 0) ||
				(moves[2] == 0 && moves[4] == 0 && moves[6] == 0)) {
			JOptionPane.showMessageDialog(frame, "PLAYER O WINS", "Tic Tac Toe", JOptionPane.INFORMATION_MESSAGE);
			oScore1++;
			getoScore().setText(String.valueOf(oScore1));
			isDraw = false;
			disableButtons(List.of(buttons));
		}

		//Checks for draw.
		if (isDraw && moves[0] != -1 && moves[1] != -1 && moves[2] != -1 &&
		        moves[3] != -1 && moves[4] != -1 && moves[5] != -1 &&
		        moves[6] != -1 && moves[7] != -1 && moves[8] != -1) {
		    JOptionPane.showMessageDialog(frame, "IT'S A DRAW!", "Tic Tac Toe", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	


	/**
	 * Method to disable specified buttons.
	 * 
	 * @param buttons the list of buttons to be disabled.
	 */
	void disableButtons(List<JButton> buttons) {
		for (JButton button : buttons) {
			button.setEnabled(false);
		}
	}

	/**
	 * Method to enable specified buttons.
	 * 
	 * @param buttons the list of buttons to be enabled.
	 */
	void enableButtons(List<JButton> buttons) {
		for (JButton button : buttons) {
			button.setEnabled(true);
		}
	}

	
	/**
	 * Handles the action when user clicks a button.
	 * Upon user interaction with button the button text,colour,move array and player turns are updated.
	 * 
	 * @param button, The JButton that was clicked.
	 * @param index, The index of button in moves array.
	 */
	private void buttonAction(JButton button, int index) {
		if (button.getText() == null || button.getText().isEmpty()) {
			button.setText(startGame);
			if (startGame.equalsIgnoreCase("X")) {
				button.setForeground(Color.BLACK);
				moves[index] = 1;
			}
			else {
				button.setForeground(Color.BLUE);
				moves[index] = 0;
			}

			if (isFirstMove()) {
				choosePlayer();
			} else {
				GameThread gameThread = new GameThread();
				gameThread.execute();
				}

			}
		}


	/**
	 * Checks for the first move of the game.
	 * 
	 * @return True if it's first move made by user else its false.
	 */
	private boolean isFirstMove() {
		for (int move : moves) {
			if (move != -1) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Initialise the game and the GUI interface.
	 */
	private void initialize() {
		// Main frame.
		frame = new JFrame();
		frame.setTitle("Tic-Tac-Toe");
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		// Random assigner to get random value to first player making move.
		Random randomAssigner = new Random();
		setStartGame((randomAssigner.nextBoolean()) ? "X" : "O");

		// Creates game board panel.
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 5, 2, 2));

		createGameBoard(panel);// populates game board with buttons which user can interact.
		
		// Panel for player scores.
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 4, 2, 2));

		// Player X label and score display.
		JLabel playerX = new JLabel("Player X");
		playerX.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		playerX.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(playerX);

		xScore = new JTextField();
		xScore.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		xScore.setHorizontalAlignment(SwingConstants.CENTER);
		xScore.setText("0");
		panel_1.add(xScore);
		xScore.setColumns(10);

		// Player O label and score display.
		JLabel playerO = new JLabel("Player O");
		playerO.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		playerO.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(playerO);

		oScore = new JTextField();
		oScore.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		oScore.setHorizontalAlignment(SwingConstants.CENTER);
		oScore.setText("0");
		panel_1.add(oScore);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		frame.getContentPane().add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		// Reset button with a action listener to reset game.
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}

			private void resetGame() {
				for (JButton button : buttons) {
					button.setText("");
				}

				// Resets all buttons.
				for (int i = 0; i < moves.length; i++) {
					moves[i] = -1;
				}

				enableButtons(List.of(buttons));
			}
		});
		btnReset.setBackground(new Color(255, 255, 255));
		btnReset.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		panel_2.add(btnReset, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		frame.getContentPane().add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		
		//Code inspiration link: https://www.tutorialspoint.com/swingexamples/show_confirm_dialog_with_yesno.htm.
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitGame();
			}

			private void exitGame() {
			    int user_option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
			    if (user_option == JOptionPane.YES_OPTION) {
			        frame.dispose();
			    }

			}
		});
		btnExit.setBackground(new Color(255, 255, 255));
		btnExit.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		panel_3.add(btnExit, BorderLayout.CENTER);
		xScore.setEditable(false); //Editable property set to false so user cannot modify it.
		oScore.setEditable(false);
	}

	/**
	 * Creates the Tic Tac Toe game board by initializing and adding buttons to the specified panel.
	 * 
	 * @param panel where the buttons will be added.
	 */
	private void createGameBoard(JPanel panel) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("");
			int creation = i;
			buttons[i].addActionListener(e -> buttonAction(buttons[creation], creation));// Use of lambda expressions to improve readability.This line of code was inspired from the link:https://www.w3schools.com/java/java_lambda.asp. 
			buttons[i].setFont(new Font("Tw Cen MT", Font.BOLD, 80));
			buttons[i].setBackground(new Color(255, 255, 255));
			panel.add(buttons[i]);
		}
	}
	
	//Use of JOptionPane which uses HTML inspired from:http://www.java2s.com/Code/JavaAPI/javax.swing/JOptionPanesetMessageObjectnewMessageHTMLmessage.htm.
	public void showTicTacToeInstructions() {
	    String instructions = "<html><strong>Welcome to Tic Tac Toe game!<br><br>"
	            + "<b>Objective:</b> The objective of Tic Tac Toe is to get three of your marks in a row (up, down, across, or diagonally) on a 3x3 grid.<br><br>"
	            + "<b>Setup:</b> The game is played on a grid that's 3 by 3 squares. One player is 'X', and the other player is 'O'. Players take turns putting their marks in empty squares.<br><br>"
	            + "<b>Moves:</b><br>"
	            + "- On your turn, place one of your marks in an empty square.<br>"
	            + "- The first player to get 3 of the marks in a row (up, down, across, or diagonally) is the winner.<br>"
	            + "- When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.<br><br>"
	            + "<b>Strategy:</b><br>"
	            + "- Try to win by getting three in a row.<br>"
	            + "- Block your opponent from getting three in a row.<br>"
	            + "- Create a strategy where you can win in two ways.<br><br>"
	            + "Enjoy the game!</html>";

	    JOptionPane.showMessageDialog(null, instructions, "Tic-Tac-Toe Instructions", JOptionPane.INFORMATION_MESSAGE);
	}


	//Getters and Setters down below for testing purposes as it can access private fields directly.

	// Getter for startGame field.
	
	/**
	 * Gets current symbol used to start game "X" or "O".
	 * 
	 * @return The symbol used to start game.
	 */
	public String getStartGame() {
		return startGame;
	}

	
	/**
	 * Sets the symbol used to start the game.
	 * 
	 * @param startGame symbol set as starting game symbol "X" or "O".
	 */
	public void setStartGame(String startGame) {
		this.startGame = startGame;
	}


	/**
	 * Gets the JTextField representing the score for Player X.
	 * 
	 * @return JTextField which represnts the score for Player X.
	 */
	public JTextField getxScore() {
		return xScore;
	}

	/**
	 * Sets the JTextField representing the score for Player X.
	 * 
	 * @param xScore to set score for Player "X".
	 */
	public void setxScore(JTextField xScore) {
		this.xScore = xScore;
	}

	/**
	 * Gets the JTextField representing the score for Player O.
	 * 
	 * @return JTextField which represnts the score for Player O.
	 */
	public JTextField getoScore() {
		return oScore;
	}

	/**
	 * Sets the JTextField representing the score for Player O.
	 * 
	 * @param oScore to set score for Player "O".
	 */
	public void setoScore(JTextField oScore) {
		this.oScore = oScore;
	}

	/**
	 * Gets array which represents moves made in the game.
	 * 
	 * @return An array of integers which represents moves made in the game.
	 */
	public int[] getMoves() {
		
		return moves;
	}

	
	/**
	 * @return the JFrame object which contains the entire user interface for the game.
	 */
	public JFrame getFrame() {
		return frame;
	}




}