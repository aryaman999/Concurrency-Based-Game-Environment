package aryaman_Project;



import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pieces.*;



/**
 * This classs serves as the main panel for chess game, it handles piece movement,
 * game rendering and game logic.It also extends JPanel and implements Runnable
 * for game loop management.
 */
@SuppressWarnings("serial")
public class ChessPanel extends JPanel implements Runnable  {

	public static final int WHITE = 1; // Identifier set to 1 for white piece.
	public static final int BLACK = 0; // Identifier set to 0 for black piece.

	// Constants set for board and buttons dimensions.This will be used to get preffered size for my ChessPanel.
	private static final int BOARD_WIDTH = ChessBoard.SQUARE_SIZE * 8;
	private static final int BOARD_HEIGHT = ChessBoard.SQUARE_SIZE * 8;
	private static final int BUTTONS_PANEL_WIDTH = 250; 
	private static final int BUTTONS_PANEL_HEIGHT = 200;
	private static boolean isGameClosed = false;

	// Game state variables
	int presentColor = WHITE; // Present color is white since the rule in chess says player with white pieces makes first move.
	private Piece chosenPiece; // Currently selected piece.
	public static Piece castlingPiece; // Piece involved in castling
	private Piece checkingPiece;// Piece which puts king in check
	boolean isStalemate; //Boolean flag to indicate if game is in stalemate.
	boolean gameFinished; //Boolean flag to indicate if game is finished.
	boolean validMove;// boolean variable to track if move played is valid.
	boolean validSquare;// boolean variable to track if se;ected square is valid.

	MouseController mouse = new MouseController(this);// Mouse controller to interact with pieces
	Thread gameThread;// Game thread which handles game loop.
	ChessBoard board = new ChessBoard();


	// Timer components for tracking time
	private Timer whiteTimer;
	private Timer blackTimer;
	private JLabel whiteTimerLabel;
	private JLabel blackTimerLabel;
	//I found 10 minute timer the best possbile way to start.It's inspired from rapid mode in chess.com, link:https://www.chess.com/play/online.
	private int whiteTimeRemaining = 600; // 10 minutes in seconds(60s x 10)
	private int blackTimeRemaining = 600; // 10 minutes in seconds



	/**
	 * Constructor initializes the chess panel including UI components and game setup.
	 * Resources used from Oracle Documentation for MouseListener:https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/java/awt/event/MouseListener.html and,
	 * Oracle Documentation for MouseMotionListener:https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseMotionListener.html
	 */
	public ChessPanel() {
		setupUI();
	}


	/**
	 * Sets up the user interface including buttons, timers, and instructions.
	 */
	private void setupUI() {
		initializeUI(); //Initialization of buttons and labels for the user interface
		initPlayerTimers();
		showChessInstructions();
		mouseListener();
		startWhiteTimer();
	}

