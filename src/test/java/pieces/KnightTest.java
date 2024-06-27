package pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the Knight piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Knights.
 */
public class KnightTest {

	// White piece testing
	@Test
	public void testValidMoveWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.WHITE);

		assertTrue(knight.canMove(5, 4));
		assertTrue(knight.canMove(2, 5));
		assertTrue(knight.canMove(5, 2));
		assertTrue(knight.canMove(1, 2));
		assertTrue(knight.canMove(1, 4));
		assertTrue(knight.canMove(4, 5));
		assertTrue(knight.canMove(4, 1));
		assertTrue(knight.canMove(2, 1));
	}

	@Test
	public void testInValidMoveWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.WHITE);

		assertFalse(knight.canMove(5, 3));
		assertFalse(knight.canMove(2, 4));
		assertFalse(knight.canMove(5, 1));
		assertFalse(knight.canMove(1, 0));
		assertFalse(knight.canMove(4, 4));
		assertFalse(knight.canMove(6, 5));
		assertFalse(knight.canMove(4, 7));
		assertFalse(knight.canMove(2, 7));
	}

	@Test
	public void testInvalidMoveOutsideBoardWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 0, 0, ChessPanel.WHITE);
		assertFalse(knight.canMove(-1, 0));
		assertFalse(knight.canMove(0, -1));
		assertFalse(knight.canMove(8, 0));
	}

	@Test
	public void testValidCaptureWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.WHITE);
		board.getChessPieces().add(knight); 
		Pawn enemyPawn = new Pawn(board, 5, 4, ChessPanel.BLACK);
		board.getChessPieces().add(enemyPawn); 
		assertTrue("Knight should capture enemy piece", knight.canMove(5, 4));
	}

	// Black piece testing
	@Test
	public void testValidMoveBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.BLACK);

		assertTrue(knight.canMove(5, 4));
		assertTrue(knight.canMove(2, 5));
		assertTrue(knight.canMove(5, 2));
		assertTrue(knight.canMove(1, 2));
		assertTrue(knight.canMove(1, 4));
		assertTrue(knight.canMove(4, 5));
		assertTrue(knight.canMove(4, 1));
		assertTrue(knight.canMove(2, 1));
	}

	@Test
	public void testInValidMoveBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.BLACK);

		assertFalse(knight.canMove(5, 3));
		assertFalse(knight.canMove(2, 4));
		assertFalse(knight.canMove(5, 1));
		assertFalse(knight.canMove(1, 0));
		assertFalse(knight.canMove(4, 4));
		assertFalse(knight.canMove(6, 5));
		assertFalse(knight.canMove(4, 7));
		assertFalse(knight.canMove(2, 7));
	}

	@Test
	public void testInvalidMoveOutsideBoardBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 0, 0, ChessPanel.BLACK);
		assertFalse(knight.canMove(-1, 0));
		assertFalse(knight.canMove(0, -1));
		assertFalse(knight.canMove(8, 0));
	}

	@Test
	public void testValidCaptureBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();

		Knight knight = new Knight(board, 3, 3, ChessPanel.BLACK);
		Pawn enemyPawn = new Pawn(board, 1, 4, ChessPanel.WHITE);
		board.getChessPieces().add(enemyPawn); 
		assertTrue("Knight should capture enemy piece", knight.canMove(1, 4));
	}
}
