package aryaman_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Represents the main interface for launching the game application. This class
 * initializes the main window from which users can choose to play different games
 * such as Tic-Tac-Toe or Chess. It creates a user-friendly GUI for game selection.
 */
public class MainInterface {
	private JFrame frame;
	private GameManager gameManager;


	/**
	 * Launches the application by setting up the main window and making it visible.
	 * It ensures that the window creation is done on the Event Dispatch Thread (EDT).
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructs the MainInterface, initializing the GameManager and setting up the GUI.
	 */
	public MainInterface() {
		gameManager = new GameManager();
		initialize();
	}

	/**
	 * Initialises the main window, setting its properties and layout. It also sets up
	 * the game selection interface, including buttons for launching Tic-Tac-Toe and Chess.
	 */
	private void initialize() {
		// New Frame.
		frame = new JFrame("Welcome to My Game Collection");
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JLabel welcomeLabel = new JLabel("Welcome, Please Select a Game to play!", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		frame.add(welcomeLabel, BorderLayout.NORTH);

		// Creates Jpanel.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, 10, 10));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		// New button for tic-tac-toe.
		//Added images to buttons with help of oracle documentation:https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/icon.html#:~:text=Many%20Swing%20components%2C%20such%20as,%2C%20JPEG%2C%20or%20PNG%20image.
		//Pictures taken from:https://www.cleanpng.com.
		JButton ticTacToeButton = new JButton(new ImageIcon("res/GUIImages/tic-tac-toe.png"));
		ticTacToeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameManager.addGame();
			}
		});
		panel.add(ticTacToeButton);

		// New button for chess game.
		JButton chessGameButton = new JButton(new ImageIcon("res/GUIImages/chessIcon.png"));
		chessGameButton.addActionListener(e -> launchChessGame());
		panel.add(chessGameButton);
		frame.setResizable(false);//Set resizable set to false as I don't want user to play on full screen and ensure conistent user experience.


	}

	/**
	 * Launches a new Chess game by creating a ChessGame instance and starting it on a new thread.
	 * This allows the Chess game to run independently of the main interface.
	 */
	public static void launchChessGame() {
		ChessGame game = new ChessGame();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}




}
