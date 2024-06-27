package pieces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the Rook piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Rooks.
 */
public class RookTest {
	private ChessBoard board;

	@Before
	public void setUp() {
		board = new ChessBoard();
	}

	// White Piece Testing:
	@Test
	public void testValidMoveHorizontalWhite() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 3, 3, ChessPanel.WHITE);
		assertTrue("Rook should move horizontally", rook.canMove(3, 6));
	}

	@Test
	public void testValidMoveVerticalWhite() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 3, 3, ChessPanel.WHITE);
		assertTrue("Rook should move vertically", rook.canMove(6, 3));
	}

	@Test
	public void testInValidMoveDiagonalWhite() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 3, 3, ChessPanel.WHITE);
		assertFalse("Rook should not move diagonally", rook.canMove(5, 5));
	}

	@Test
	public void testInvalidMoveOutsideBoardWhite() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 0, 0, ChessPanel.WHITE);
		assertFalse("Move should be invalid as it's outside the board", rook.canMove(-1, 0));
		assertFalse("Move should be invalid as it's outside the board", rook.canMove(0, -1));
		assertFalse("Move should be invalid as it's outside the board", rook.canMove(8, 0));
	}

	@Test
	public void testValidMoveEnemyPieceInPathWhite() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 3, 3, ChessPanel.WHITE);
		board.getChessPieces().add(new Piece(board, 3, 5, ChessPanel.BLACK)); // Enemy piece in the path
		assertTrue("Rook should be able to capture", rook.canMove(3, 5)); // Rook should be able to move to capture
	}

	// Black Piece Testing:
	@Test
	public void testValidMoveHorizontalBlack() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 0, 0, ChessPanel.BLACK);
		assertTrue("Rook should move horizontally", rook.canMove(0, 5));
	}

	@Test
	public void testValidMoveVerticalBlack() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 0, 0, ChessPanel.BLACK);
		assertTrue("Rook should move vertically", rook.canMove(5, 0));
	}

	@Test
	public void testInValidMoveDiagonalBlack() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 0, 0, ChessPanel.BLACK);
		assertFalse("Rook should not move diagonally", rook.canMove(4, 4));
	}

	@Test
	public void testInvalidMoveOutsideBoardBlack() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 7, 7, ChessPanel.BLACK);
		assertFalse("Move should be invalid as it's outside the board", rook.canMove(7, 8));
	}

	@Test
	public void testValidMoveEnemyPieceInPathBlack() {
		board.getChessPieces().clear();

		Rook rook = new Rook(board, 7, 7, ChessPanel.BLACK);
		board.getChessPieces().add(new Piece(board, 7, 5, ChessPanel.WHITE)); // Enemy piece in the path
		assertTrue("Rook should be able to capture", rook.canMove(7, 5)); // Rook should be able to move to capture
	}
}
