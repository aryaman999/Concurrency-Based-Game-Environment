package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents a Pawn chess piece. Pawns have the most complex rules of movement:
 * they move forward one square, but capture diagonally. In the first move, Pawn
 * can move two squares forward. Pawns can be promoted to another piece if they reach
 * the opponent's back rank.
 */
public class Pawn extends Piece  {

	/**
	 * Constructs Pawn piece within specified loaction within chess board.
	 * 
	 * @param column The starting column position of the Pawn on the chess board.
	 * @param row The starting row position of the Pawn on the chess board.
	 * @param color The color of Pawn piece.
	 */
	public Pawn(ChessBoard board,int column, int row, int color) {
		super(board,column, row, color);
		type = Type.PAWN;

		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-pawn");
		}
		else {
			image = loadImage("/pieces/b-pawn");
		}


	}


	//Requirements for Pawn Promotion:
	//A pawn can be promoted when it reaches the opposite side of the board.
	//The player can choose between a queen, rook, bishop, or knight for the promotion.
	//The promoted piece replaces the pawn on the same square.

	/**
	 * This method determines whether the Pawn can move to the specified target location.
	 * according to chess rules. This includes normal forward movement, capture 
	 * movements,initial two-square moves and also checks for en passant rule.
	 * 
	 * Pawn piece rule learned from link: https://www.chess.com/terms/chess-pieces#king.
	 * Information of pawn promotion gathered from: https://www.chess.com/terms/pawn-promotion.
	 * Information of En Passant gathered from: https://www.chess.com/terms/en-passant
	 *
	 * @param targetColumn The target column position for the Pawn's move.
	 * @param targetRow The target row position for the Pawn's move.
	 * @return true if move is valid else return false all asdhering to chess rules.
	 *
	 */
	public boolean canMove(int targetColumn, int targetRow) {

		if(isInsideBoard(targetColumn,targetRow) && isPieceInSameSquare(targetColumn, targetRow) == false) {
			int temp; // temp variable used to decide the direction pawn takes based on color.
			if(color == ChessPanel.WHITE) {
				temp = -1;
			}
			else {
				temp = 1;
			}

			capturedPiece = getCapturedPiece(targetColumn,targetRow);
			// Checks for forward move.
			if(targetColumn == previousColumn && targetRow == previousRow + temp && capturedPiece == null) {
				return true;
			}
			// Checks for initial two-square forward move.
			if(targetColumn == previousColumn && targetRow == previousRow + temp*2 && capturedPiece == null && hasPieceMoved == false &&
					isPathClear(targetColumn, targetRow) == false) {
				return true;
			}
			
			// Checks for diagonal capture move.
			if(Math.abs(targetColumn - previousColumn) == 1 && targetRow == previousRow + temp && capturedPiece != null &&
					capturedPiece.color != color && canCapture(targetColumn, targetRow)){
				return true;
			}

			// Checks for En passant
			if(Math.abs(targetColumn - previousColumn) == 1 && targetRow == previousRow + temp){
				for(Piece piece : board.getChessPieces()) {
					if(piece.column == targetColumn && piece.row == previousRow && piece.enPassantEligible == true) {
						capturedPiece = piece;
						return true;

					}
				}

			}



		}

		return false;
	}




}


