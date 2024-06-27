package pieces;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;
import aryaman_Project.Type;

/**
 * This class represents King piece in the chess game.The piece can move one square
 * in any direction(vertical,horizontal or diagonal).
 */
public class King extends Piece  {

	/**
	 * This constructs King piece within the chess board.
	 * @param column starting column position of the King on the chess board.
	 * @param row The starting row position of the King on the chess board.
	 * @param color The color of the King piece.
	 */
	public King(ChessBoard board, int column, int row, int color) {
		super(board,column, row, color);
		type = Type.KING; //Assign piece type.


		if (color == ChessPanel.WHITE) {
			image = loadImage("/pieces/w-king");
		}
		else {
			image = loadImage("/pieces/b-king");
		}


	}



	/**
	 * This method determines if the King piece can move to the specified target location based on
	 * chess rules. The King moves one square in any direction. The method checks
	 * if the proposed move is within one square of the King's current position
	 * and ensures the target square is either empty or contains an opponent's piece.This method
	 * also checks for castling between King peice and Rook piece.
	 * 
	 * This method uses Math.abs to get a absolute value.Inspired from website:https://www.geeksforgeeks.org/java-math-abs-method-examples/.
	 * King piece rule learned from link: https://www.chess.com/terms/chess-pieces#king. 
	 * Castling rule learned from link: https://www.chess.com/terms/castling-chess

	 */
	public boolean canMove( int targetColumn, int targetRow) {
		if(isInsideBoard(targetColumn, targetRow)) {
			if(Math.abs(targetColumn - previousColumn) + Math.abs(targetRow - previousRow) == 1 || Math.abs(targetColumn - previousColumn) * Math.abs(targetRow - previousRow) == 1) {
				if(canCapture(targetColumn,targetRow)) {
					return true;
				}
			}

			//Castling logic:
			if(hasPieceMoved == false) { // Confirm the King has not moved yet.
				// Castling to right.Check if the target position for the king is two squares to the left or right of it's current position.
				if(targetColumn == previousColumn + 2 && targetRow == previousRow && isPathClear(targetColumn, targetRow)== false) {
					for(Piece piece : board.chessPieces) {
						if(piece.column == previousColumn+3 && piece.row == previousRow && piece.hasPieceMoved == false) { // Rook must also have not moved yet
							ChessPanel.castlingPiece = piece;
							return true;
						}

					}
				}
				// Castling to left.
				if(targetColumn == previousColumn - 2 && targetRow == previousRow && isPathClear(targetColumn, targetRow)== false) {
					Piece pieceRook1 = null;//Added two potenial rooks pieces involved in castling process.
					Piece pieceRook2 = null;
					for (Piece piece : board.chessPieces) {
						if (piece.row == targetRow) {
							if (piece.column == previousColumn - 3) {
								pieceRook1 = piece;
							} else if (piece.column == previousColumn - 4) {
								pieceRook2 = piece;
							}
						}
						if (pieceRook1 == null && pieceRook2 != null && !pieceRook2.hasPieceMoved) {
							ChessPanel.castlingPiece = pieceRook2;
							return true;
						}
					}

					if (pieceRook1 == null && pieceRook2 != null && !pieceRook2.hasPieceMoved) {
						ChessPanel.castlingPiece = pieceRook2;
						return true;
					}

				}

				return false; //If none of the above conditions are met, castling isnt possible.
			}
		}

		return false;
	}



}





