package aryaman_Project;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 * Manages instances of the TicTacToe game. This class is responsible for creating, launching, 
 * and tracking multiple TicTacToe games. It utilizes a SwingWorker to ensure each game
 * runs in its own thread, allowing for concurrent gameplay without blocking the user interface.
 */
public class GameManager {
	private List<TicTacToe> games;

	/**
	 * Initializes a new GameManager instance. Prepares an empty list to track active TicTacToe games.
	 */
	public GameManager() {
		games = new ArrayList<>(); // Using array list to keep track of all the games created.
	}

	/**
	 * Creates a new instance of TicTacToe, adds it to the list of games, and launches the game
	 * in a separate thread using SwingWorker for asynchronous execution. This ensures that
	 * game initialization and execution does not block the Swing event dispatch thread, keeping the
	 * GUI responsive. When a game is closed, it is automatically removed from the list of active games.
	 */
	public void addGame() {
		TicTacToe newGame = new TicTacToe();
		games.add(newGame);


		//Inspired from:https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/SwingWorker.html.
		SwingWorker<Void, Void> gameWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				newGame.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				newGame.getFrame().setVisible(true);

				return null;

			}
			@Override
			protected void done() {
				games.remove(newGame); 
			}
		};

		gameWorker.execute(); // Executes the SwingWorker to launch the game asynchronously.

	}

}



