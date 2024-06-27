package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents the Rook chess piece. The Rook moves any number of squares along a row or column, but cannot jump over other pieces.
 * This class encapsulates the behavior and properties of a Rook piece, including its movement rules.
 */
public class Rook extends Piece  {

	/**
	 * Constructs a Rook piece with the specified location and color.
	 * Initializes the piece's position on the chessboard and loads its image based on the piece's color.
	 *
	 * @param column The starting column of the Rook on the chess board.
	 * @param row The starting row of the Rook on the chess board.
	 * @param color The color of the Rook piece.
	 */
	public Rook(ChessBoard board,int column, int row, int color) {
		super(board,column, row, color); //Initialize Piece class attributes.
		type = Type.ROOK; //Setting piece type to Rook.


		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-rook");
		}
		else {
			image = loadImage("/pieces/b-rook");
		}


	}
	/**
	 * Determines whether the Rook can legally move to the specified target location according to chess rules.
     * Movement rules for each piece checked using the link:https://www.chess.com/terms/chess-pieces
     * 
     * @param targetColumn The column index of the target position for the move.
     * @param targetRow The row index of the target position for the move.
     * @return true if move is valid otherwise return false.
	 */
	public boolean canMove(int targetColumn, int targetRow) {
		// Check if the target position is inside the board
		if (isInsideBoard(targetColumn, targetRow) && isPieceInSameSquare(targetColumn, targetRow)== false) {
			if (targetColumn == previousColumn || targetRow == previousRow) {
				if(canCapture(targetColumn, targetRow) && isPathClear(targetColumn, targetRow)== false) {
					return true;
				}

			}
		}
		return false;

	}
}




