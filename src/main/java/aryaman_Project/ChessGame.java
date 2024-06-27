package aryaman_Project;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

/**
 * This class represents the main entry point for the Chess game application.
 * It sets up the game window, initializes the game components, and starts the game.
 * It utilises SwingUtilities invokeLater to ensure that all GUI updates are performed
 * in the Event Dispatch Thread (EDT), adhering to Swing's single-thread rule.
 * 
 */
public class ChessGame implements Runnable {
	/**
	 * Main method that serves as the entry point of the application.
	 * It schedules the application to be run using the Event Dispatch Thread (EDT)
	 * to ensure thread safety with Swing components.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ChessGame());// 	// Main class which handles chess game execution.

	}

	/**
	 * Sets up and displays the main game window for the chess game.
	 * It initialises the frame, sets its properties, adds a ChessPanel to it,
	 * and makes the frame visible to the user. This method is intended to be run on the EDT.
	 */
	public void run() {
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());// Adding new layout manager

		ChessPanel pn = new ChessPanel();
		frame.add(pn);// Add the ChessPanel to the frame

		frame.pack();// Resize frame to fit the preferred sizes of its subcomponents.
		frame.setVisible(true);// Set the frame to be visible
		frame.setMinimumSize(frame.getPreferredSize());// Set a minimum size to ensure the chessboard is fully visible.

		frame.setResizable(false);//Set resizable set to false as I don't want user to play on full screen and ensure conistent user experience.

		pn.runGame();
	}

	/**
	 * Utility method to launch a new game instance.
     * It schedules a new ChessGame instance to be started on the EDT.
	 */
	public static void launchNewGame() {
		SwingUtilities.invokeLater(new ChessGame());
	}
}
