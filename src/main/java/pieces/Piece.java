package pieces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import aryaman_Project.ChessBoard;
import aryaman_Project.Type;

/**
 * Abstract class representing a generic chess piece. This class provides the common
 * functionality and properties that are shared across all types of chess pieces, including
 * position, color, image representation, and movement capabilities.
 * 
 * Chess piece images are sourced from: https://ryisnow.itch.io/pixel-art-chess-piece-images
 * The movement rules, including promotion, En Passant, and castling, are based on standard
 * chess rules described at: https://www.chess.com/learn-how-to-play-chess.
 */
public class Piece {
	public Type type; // Enum representing specific tyoe of chess piece.
	public int x,y; // Pixel coordinates for rendering the piece on the board.
	public int column, row, previousColumn, previousRow; // Current and previous board coordinates of the piece.
	public int color; // Color of the piece.
	public BufferedImage image; 
	public Piece capturedPiece;
	public boolean hasPieceMoved; // Boolean variable indicates whether piece has moved from it's initial position. 
	public boolean enPassantEligible = false;
	protected ChessBoard board;
	/**
	 * This constructor constructs a Piece with specified board location and it's color.
	 * @param column The column position of the piece on the chess board.
	 * @param row The row position of the piece on the chess board.
	 * @param color The color of the piece.
	 */
	public Piece(ChessBoard board, int column, int row, int color) {
		this.column = column;
		this.board = board;
		this.row = row;
		this.color = color;
		x = getX(column);
		y = getY(row);
		previousColumn = column;
		previousRow = row;

	}
	
	/**
	 * Creates a copy of the Piece object.
	 * This method is crucial for simulating moves on the chessboard without affecting the actual game state.
	 * For help I referred to a website I found at https://www.scaler.com/topics/clone-method-in-java/
	 *
	 * @return A new Piece object which is an exact copy of it.
	 */
	@Override
	public Piece clone(){
        Piece piece = new Piece(board, column, row, color);
		piece.x = x;
		piece.y = y;
		piece.previousColumn = previousColumn;
		piece.previousRow = previousRow;
		piece.hasPieceMoved = hasPieceMoved;
		piece.enPassantEligible = enPassantEligible;
		piece.board = board;
		piece.capturedPiece = capturedPiece;
		piece.image = image;
		piece.type = type;
		return piece;// Returns new cloned Piece object.
	}

