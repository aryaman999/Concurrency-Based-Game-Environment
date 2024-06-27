package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents the Queen piece in a chess game. The Queen combines the power of a Rook and a Bishop,
 * being able to move any number of squares along a rank, file, or diagonal, but cannot jump over other pieces.
 */
public class Queen extends Piece  {

	/**
	 * The constructor constructs a Queen piece with the specified location and color.
	 * @param column The starting column position of the Queen on the chess board
	 * @param row The starting row position of the Queen on the chess board
	 * @param color The color of Queen piece.
	 */
	public Queen(ChessBoard board,int column, int row, int color) {
		super( board, column, row, color);
		type = Type.QUEEN;

		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-queen");
		}
		else {
			image = loadImage("/pieces/b-queen");
		}


	}

	
	/**
	 * Determines whether the Queen can move to the specified target location according
     * to chess rules. 
     * 
	 * Queen piece rule learned from link: https://www.chess.com/terms/chess-pieces#king.
	 * Queen can move horizonal, vertical and diagonal any number of steps.
	 * 
	 * @param targetColumn The target column position for the Queen's move.
	 * @param targetRow The target row position for the Queen's move.
	 * @return true if move is valid otherwise returns false.
	 */
	public boolean canMove( int targetColumn, int targetRow) {
		
		// Queen has ability of several pieces I will reuse code used for rook piece and bishop piece to complete queen piece movement
		// Passing Queen rook ability
		if(isInsideBoard(targetColumn, targetRow) && isPieceInSameSquare(targetColumn, targetRow)== false){
			if (targetColumn == previousColumn || targetRow == previousRow) {
				if(canCapture(targetColumn, targetRow) && isPathClear(targetColumn, targetRow)== false) {
					return true;
				}
			}
			
			if(Math.abs(targetColumn - previousColumn) == Math.abs(targetRow - previousRow)) {
				if(canCapture(targetColumn, targetRow) && bishopDiagonalCheck(targetColumn, targetRow)== false) {
					return true;
				}
			}

		}
		return false;
	}
}


