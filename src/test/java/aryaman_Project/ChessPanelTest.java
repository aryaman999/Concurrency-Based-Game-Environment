package aryaman_Project;

import static org.junit.Assert.*;
import org.junit.Test;
import pieces.King;
import pieces.Rook;

public class ChessPanelTest {

	@Test
	public void testIllegalKingMove() {
		// Setup the chess panel and clear any existing pieces.

		ChessPanel chessPanel = new ChessPanel();
		chessPanel.board.getChessPieces().clear();
		// Place a black king and a white rook on the board such that the rook checks the king.
		King kingBlack = new King(chessPanel.board, 4, 0, ChessPanel.BLACK);
		Rook rookWhite = new Rook(chessPanel.board, 4, 1, ChessPanel.WHITE);
		chessPanel.board.getChessPieces().add(kingBlack);
		chessPanel.board.getChessPieces().add(rookWhite);
		// Assert that the move is correctly identified as illegal king would be in check.
		boolean result = chessPanel.illegalKingMove(kingBlack);
		assertTrue("The move should be identified as illegal as it puts king in check", result);
	}

	@Test
	public void testGameDetectsCheck() {
		// Setup the chess panel and clear any existing pieces
		ChessPanel chessPanel = new ChessPanel();
		chessPanel.board.getChessPieces().clear();

		// Place a white king and a black rook on the board in positions that result in a check
		King kingWhite = new King(chessPanel.board, 4, 0, ChessPanel.WHITE);
		Rook rookBlack = new Rook(chessPanel.board, 4, 7, ChessPanel.BLACK);
		chessPanel.board.getChessPieces().add(kingWhite);
		chessPanel.board.getChessPieces().add(rookBlack);

		// Assert that the game correctly detects the king is in check
		assertTrue("Game should detect check on the white king", chessPanel.isKingInCheck(false));
	}
}

