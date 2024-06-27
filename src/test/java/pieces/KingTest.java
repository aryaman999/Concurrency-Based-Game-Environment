package pieces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the King piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Kings.
 */
public class KingTest {

	// White Piece Testing
	@Test
	public void testValidMoveWhite() {   // Tests for white King's valid movements on the board.

		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 2, 3, ChessPanel.WHITE);
		assertTrue(king.canMove(2, 4));// Verify the king can move to all adjacent squares correctly.
		assertTrue(king.canMove(3, 3)); 
		assertTrue(king.canMove(2, 2));
		assertTrue(king.canMove(1, 3)); 
		assertTrue(king.canMove(3, 4)); 
		assertTrue(king.canMove(1, 2)); 
		assertTrue(king.canMove(3, 2)); 
		assertTrue(king.canMove(1, 4)); 
	}

	@Test
	public void testInvalidMoveWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 5, 5, ChessPanel.WHITE);
		assertFalse(king.canMove(7, 7)); 
		assertFalse(king.canMove(5, 7)); 
		assertFalse(king.canMove(7, 5));
	}

	@Test
	public void testInvalidMoveOutsideBoardWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 0, 0, ChessPanel.WHITE);
		assertFalse(king.canMove(-1, 0));
		assertFalse(king.canMove(0, -1)); 
		assertFalse(king.canMove(8, 0)); 
	}

	@Test
	public void testKingCapturesPieceWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 7, ChessPanel.WHITE);
		board.getChessPieces().add(king);
		Pawn enemyPawn = new Pawn(board, 4, 6, ChessPanel.BLACK);
		board.getChessPieces().add(enemyPawn);
		assertTrue("King should be able to capture the pawn", king.canMove(4, 6));
	}

	@Test
	public void testCaptureAllyPieceWhite() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 7, ChessPanel.WHITE);
		Rook ally = new Rook(board, 4, 6, ChessPanel.WHITE);
		board.getChessPieces().add(ally);
		assertFalse("King should not capture ally piece", king.canMove(4, 6));
	}

	// Black Piece Testing
	@Test
	public void testValidMoveBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 0, ChessPanel.BLACK);
		assertTrue(king.canMove(4, 1)); 
		assertTrue(king.canMove(3, 0)); 
		assertTrue(king.canMove(5, 1)); 
		assertTrue(king.canMove(3, 1));
		assertTrue(king.canMove(5, 0)); 
	}

	@Test
	public void testInvalidMoveBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 0, ChessPanel.BLACK);
		assertFalse(king.canMove(4, 2)); 
		assertFalse(king.canMove(4, 3)); 
	}

	@Test
	public void testInvalidMoveOutsideBoardBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 0, 0, ChessPanel.BLACK);
		assertFalse(king.canMove(-1, 0));
		assertFalse(king.canMove(0, -1)); 
	}

	@Test
	public void testKingCapturesPieceBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 0, ChessPanel.BLACK);
		board.getChessPieces().add(king);
		Pawn enemyPawn = new Pawn(board, 4, 1, ChessPanel.WHITE);
		board.getChessPieces().add(enemyPawn);
		assertTrue("King should be able to capture the pawn", king.canMove(4, 1));
	}

	@Test
	public void testCaptureAllyPieceBlack() {
		ChessBoard board = new ChessBoard();
		board.getChessPieces().clear();
		King king = new King(board, 4, 0, ChessPanel.BLACK);
		Rook ally = new Rook(board, 4, 1, ChessPanel.BLACK);
		board.getChessPieces().add(king);
		board.getChessPieces().add(ally); // Add the ally piece to the board
		assertFalse("King should not capture ally piece", king.canMove(4, 1));
	}



}