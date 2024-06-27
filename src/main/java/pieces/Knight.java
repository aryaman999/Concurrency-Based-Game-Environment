package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents the Knight chess piece. The Knight moves in an L-shape, it can move two squares
 * in one direction and then one square perpendicular to that direction, or one square in
 * one direction and two squares perpendicular to that direction.
 */
public class Knight extends Piece  {

	/**
	 * Constucts Knight piece within specificed location and color.
	 * @param column The starting column position of the Knight on the chess board.
	 * @param row The starting row position of the Knight on the chess board.
	 * @param color The color of the Knight piece.
	 */
	public Knight(ChessBoard board,int column, int row, int color) {
		super(board,column, row, color);
		type = Type.KNIGHT;


		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-knight");
		}
		else {
			image = loadImage("/pieces/b-knight");
		}


	}
	
	
	/**
	 * This method determines whether the Knight can move to the specified target location according
     * to chess rules. The Knight's move is valid if it is an L-shaped move and the target
     * square is either empty or contains an opponent's piece that can be captured.
     * 
	 * Knight piece rule learned from link: https://www.chess.com/terms/chess-pieces#king 
	 * Knight move code inspired from link:https://stackoverflow.com/questions/71594433/how-to-write-the-moves-the-knight-can-perform#:~:text=A%20knight%20has%208%20possible,vertically%20and%20one%20square%20horizontally.
	 */
	public boolean canMove( int targetColumn, int targetRow) {
		if(isInsideBoard(targetColumn, targetRow) && (Math.abs(targetColumn - previousColumn) * Math.abs(targetRow - previousRow) == 2)
				&& canCapture(targetColumn,targetRow)) {
					return true;
				}
		return false;
		}


}