	/**
	 * This method loads image for the chess pieces from the resources folder.
	 * This method was created with the help provided from documentation at: https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html

	 * @param imageLoader The path of the image file within resources folder.
	 * @return A BufferedImage object representing loaded image.
	 */
	public BufferedImage loadImage(String imageLoader) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageLoader + ".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;

	}
	


	/**
	 * Getter method converts row index to it's corresponding y-coordinate for image rendering.
	 * @param row The row index of the board.
	 * @return The y-coordinate in pixels.
	 */
	public int getY(int row) {
		return row * ChessBoard.SQUARE_SIZE;
	}

	/**
	 * Getter method converts column index to it's corresponding x-coordinate for image rendering.

	 * @param column The column index of the board.
	 * @return The x-coordinate in pixels.
	 */
	public int getX(int column) {
		return column * ChessBoard.SQUARE_SIZE;

	}

	/**
	 * Converts a pixel x-coordinate into a column index on the chess board.

	 * @param x The x-coordinate in pixels.
	 * @return The column index on chess board correspoding to x-coordinate.
	 */
	public int getColumn(int x) {
		return (x + ChessBoard.HALF_SQUARE)/ChessBoard.SQUARE_SIZE;

	}

	/**
	 * Converts a pixel y-coordinate into a row index on the chess board.

	 * @param y The y-coordinate in pixels.
	 * @return The row index on chess board corresponding to y-coordinate.
	 */
	public int getRow(int y) {
		return (y + ChessBoard.HALF_SQUARE)/ChessBoard.SQUARE_SIZE;

	}


	/**
	 * Updates piece position aftwr user has made a move.
	 */
	public void UpdatePiecePosition() {
		if(type == Type.PAWN) {
			if(Math.abs(row - previousRow) == 2) {
				enPassantEligible = true;
			}
		}
		x = getX(column);
		y = getY(row);
		previousColumn = getColumn(x);
		previousRow = getRow(y);
		hasPieceMoved = true;

	}

	/**
	 * This boolean method determines if the piece can move to specified location on the board.
	 * 
	 * @param targetColumn The column index of target position.
	 * @param targetRow The row index of target position.
	 * @return returns false but should return true in subclasses if move is valid.
	 */
	public boolean canMove(int targetColumn, int targetRow) {
		return false;
	}


	/**
	 * This method checks if specified board position is within valid range of the chess board.
	 * @param targetColumn The column index of position to check.
	 * @param targetRow The row index of position to check.
	 * @return true if position is within bounds of chess board otherwise false.
	 */
	public boolean isInsideBoard(int targetColumn, int targetRow) {
		if(targetColumn <= 7 && targetColumn >= 0 &&  targetRow >= 0 && targetRow <= 7) {
			return true;
		}
		return false;
	}
	


	/**
	 * Resets piece position to previous coordinates on the board.
	 * This method is mainly called when a move is invalid ot when simulating moves for checking game conditions.
	 */
	public void resetPosition() {
		column = previousColumn;
		row = previousRow;
		x = getX(column);
		y = getY(row);
	}

	/**
	 * Gets the piece at specified board position if it exists.
	 * This method is used to determine if there is a piece that can be captured by a move to it's target location.
	 * 
	 * @param targetColumn The column index of the target position.
	 * @param targetRow The row index of the target position.
	 * @return The piece at the specified position, or null if no piece is found or the piece
	 * is the same as the calling piece.
	 */
	public Piece getCapturedPiece(int targetColumn, int targetRow) {
		//for loop iterates through list of chess pieces and checks for the piece at the coordinate if found it retuns the piece else it returns null.
		for (Piece piece : board.getChessPieces()) {
			if (piece != this && piece.column == targetColumn && piece.row == targetRow) {
				return piece;
			}
		}

		return null;
	}




	/**
	 * This method determines if the piece can capture another piece at the specified board position.
	 * It utilizes getCapturedPiece to check for a piece it can capture.
	 * 
	 * @param targetColumn The column index of the potential capture position.
	 * @param targetRow The row index of the potential capture position.
	 * @return true if capture operation is possible else return false.
	 */
	public boolean canCapture(int targetColumn, int targetRow) {
		capturedPiece = getCapturedPiece(targetColumn, targetRow);
		if (capturedPiece == null || capturedPiece.color != this.color) {
			return true;
		} else {
			capturedPiece = null;
			return false;
		}
	}



	/**
	 * This method checks if target position is same as previous postion to determine if piece has moved.
	 * 
	 * @param targetColumn The column index of the target position.
	 * @param targetRow The row index of the target position.
	 * @return true if target position mathes pieces previous position otherwise returns false.
	 */
	public boolean isPieceInSameSquare(int targetColumn, int targetRow) {
		if(targetColumn == previousColumn && targetRow == previousRow) {
			return true;

		}
		return false;
	}

	/**
	 * Determines if path inbetween pieces current postion and target postion is clear.
	 * This method is important for pieces that move in straight lines such as rooks, bishops, and queens and must have clear paths to their destination squares.
	 *
	 * @param targetColumn The column index of the target position.
	 * @param targetRow The row index of the target position.
	 * @return true if no blocking puece on path otherwise return false.
	 */
	public boolean isPathClear(int targetColumn, int targetRow) {
		//Checks for up movement
		for(int r = previousRow - 1; r > targetRow; r--) {
			for(Piece piece : board.getChessPieces()){
				if(piece.column == targetColumn && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}
		//Checks for down movement
		for(int r = previousRow + 1; r < targetRow; r++) {
			for(Piece piece : board.getChessPieces()){
				if(piece.column == targetColumn && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}

		//Checks for left movement
		for(int col = previousColumn - 1; col > targetColumn; col--) {
			for(Piece piece : board.getChessPieces()){
				if(piece.column == col && piece.row == targetRow) {
					capturedPiece = piece;
					return true;
				}
			}
		}
		//Checks for right movement
		for(int col = previousColumn + 1; col < targetColumn; col++) {
			for(Piece piece : board.getChessPieces()){
				if(piece.column == col && piece.row == targetRow) {
					capturedPiece = piece;
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * Thus method mainly checks for diagonal clear paths for the Bishop piece, ensuring that the move
	 * from its current position to the target position is not obstructed by other pieces.
	 *
	 * @param targetColumn The column index of the target position.
	 * @param targetRow The row index of the target position.
	 * @return true if path is clear for diagonal move otherwise return false.
	 */
	public boolean bishopDiagonalCheck(int targetColumn, int targetRow) {
		// Checks for down right movement
		for(int r = previousRow + 1, col = previousColumn + 1; r < targetRow && col < targetColumn; r++, col++) {
			for(Piece piece : board.getChessPieces()) {
				if(piece.column == col && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}
		// Checks for down left movement
		for(int r = previousRow + 1, col = previousColumn - 1; r < targetRow && col > targetColumn; r++, col--) {
			for(Piece piece : board.getChessPieces()) {
				if(piece.column == col && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}
		// Checks for up left movement
		for(int r = previousRow - 1, col = previousColumn - 1; r > targetRow && col > targetColumn; r--, col--) {
			for(Piece piece : board.getChessPieces()) {
				if(piece.column == col && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}
		// Checks for up right movement
		for(int r = previousRow - 1, col = previousColumn + 1; r > targetRow && col < targetColumn; r--, col++) {
			for(Piece piece : board.getChessPieces()) {
				if(piece.column == col && piece.row == r) {
					capturedPiece = piece;
					return true;
				}
			}
		}



		return false;
	}



	/**
	 * Draws the chess piece on the game board.
	 * 
	 * @param g The Graphics context used for drawing operations.
	 */
	public void draw(Graphics g) {
		g.drawImage(image, x, y, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, null);
	}


}






