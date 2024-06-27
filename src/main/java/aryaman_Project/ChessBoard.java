package aryaman_Project;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * This class represents the chess board inside the chess game.
 * It initializes the pieces in their initial positions according to the chess game.
 * It also handles rendering of the board and its pieces in the GUI.
 */
public class ChessBoard {

	// Parameters for chess board.
	// Size of each square in the chessboard.
	public static final int SQUARE_SIZE = 100;
	public static final int HALF_SQUARE = SQUARE_SIZE/2;

	public List<Piece> chessPieces = new ArrayList<>();//List of pieces active on board.



	/**
	 * Constructor for ChessBoard class.Initializes board by placing the pieces in their initial positions.
	 */
	public ChessBoard() {
		initializeWhitePieces();
		initializeBlackPieces();
	}

	/**
	 * Initializes white pieces on the board.
	 * Positon of pieces placed using the images provided in https://www.chess.com/terms/chess-pieces.
	 * Chess board is 8x8 and files and ranks are all numbered from 0 to 7.
	 */
	private void initializeWhitePieces() {
		// White pieces.
		chessPieces.add(new Rook(this, 0, 7, ChessPanel.WHITE));
		chessPieces.add(new Rook(this,7, 7, ChessPanel.WHITE));
		chessPieces.add(new Bishop(this,2, 7, ChessPanel.WHITE));
		chessPieces.add(new Bishop(this,5, 7, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,0, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,1, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,2, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,3, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,4, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,5, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,6, 6, ChessPanel.WHITE));
		chessPieces.add(new Pawn(this,7, 6, ChessPanel.WHITE));
		chessPieces.add(new King(this,4, 7, ChessPanel.WHITE));
		chessPieces.add(new Queen(this,3, 7, ChessPanel.WHITE));
		chessPieces.add(new Knight(this,1, 7, ChessPanel.WHITE));
		chessPieces.add(new Knight(this,6, 7, ChessPanel.WHITE));
	}

	/**
	 * Initializes black pieces on the board.
	 * Positon of pieces placed using the images provided in https://www.chess.com/terms/chess-pieces.
	 */
	private void initializeBlackPieces() {

		// Black pieces.
		chessPieces.add(new Rook(this,0, 0, ChessPanel.BLACK));
		chessPieces.add(new Rook(this,7, 0, ChessPanel.BLACK));
		chessPieces.add(new Bishop(this,2, 0, ChessPanel.BLACK));
		chessPieces.add(new Bishop(this,5, 0, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,0, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,1, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,2, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,3, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,4, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,5, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,6, 1, ChessPanel.BLACK));
		chessPieces.add(new Pawn(this,7, 1, ChessPanel.BLACK));
		chessPieces.add(new King(this,4, 0, ChessPanel.BLACK));
		chessPieces.add(new Queen(this,3, 0, ChessPanel.BLACK));
		chessPieces.add(new Knight(this,1, 0, ChessPanel.BLACK));
		chessPieces.add(new Knight(this,6, 0, ChessPanel.BLACK));
	}




	/**
	 * Paints chessboard and it's pieces onto the GUI.
	 * @param g The Graphics object used for drawing.
	 */
	public void paint(Graphics g) {
		drawChessBoard(g);
		drawPieces(g);
	}

	/**
	 * This method draws a 8x8 chess board.
	 * This class was also modified using documentation provided by oracle at:https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html.
	 * @param g The Graphics object used for drawing.
	 */
	private void drawChessBoard(Graphics g) {
		//Paint method uses nested for loop to iterate through each square on chess board.
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				// Chess board inspired from the video link: https://www.youtube.com/watch?v=vO7wHV0HB8w&ab_channel=ScreenWorks.
				if ((x + y) % 2 == 0) {
					g.setColor(new Color(235, 236, 208));
				} else {
					g.setColor(new Color(119, 154, 88));
				}				g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

			}

		}


	}

	/**
	 * Draws all the chess pieces onto the board.
	 * @param g The Graphics object used for drawing.
	 */
	private void drawPieces(Graphics g) {
		// Chess board inspired from the video link: https://www.youtube.com/watch?v=vO7wHV0HB8w&ab_channel=ScreenWorks.
		for (Piece piece : chessPieces) {
			piece.draw(g);
		}
	}





	/**
	 * Returns list of chess pieces currently active on the board.
	 * @return A list of Piece objects.
	 */
	public List<Piece> getChessPieces() {
		return chessPieces;
	}

	/**
	 * Resets chessboard to orignal state.
	 */
	public void resetChessBoard() {
		//Clear the existing pieces
		chessPieces.clear();
		//Reinitialize white pieces
		initializeWhitePieces();
		//Reinitialize black pieces
		initializeBlackPieces();		
	}















}