	/**
	 * Adds mouse listeners to this panel for handling piece selection and movement.
	 */
	private void mouseListener() {
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	//Timer related methods:

	// This method was constructed with help of formum page:https://stackoverflow.com/questions/1006611/java-swing-timer.
	// I also used 	oracle documentation to help me implment swing timer into my game:https://docs.oracle.com/javase/8/docs/api/javax/swing/Timer.html.
	//As suggested in the steps in stack overflow I had to:
	//1) create the actionlistener
	//2) create the timer constructor then pass time and actionlistener in that.
	//3)implement the actionPerformed() function in which do your task.
	//4) use timer.start() for start the task between the time specified in timer constructor, use timer.stop() for stop the task.

	/**
	 * Initializes the timers for both white and black players, setting the countdown and defining actions
	 * when the timer ticks and when it runs out. Prompts for a game restart upon time expiration.
	 */
	private void initPlayerTimers() {
		//Initializes the timers for both players.
		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == whiteTimer) {
					whiteTimeRemaining--;
					updateTimer(whiteTimerLabel, whiteTimeRemaining);
					if (whiteTimeRemaining <= 0) {
						whiteTimer.stop();
						promptRestart("Black wins! Time's up for White. Do You Want To Play again?");
					}
				} else if (e.getSource() == blackTimer) {
					blackTimeRemaining--;
					updateTimer(blackTimerLabel, blackTimeRemaining);
					if (blackTimeRemaining <= 0) {
						blackTimer.stop();
						promptRestart("White wins! Time's up for Black. Do You Want To Play again?");

					}
				}
			}


		};

		// Timer for white and black player, triggers every second.
		whiteTimer = new Timer(1000, timerListener);
		blackTimer = new Timer(1000, timerListener);		
	}

	/**
	 * Prompts the user with an option to restart the game or exit after the game is over.
	 * This method is called when a game ends by timer running out.
	 * 
	 * @param message The message to be displayed in the dialog box, indicating the reason for the game ending and asking if the user wants to play again.
	 */
	public void promptRestart(String message) {
		int userOption = JOptionPane.showConfirmDialog(null, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (userOption == JOptionPane.YES_OPTION) {
			resetGame();
		} else {
			System.exit(0); // This will close the application
		}
	}


	/**
	 * Starts the timer for the white player, indicating the beginning of their turn.
	 */
	private void startWhiteTimer() {
		whiteTimer.start();//Start the timer for the white player as they play make first move.
	}





	/**
	 * Updates the display of the timer label to show the current remaining time.
	 * Formats the time into minutes and seconds.
	 *
	 * @param label The JLabel to update with the remaining time.
	 * @param timeRemaining The time remaining in seconds.
	 */
	private void updateTimer(JLabel label, int timeRemaining) {
		int minutes = timeRemaining / 60;
		int seconds = timeRemaining % 60;
		String labelTitle;
		if(label == whiteTimerLabel){
			labelTitle = "White Time: ";
		}else{
			labelTitle = "Black Time: ";
		}

		String time = labelTitle + String.format("%02d:%02d", minutes, seconds);
		label.setText(time);// Passing label as a string to format the integers into string.Inside %d prints the integer and number indicates how long the digits will be.

	}



	@Override
	public Dimension getPreferredSize() {
		//Returns preffered dimensions.Code developed with the help from website link:https://www.geeksforgeeks.org/java-math-max-method-examples/.
		return new Dimension(BOARD_WIDTH + BUTTONS_PANEL_WIDTH, Math.max(BOARD_HEIGHT, BUTTONS_PANEL_HEIGHT));
	}

	/**
	 * Sets up the layout and components of the user interface, including buttons and timer labels.
	 */
	private void initializeUI() {
		this.setLayout(new BorderLayout()); // Added a new layout for my Chess panel.
		JPanel buttonsPanel = new JPanel(); //Initializing new Jpanel for buttons.
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically for better GUI appearance.

		JButton showRulesButton = new JButton("Show Chess Rules");
		showRulesButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		showRulesButton.addActionListener(e -> showChessRules());
		buttonsPanel.add(showRulesButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Used help from follwoing link to create space between two buttons:https://stackoverflow.com/questions/8335997/how-can-i-add-a-space-in-between-two-buttons-in-a-boxlayout.

		//Special rules information taken from:https://www.chess.com/learn-how-to-play-chess
		JButton showSpecialRulesButton = new JButton("Show Special Chess Rules");
		showSpecialRulesButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		showSpecialRulesButton.addActionListener(e -> showSpecialRules());
		buttonsPanel.add(showSpecialRulesButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Used help from follwoing link to create space between two buttons:https://stackoverflow.com/questions/8335997/how-can-i-add-a-space-in-between-two-buttons-in-a-boxlayout.

		//New Reset Game button with action listener to reset the game state so user start again.
		JButton resetButton = new JButton("Reset Game");
		resetButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		resetButton.addActionListener(e -> resetGame());
		buttonsPanel.add(resetButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Used help from follwoing link to create space between two buttons:https://stackoverflow.com/questions/8335997/how-can-i-add-a-space-in-between-two-buttons-in-a-boxlayout.

		//New create game button with action listener to create a new instance of chess game.
		JButton createNewGameButton = new JButton("Create New Game");
		createNewGameButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		createNewGameButton.addActionListener(e -> createNewGame());
		buttonsPanel.add(createNewGameButton);

		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Used help from follwoing link to create space between two buttons:https://stackoverflow.com/questions/8335997/how-can-i-add-a-space-in-between-two-buttons-in-a-boxlayout.
		this.add(buttonsPanel, BorderLayout.EAST);


		// Initialize timer labels.
		whiteTimerLabel = new JLabel("White Time: 10:00");
		whiteTimerLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		blackTimerLabel = new JLabel("Black Time: 10:00");
		blackTimerLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		buttonsPanel.add(whiteTimerLabel);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Used help from follwoing link to create space between two buttons:https://stackoverflow.com/questions/8335997/how-can-i-add-a-space-in-between-two-buttons-in-a-boxlayout.
		buttonsPanel.add(blackTimerLabel);
	}


	/**
	 * Displays an instruction dialog to the player with an overview of chess rules and how to play the game.

	 */
	public void showChessInstructions() {
		//Chess rules applied from:https://www.chess.com/learn-how-to-play-chess.
		//Use of JOptionPane which uses HTML inspired from:http://www.java2s.com/Code/JavaAPI/javax.swing/JOptionPanesetMessageObjectnewMessageHTMLmessage.htm.
		String instructions = "<html>Welcome to my Chess game!<br><br>"
				+ "Objective: The objective of chess is to checkmate your opponent's king. This happens when the king is in a position to be captured (in \"check\") and there is no way for it to escape.<br><br>"
				+ "<b>Setup:</b> The game is set up on an 8x8 board with each player controlling 16 pieces: one king, one queen, two rooks, two knights, two bishops, and eight pawns. The pieces are set up in the same way each game.<br><br>"
				+ "<b>How to Play: Use your mouse to hold and drag the pieces.When you have made your decision release the mouse to move the piece.</b><br>"

				+ "<b>Moves:</b><br>"
				+ "- Pawns move forward one square, but on their first move, they can choose to advance two squares. Pawns captures diagonally.<br>"
				+ "- Rooks move horizontally or vertically any number of squares.<br>"
				+ "- Knights move in an L-shape: two squares in one direction and then one square perpendicular, or one square in one direction and then two squares perpendicular.<br>"
				+ "- Bishops move diagonally any number of squares.<br>"
				+ "- The Queen can move any number of squares along a row, column, or diagonal.<br>"
				+ "- The King moves one square in any direction.<br><br>"
				+ "<b>Special Moves:</b><br>"
				+ "- Castling, En Passant, Pawn Promotion.<br><br>"
				+ "End of Game: The game ends by checkmate, stalemate, draw by agreement, or if a player resigns.<br><br>"
				+ "Enjoy the game!</html>";
		JOptionPane.showMessageDialog(null, instructions, "Chess Instructions", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays a dialog box containing the basic rules of chess. Triggered by pressing the "Show Chess Rules" button.
	 */
	private void showChessRules() {
		JTextArea rulesArea = new JTextArea(30, 30);
		rulesArea.setText("Chess Rules:\n"
				+ "Goal: The goal is to checkmate the opponent's king.\n"
				+ "Each type of piece moves differently...\n"
				+ "- Pawns move forward one square, but on their first move, they can choose to advance two squares. Pawns captures diagonally.\n"
				+ "- Rooks move horizontally or vertically any number of squares.\n"
				+ "- Knights move in an L-shape: two squares in one direction and then one square perpendicular, or one square in one direction and then two squares perpendicular.\n"
				+ "- Bishops move diagonally any number of squares.\n"
				+ "- The Queen can move any number of squares along a row, column, or diagonal.\n"
				+ "- The King moves one square in any direction.\n"
				+ "Special Moves: Castling, En Passant, Pawn Promotion\n");

		rulesArea.setEditable(false);
		rulesArea.setWrapStyleWord(true);
		rulesArea.setLineWrap(true);
		JOptionPane.showMessageDialog(this, rulesArea, "Read Chess Rules", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays a dialog box containing special chess rules, such as Castling, En Passant, and Pawn Promotion.
	 * Triggered by pressing the "Show Special Chess Rules" button.
	 */
	private void showSpecialRules() {
		JTextArea specialRulesArea = new JTextArea(30, 30);
		specialRulesArea.setText("Special Chess Rules:\n"
				+ "- Castling: Move the king two squares towards a rook, then move that rook to the square next to the king. Conditions: No previous moves by the king and rook, no pieces between them, and no check.\n"
				+ "- En Passant: If an opponent's pawn moves two squares forward from its initial position and ends next to your pawn, you can capture it as if it moved one square. This must be done immediately.\n"
				+ "- Pawn Promotion: When a pawn reaches the far side of the board, it can be promoted to a queen, rook, bishop, or knight.\n"
				);
		specialRulesArea.setEditable(false);

		specialRulesArea.setWrapStyleWord(true);
		specialRulesArea.setLineWrap(true);

		JOptionPane.showMessageDialog(this, specialRulesArea, "Read Special Chess Rules", JOptionPane.INFORMATION_MESSAGE);

	}


	//Game Logic related methods

	/**
	 * Initiates the creation of a new game instance, effectively restarting the game.
	 */
	private void createNewGame() {
		ChessGame.launchNewGame();

	}

	/**
	 * Resets the game to its initial state. This includes resetting the timer, board state, and any game flags.
	 */
	public void resetGame() {
		// Resets the game to its initial state, including resetting all pieces and game flags.
		board.resetChessBoard();
		presentColor = WHITE;
		checkingPiece = null;
		gameFinished = false;
		validMove = false;
		validSquare = false;
		chosenPiece = null;
		castlingPiece = null;
		whiteTimeRemaining = 600; // Reset to initial value, e.g., 10 minutes
		blackTimeRemaining = 600; // Reset to initial value, e.g., 10 minutes
		// Update timer labels
		updateTimer(whiteTimerLabel, whiteTimeRemaining);
		updateTimer(blackTimerLabel, blackTimeRemaining);

		// Start the white timer and stop the black timer as white starts the game
		whiteTimer.start();
		blackTimer.stop();

		// Repaint the board to reflect the reset state
		repaint();
	}


	/**
	 * Runs game loop in a new thread.
	 */
	public void runGame() {
		gameThread = new Thread(this);
		gameThread.start(); //Instantiating the thread
	}

	/**
	 * Sets boolean flag as true when game is closed and stops the timers.
	 */
	public void stopGame() {
		isGameClosed = true;
		blackTimer.stop();
		whiteTimer.stop();
	}

	/**
	 * Updates the state of the game by handling piece movement and player turn switching.
	 */
	public void update() {
		if(mouse.pressed) {
			if(chosenPiece == null) {
				for(Piece piece : board.getChessPieces()) {
					if(piece.color == presentColor &&
							piece.column == mouse.x/ChessBoard.SQUARE_SIZE &&
							piece.row == mouse.y/ChessBoard.SQUARE_SIZE){
						chosenPiece = piece;
					}	
				}
			}else {
				updateMove();
			}
		} else if(chosenPiece!=null && validMove && !(illegalKingMove(chosenPiece) || illegalAllyMove())) {
			int targetColumn = chosenPiece.getColumn(chosenPiece.x);
			int targetRow = chosenPiece.getRow(chosenPiece.y);
			Piece capturedPiece = chosenPiece.getCapturedPiece(targetColumn, targetRow);

			if (capturedPiece != null) {
				board.getChessPieces().remove(capturedPiece);
			}
			//Casting learnt from:/https://ioflood.com/blog/java-casting/
			if(chosenPiece instanceof Pawn && ((Pawn) chosenPiece).capturedPiece != null && ((Pawn) chosenPiece).capturedPiece.enPassantEligible) {
				board.getChessPieces().remove(((Pawn) chosenPiece).capturedPiece);

			}

			if (isKingInCheck(false)) {
				chosenPiece.resetPosition();
				board.getChessPieces().add(capturedPiece);
				chosenPiece = null;
				validMove = false;
				return;
			}

			chosenPiece.UpdatePiecePosition();


			if(isCheckmate()) {
				gameFinished = true;

			}

			if(canPromote()) {
				promotePawnPiece(chosenPiece);
			}

			switchPlayerTurn(); // switches player turn
			validMove = false;  //Resetting for next turn made by user
			chosenPiece = null; //Resetting for next turn made by user
			if(isCheckmate() || isStalemate()) {
				gameFinished = true;
				blackTimer.stop();
				whiteTimer.stop();
			}
		} else if (chosenPiece != null){
			chosenPiece.resetPosition();
			chosenPiece = null;
		}



	}



	/**
	 * Promotes pawn piece after reaching opposite board edge.
	 * @param pawn The pawn piece eligible for promotion.
	 */
	private void promotePawnPiece(Piece pawn) {
		Piece promotedPiece = showPromotionDialog(this, pawn.color);
		board.getChessPieces().remove(pawn);
		board.getChessPieces().add(promotedPiece);
	}

	/**
	 * Displays a dialog for user to choose a piece for pawn promotion.
	 * This method was updated to display images to user with the help of oracle documentation: https://docs.oracle.com/javase/8/docs/api/javax/swing/ImageIcon.html.
	 * @param parent The parent component for the dialog.
	 * @param color The color of the pawn being promoted.
	 * @return The new selected piece chosen by user.
	 */

	public Piece showPromotionDialog(Component parent, int color) {
		// Path to my chess piece images.
		String pathToImages = "/pieces/";

		//Terenary operator added using resources on:https: www.w3schools.com/java/java_conditions_shorthand.asp.
		String colorOperator = (color == ChessPanel.WHITE) ? "w-" : "b-"; // Use of ternary conditional operator create shorter version of if-else statement.   

		ImageIcon queenImage = new ImageIcon(getClass().getResource(pathToImages + colorOperator + "queen.png"));
		ImageIcon rookImage = new ImageIcon(getClass().getResource(pathToImages + colorOperator + "rook.png"));
		ImageIcon bishopImage = new ImageIcon(getClass().getResource(pathToImages + colorOperator + "bishop.png"));
		ImageIcon knightImage = new ImageIcon(getClass().getResource(pathToImages + colorOperator + "knight.png"));

		// Object array holding the images
		Object[] choices = { queenImage, rookImage, bishopImage, knightImage };
		int showPromotions = JOptionPane.showOptionDialog(
				parent,
				"Choose piece for promotion:",
				"Pawn Promotion",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				choices,
				choices[0]);

		switch (showPromotions) {
		case 0: return new Queen(board, chosenPiece.column, chosenPiece.row, color);
		case 1: return new Rook(board,chosenPiece.column, chosenPiece.row, color);
		case 2: return new Bishop(board,chosenPiece.column, chosenPiece.row, color);
		case 3: return new Knight(board,chosenPiece.column, chosenPiece.row, color);
		default: return new Queen(board,chosenPiece.column, chosenPiece.row, color); // Default promotion set to queen as it's the highest rank and making any other piece a default would mean a underpromotion.
		}
	}




	/**
	 * Updates move based on current mouse positioning and selected piece.
	 */
	public void updateMove() {

		validMove = false;
		validSquare = false;


		chosenPiece.x = mouse.x - ChessBoard.HALF_SQUARE;
		chosenPiece.y = mouse.y - ChessBoard.HALF_SQUARE;
		chosenPiece.column = chosenPiece.getColumn(chosenPiece.x);
		chosenPiece.row = chosenPiece.getRow(chosenPiece.y);


		if(castlingPiece != null) {
			castlingPiece.column = castlingPiece.previousColumn;
			castlingPiece.x = castlingPiece.getX(castlingPiece.column);
			castlingPiece = null;
		}

		if(chosenPiece.canMove(chosenPiece.column, chosenPiece.row)) {
			validMove = true;
			validSquare = true;
			adjustRookPositionForCastling();
			if(!illegalKingMove(chosenPiece)&& !illegalAllyMove()){ 
				validSquare = true;
			}

		}




	}


	/**
	 * Adjusts the position of a rook during a castling move. This method is called when a valid castling move is detected.

	 */
	private void adjustRookPositionForCastling() {
		if (castlingPiece == null) {
			return;
		}
		switch (castlingPiece.column) {
		case 0: // Left side rook for Queen side castling
			castlingPiece.column += 3; // Position next to the king after castling
			break;
		case 7: // Right side rook for King side castling
			castlingPiece.column -= 2; // Position next to the king after castling
			break;
		}
		castlingPiece.x = castlingPiece.getX(castlingPiece.column); // Update rook position.


	}


	/**
	 * Switches the turn to other player.
	 */
	public void switchPlayerTurn() {
		if(presentColor == WHITE) {
			presentColor = BLACK;
			whiteTimer.stop(); // Stop white timer when black piece's turn.
			blackTimer.start();// Start black timer when it's black piece's turn.
			for(Piece piece : board.getChessPieces()) {
				if(piece instanceof Pawn){
					if(piece.color == BLACK){
						piece.enPassantEligible = false; // Resets En Passant eligibility
					}
				}
			}
		}

		else {
			presentColor = WHITE;

			blackTimer.stop(); // Stop black timer when white piece's turn.
			whiteTimer.start();// Start white timer when white piece's turn.
			for(Piece piece : board.getChessPieces()) {
				if(piece instanceof Pawn){
					if(piece.color == WHITE){
						piece.enPassantEligible = false; // Resets En Passant eligibility
					}
				}
			}
			;
		}
		chosenPiece = null;
		repaint();
	}


	/**
	 * Check if the pawn piece can be promoted.
	 * @return true if pawn can be promoted and false if can't be promoted.
	 */
	public boolean canPromote() {
		if (chosenPiece != null) {
			if(chosenPiece.type == Type.PAWN) {
				if((chosenPiece.color == ChessPanel.WHITE && chosenPiece.row == 0) || (chosenPiece.color == ChessPanel.BLACK && chosenPiece.row == 7)) {
					return true;

				}
			}
		}
		return false;
	}	

	@Override
	public void run(){
		while(!isGameClosed){
			if (!gameFinished) update();
			repaint();
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}



	/**
	 * Checks if the current player's king is in a check position. This method scans for threats to the king from opponent pieces.
	 *
	 * @param opponent Indicates whether to check for the opponent's king instead of the current player's king.
	 * @return true if the king is in check, false otherwise.
	 */
	boolean isKingInCheck(boolean opponent) {
		Piece king = getKing(opponent); // false to get the current player's king
		if (king == null) {
			return false;
		}
		for (Piece piece : board.getChessPieces()) {
			if (piece.color != presentColor && piece.canMove(king.column, king.row)) {
				checkingPiece = piece;
				return true;
			}
		}
		checkingPiece = null;
		return false;
	}

	/**
	 * Determines if a move would place the king in check. This method is used to simulate
	 * the outcome of a move without actually making it, particularly useful in scenarios
	 * like checking for checkmate or validating moves that could potentially put the king in danger.
	 *
	 * @param king The king piece to check for potential threats after a hypothetical move.
	 * @return true if the king would be in check after the move, false otherwise.
	 */
	boolean willKingBeInCheck(Piece king) {
		if (king == null) {
			return false;
		}
		for (Piece piece : board.getChessPieces()) {
			if (piece.color != presentColor && piece.canMove(king.column, king.row)) {
				checkingPiece = piece;
				//System.out.println("King"+king+" is in check by " + piece);
				return true;
			}
		}
		checkingPiece = null;
		return false;
	}

	//This method finds and returns the king piece for the current player or their opponent on a chessboard.
	/**
	 * @param opponentPiece
	 * @return
	 */
	private Piece getKing(boolean opponentPiece) {
		Piece king = null;//Inital king peice set to null.
		for(Piece piece: board.chessPieces) {// For loop used to iterate over all pieces checking for king based of opponentPiece flag.
			if(opponentPiece) {//If true it searches for opponent's king piece.
				if(piece.type == Type.KING && piece.color != presentColor) {
					king = piece;
				}
			}
			else{// Looks for current players king piece if flag is false.
				if(piece.type == Type.KING && piece.color == presentColor) {
					king = piece;//returns found king piece.
				}

			}

		}
		return king;//Returns null if no king is found.
	}



	//Illegal king move learnt from:https://redknightchess.com/illegal-moves/#:~:text=%E2%80%93%20Moving%20the%20King%20on%20a,is%20not%20allowed%20to%20move.
	// Checks if moving a king piece on a square results in a situation where king is under direct threat from opposing piece.
	/**
	 * Verifies if a move is illegal due to it either placing or leaving the king in check.
	 *
	 * @param king The king piece to check for illegal moves.
	 * @return true if the move is illegal, false otherwise.
	 */
	public boolean illegalKingMove(Piece king){
		if (king == null) {
			return false;
		}
		if(king.type == Type.KING){
			for(Piece piece : board.chessPieces){
				if(piece.canMove(king.column,  king.row) && piece != king && piece.color != king.color) {
					return true;
				}
			}
		}
		return false;
	}

	//Checkmate detection logic:

	// Checkmate information taken from https://www.chess.com/terms/checkmate-chess
	// Reading upon checkmate I have come up with set of rules I need to implement to ensure I can succesfully implement checkmate logic.
	// Rule 1: The King cannot move to a square where it is not under attack.
	// Rule 2: If the King or its ally can capture the checking piece.
	// Rule 3: An ally can be placed between the King and the checking piece.

	//SHOULD SATISFY RULE 1

	//To generate legal moves for king I followed the suggestions given in the forum link:https://stackoverflow.com/questions/24132096/chess-generate-possible-moves-king-safe
	/**
	 * Checks for any legal and safe moves available for the king. This method is critical
	 * for determining checkmate and stalemate conditions by assessing if the king can move
	 * to a position that is not under threat from opponent pieces. It simulates each potential
	 * move of the king to ensure the move does not result in a check.
	 *
	 * @param king The king piece whose potential safe moves are to be evaluated.
	 * @return true if at least one safe move is available for the king, meaning the move
	 * does not put the king in check; false if no such moves exist, indicating a possible
	 * checkmate or stalemate condition.
	 */
	public boolean hasSafeMoves(Piece king) {
		//System.out.println("Checking safe moves for King at: " + king.column + ", " + king.row);
		for (int columnOffset = -1; columnOffset <= 1; columnOffset++) { // Iterate over all possible moves horizontally.
			for (int rowOffset = -1; rowOffset <= 1; rowOffset++) { // Iterate over all possible moves vertically.
				int newColumn = king.column + columnOffset;
				int newRow = king.row + rowOffset;
				// Ensure new position is within the board.
				if (newColumn >= 0 && newColumn < 8 && newRow >= 0 && newRow < 8) {
					// Clone king to simulate the move.
					Piece temp = king.clone();
					temp.column = newColumn;
					temp.row = newRow;
					//System.out.println("Testing move to: " + newColumn + ", " + newRow);
					if (temp.canMove(newColumn, newRow)) {
						//System.out.println("Move is within legal movement rules.");
						if (!willKingBeInCheck(temp)) {
							//System.out.println("This move is safe from checks.");
							return true; // King has at least one safe move.
						} else {
							//System.out.println("This move would place the King in check.");
						}
					} else {
						//System.out.println("Move is not within legal movement rules.");
					}
				}
			}
		}
		//System.out.println("No safe moves available for the King.");
		return false; // No safe moves available.
	}

	//SHOULD SATISFY RULE 3
	/**
	 * Evaluates if any ally piece, other than the king, can block the check by moving to a specific square
	 * or capture the checking piece. This method is crucial for assessing the king's safety and determining
	 * the viability of the current game state, especially in situations of check or checkmate. It iterates
	 * through all potential moves of each ally piece to see if the move can block the opponent's check or
	 * capture the threatening piece, there by potentially rescuing the king from check.
	 *
	 * @param king The king piece currently in check or being evaluated for potential checks.
	 * @return true if an ally piece can legally move to block the check against the king or capture the checking piece,
	 * thus resolving the check situation. Returns false if no such ally piece move can resolve the check.
	 */
	public boolean canBlockOrCaptureCheckingPiece(Piece king) {
		// Iterate over all chess pieces on the board.
		for (int index = 0; index < board.getChessPieces().size(); index++) {
			Piece piece = board.getChessPieces().get(index);
			// Check if the piece is an ally of the king
			if (piece.color == king.color) {
				// Iterate over all squares on the chess board to explore possible moves
				for (int column = 0; column < 8; column++) {
					for (int row = 0; row < 8; row++) {
						// Check if the ally piece can legally move to the current square
						if (piece.canMove(column, row)) {
							// If the square is occupied by the checking piece, capturing it would resolve the check.
							if (checkingPiece.row == row && checkingPiece.column == column) {
								// Debug print statement for capturing the checking piece.
								//System.out.println(piece.type + " Can capture checking piece " + piece.row + "," + piece.column);
								return true;
							}
							// Clone the piece to simulate the move without affecting the game state.
							Piece temp = piece.clone(); // Clone method is used to avoid mutating the original piece object.
							temp.column = column;
							temp.row = row;
							// Temporarily move the piece in the simulation.
							board.getChessPieces().set(index, temp);
							// Check if moving the piece resolves the check against the king.
							if (!isKingInCheck(false)) {
								// Debug print statement for a move that blocks the check or captures the checking piece.
								//System.out.println(temp.type + " Can block or capture checking piece " + temp.row + "," + temp.column);
								// Restore the original piece after the simulation.
								board.getChessPieces().set(index, piece);
								return true; // The check can be resolved by this piece's move.
							}
							// Restore the original piece if the simulated move does not resolve the check.
							board.getChessPieces().set(index, piece);
						}
					}
				}
			}
		}
		return false; // Return false if no piece can block the check or capture the checking piece.
	}


	/**
	 * Detects if the current game state is a checkmate, meaning the current player's king is in check with no legal moves to escape.
	 *
	 * @return true if the game is in a checkmate state, false otherwise.
	 */
	public boolean isCheckmate() {
		Piece king = getKing(false);
		if(isKingInCheck(false)) {// If king is not in check and if ally piece cannot block or capture checkingPiece,it is considered a checkmate.
			if(!canBlockOrCaptureCheckingPiece(king) && !hasSafeMoves(king)) {
				//System.out.println("Checkmate!");
				return true;
			}
		}
		return false;//Not a checkmate
	}

	/**
	 * Checks if the game is in a stalemate condition for the current player.
	 * Stalemate is a situation in chess where the player whose turn it is to move is not in check but has no legal move. 
	 * The rules of chess provide that when stalemate occurs, the game ends as a draw.
	 *
	 * In this method, a stalemate condition is identified by checking if the current player
	 * has only one piece left on the board (presumably the King, as it cannot be captured), 
	 * and if that piece has no legal moves without putting itself into check.
	 *
	 * @return true if the current player is in stalemate condition, otherwise return false.
	 */
	private boolean isStalemate() {
		int pieceCount = 0; // Initialize a counter for the number of pieces the current player has on the board.
		// Log the start of a stalemate check for debugging or informational purposes.
		//System.out.println("Checking for stalemate for ".concat((presentColor==WHITE)?"White":"Black"));
		for(Piece piece : board.chessPieces) { // Counts the current player's pieces on the board.

			if(piece.color == presentColor) {
				pieceCount++;
			}
		}
		//System.out.println("Piece count: "+pieceCount); // Log the piece count for informational purposes.

		// Keeps count of number of pieces current player has on board.
		if(pieceCount == 1) { // If the current player has only one piece left (the King), check for legal moves.

			if(!hasSafeMoves(getKing(false))){ // Retrieve the current player's king and check if it has any legal moves.

				isStalemate = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if any ally move results in an illegal game state, specifically focusing on whether 
	 * making a move would leave the king in check. This method is essential for validating moves 
	 * before they are made, ensuring that the move does not put the player's own king into check,
	 * which is not allowed in chess. It first checks if the chosen piece is directly moving into
	 * the position of the checking piece, which would be a legal capture move. Then, it iterates 
	 * through all opponent pieces to see if any can still move to the king's position after the 
	 * proposed ally move, indicating the move is illegal as it would leave or place the king in check.
	 *
	 * @return true if the proposed move is illegal because it leaves the king in check or does not resolve
	 * an existing check. Returns false if the move is legal, meaning it does not result in the king being in check.
	 */
	public boolean illegalAllyMove() {
		Piece king = getKing(false);
		if(chosenPiece != null && checkingPiece != null) {//Initial Check for Chosen and Checking Piece
			if(chosenPiece.row == checkingPiece.row && chosenPiece.column == checkingPiece.column) {
				return false;
			}
		}
		for(Piece piece : board.chessPieces) {// Iterates through all pieces on the board to scan for threats to king piece.
			if(piece.color != king.color && piece.canMove(king.column,king.row)){
				return true; //Illegal move
			}
		}
		return false;//move is legal if returns false.
	}
	//Paint related methods

	/**
	 * Custom paint method for the chess panel. It handles drawing the chess board,
	 * pieces, and highlighting valid or invalid moves. Additionally, it displays
	 * messages for game states like check, checkmate, or stalemate.
	 *
	 * @param g The Graphics context used for drawing.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		board.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		for (Piece piece : board.getChessPieces()) {
			g.drawImage(piece.image, piece.x, piece.y, null);
		}

		// Highlighting logic for valid and invalid moves
		if (chosenPiece != null) {
			if (validMove) {
				if (illegalKingMove(chosenPiece)|| illegalAllyMove()) {
					g2d.setColor(Color.red);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2d.fillRect(chosenPiece.column * ChessBoard.SQUARE_SIZE, chosenPiece.row * ChessBoard.SQUARE_SIZE,
							ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				} else {
					g2d.setColor(Color.blue);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2d.fillRect(chosenPiece.column * ChessBoard.SQUARE_SIZE, chosenPiece.row * ChessBoard.SQUARE_SIZE,
							ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}

			}
			else {
				g2d.setColor(Color.red);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2d.fillRect(chosenPiece.column * ChessBoard.SQUARE_SIZE, chosenPiece.row * ChessBoard.SQUARE_SIZE,
						ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


			}



		}

		if (isStalemate) {
			String stalemateMessage = "Stalemate!";
			g2d.setColor(Color.red);
			g2d.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
			int messageWidth = g2d.getFontMetrics().stringWidth(stalemateMessage);//Adjusted font to centre of chessboard using stackoverflow forum page:https://stackoverflow.com/questions/61823828/java-2d-vertically-centering-text.
			int messageHeight = g2d.getFontMetrics().getHeight();
			int xPos = (BOARD_WIDTH - messageWidth) / 2;//Simple calculations to get x & y positions.
			int yPos = (BOARD_HEIGHT - messageHeight) / 2 + messageHeight;
			g2d.drawString(stalemateMessage, xPos, yPos);
		} else if(gameFinished) {
			if(presentColor == BLACK) {
				String winningMessage = "White Wins!";
				g2d.setColor(Color.red);
				g2d.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
				int messageWidth = g2d.getFontMetrics().stringWidth(winningMessage);//Adjusted font to centre of chessboard using stackoverflow forum page:https://stackoverflow.com/questions/61823828/java-2d-vertically-centering-text.
				int messageHeight = g2d.getFontMetrics().getHeight();
				int xPos = (BOARD_WIDTH - messageWidth) / 2;//Simple calculations to get x & y positions.
				int yPos = (BOARD_HEIGHT - messageHeight) / 2 + messageHeight;
				g2d.drawString(winningMessage, xPos, yPos);

			}
			else {
				String winningMessage = "Black Wins!";
				g2d.setColor(Color.red);
				g2d.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
				int messageWidth = g2d.getFontMetrics().stringWidth(winningMessage);//Adjusted font to centre of chessboard using stackoverflow forum page:https://stackoverflow.com/questions/61823828/java-2d-vertically-centering-text.
				int messageHeight = g2d.getFontMetrics().getHeight();
				int xPos = (BOARD_WIDTH - messageWidth) / 2;//Simple calculations to get x & y positions.
				int yPos = (BOARD_HEIGHT - messageHeight) / 2 + messageHeight;
				g2d.drawString(winningMessage, xPos, yPos);
			}
			return;

		}

		if(isKingInCheck(false)) {//If king is in check, paint method will display checkMessage in the middle of the chessboard.
			g2d.setColor(Color.red);
			g2d.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
			String checkMessage = "King is in check!";
			int messageWidth = g2d.getFontMetrics().stringWidth(checkMessage);//Adjusted font to centre of chessboard using stackoverflow forum page:https://stackoverflow.com/questions/61823828/java-2d-vertically-centering-text.
			int messageHeight = g2d.getFontMetrics().getHeight();
			int xPos = (BOARD_WIDTH - messageWidth) / 2;//Simple calculations to get x & y positions.
			int yPos = (BOARD_HEIGHT - messageHeight) / 2 + messageHeight;
			g2d.drawString(checkMessage, xPos, yPos);
		}

		if (chosenPiece != null) {
			chosenPiece.draw(g);
		}
	}





	//Getter and Setter related methods

	/**
	 * Getter method returns current piece.
	 * @return chosen piece
	 */
	public Piece getChosenPiece() {
		return chosenPiece;
	}

	/**
	 * Setter method sets currently chosen piece on the chess board.
	 * @param piece The piece object to be set as currently chosen piece.
	 */
	public void setChosenPiece(Piece piece) {
		chosenPiece= piece;
	}




}



