package pieces;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the Queen piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Queens.
 */
public class QueenTest {

	private ChessBoard board;

	@Before
	public void setUp() {
		// Initialize a new board for each test to ensure isolation
		board = new ChessBoard();
	}

	@Test
	public void testValidMoveHorizontalWhite() {
		board.getChessPieces().clear();
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		assertTrue("Queen should move horizontally", queen.canMove(3, 6));
	}

	@Test
	public void testValidMoveVerticalWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		assertTrue("Queen should move vertically", queen.canMove(6, 3));
	}

	@Test
	public void testValidMoveDiagonalWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		assertTrue("Queen should move diagonally", queen.canMove(5, 5));
	}

	@Test
	public void testInvalidMoveOutsideBoardWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		assertFalse("Move should be invalid as it's outside the board", queen.canMove(-1, 3));
		assertFalse("Move should be invalid as it's outside the board", queen.canMove(3, 8));
	}

	@Test
	public void testValidCaptureWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		board.getChessPieces().add(new Piece(board, 3, 5, ChessPanel.BLACK)); // Adjusted to reflect correct setup
		assertTrue("Queen should capture enemy piece", queen.canMove(3, 5));
	}

	@Test
	public void testInvalidCaptureOwnPieceWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		board.getChessPieces().add(new Piece(board, 3, 5, ChessPanel.WHITE)); // Adjusted to reflect correct setup
		assertFalse("Queen should not capture own piece", queen.canMove(3, 5));
	}

	@Test
	public void testPathBlockedByOwnPieceWhite() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.WHITE);
		board.getChessPieces().add(new Piece(board, 3, 4, ChessPanel.WHITE)); // Adjusted to reflect correct setup
		assertFalse("Queen should not be able to move through ally piece", queen.canMove(3, 5));
	}

	@Test
	public void testValidMoveHorizontalBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		assertTrue("Queen should move horizontally", queen.canMove(3, 6));
	}

	@Test
	public void testValidMoveVerticalBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		assertTrue("Queen should move vertically", queen.canMove(6, 3));
	}

	@Test
	public void testValidMoveDiagonalBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		assertTrue("Queen should move diagonally", queen.canMove(5, 5));
	}

	@Test
	public void testInvalidMoveOutsideBoardBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		assertFalse("Move should be invalid as it's outside the board", queen.canMove(-1, 3));
		assertFalse("Move should be invalid as it's outside the board", queen.canMove(3, 8));
	}

	@Test
	public void testValidCaptureBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		board.getChessPieces().add(new Piece(board, 3, 5, ChessPanel.WHITE)); // Enemy piece for the black queen to capture
		assertTrue("Queen should capture enemy piece", queen.canMove(3, 5));
	}

	@Test
	public void testInvalidCaptureOwnPieceBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		board.getChessPieces().add(new Piece(board, 3, 5, ChessPanel.BLACK)); // Ally piece, capture should be invalid
		assertFalse("Queen should not capture own piece", queen.canMove(3, 5));
	}

	@Test
	public void testPathBlockedByOwnPieceBlack() {
		Queen queen = new Queen(board, 3, 3, ChessPanel.BLACK);
		board.getChessPieces().add(new Piece(board, 3, 4, ChessPanel.BLACK)); // Ally piece blocking the path
		assertFalse("Queen should not be able to move through ally piece", queen.canMove(3, 5));
	}

}
