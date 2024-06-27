package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents movement rules for Bishop chess piece.It determines valid move for bishop piece.
 * Bishop can move diagnoally across the board and can moe any number of squares
 * along a diagnonal as long as it't not obstructed.
 */
public class Bishop extends Piece  {

	/**
	 * 
	 * Displays bishop piece with specified location and color.
	 * @param column The starting column position of bishop.
	 * @param row The starting row position of bishop.
	 * @param color The color of Bishop, WHITE or BLACK.
	 */
	public Bishop(ChessBoard board, int column, int row, int color) {
		super(board,column, row, color);// Superclass constructor to set position and color.
		type = Type.BISHOP; //Setting type for this piece.

		//Loads image for Bishop piece.
		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-bishop");
		}
		else {
			image = loadImage("/pieces/b-bishop");
		}


	}

	/**
	 * This boolean method determines if bishop piece can move to it's target location.
	 * The piece can move if it moves diagonally, the path is unobstructed,
	 * and follows the general chess movement rules.
	 * @param targetColumn The target column position for Bishop's move.
	 * @param targetRow The target row position for Bishop's move.
	 * @return true if move is valid otherwise return false.
	 */
	public boolean canMove(int targetColumn, int targetRow) {
		if(isInsideBoard(targetColumn, targetRow) && isPieceInSameSquare(targetColumn, targetRow)== false) {
			if(Math.abs(targetColumn - previousColumn) == Math.abs(targetRow - previousRow)) {
				if(canCapture(targetColumn, targetRow) && bishopDiagonalCheck(targetColumn, targetRow)== false) {
					return true;
				}

			}
		}
		return false;
	}



}
