package pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the Pawn piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Pawns.
 */
public class PawnTest {

	//White pawn testing
	@Test
	public void testPawnMovementOneSquareWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 4, ChessPanel.WHITE);
		assertTrue("Pawn should move forward one square", pawn.canMove(1, 3));
	}

	@Test
	public void testPawnInitialMovementWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 6, ChessPanel.WHITE); // Starting from row 6 for white
		assertTrue("Pawn should move forward two squares if it's the first move", pawn.canMove(1, 4));
	}

	@Test
	public void testPawnInvalidMoveWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 6, ChessPanel.WHITE);
		assertFalse("Pawn should not move backwards", pawn.canMove(1, 7));
	}

	@Test
	public void testPawnCapturesDiagonallyWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 6, ChessPanel.WHITE);
		Pawn enemyPawn = new Pawn(board, 2, 5, ChessPanel.BLACK);
		board.getChessPieces().add(pawn);
		board.getChessPieces().add(enemyPawn);
		assertTrue("Pawn should be able to capture diagonally", pawn.canMove(2, 5));
	}

	@Test
	public void testPawnMoveIfBlockedWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 6, ChessPanel.WHITE);
		Pawn blockingPawn = new Pawn(board, 1, 5, ChessPanel.WHITE);
		board.getChessPieces().add(pawn);
		board.getChessPieces().add(blockingPawn);  
		assertFalse("Pawn should not be able to move forward if blocked", pawn.canMove(1, 5));
	}

	@Test
	public void testPawnTwoSquareMovementWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 6, ChessPanel.WHITE); 
		pawn.hasPieceMoved = true; // Simulate that pawn has moved
		assertFalse("Pawn should not be able to move forward two squares if not the first move", pawn.canMove(1, 4));
	}

	// Black pawn testing
	@Test
	public void testPawnMovementOneSquareBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 3, ChessPanel.BLACK);
		assertTrue("Pawn should move forward one square", pawn.canMove(1, 4));
	}

	@Test
	public void testPawnInitialMovementBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 1, ChessPanel.BLACK); // Starting from row 1 for black
		assertTrue("Pawn should move forward two squares if it's the first move", pawn.canMove(1, 3));
	}

	@Test
	public void testPawnInvalidMoveBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 1, ChessPanel.BLACK);
		assertFalse("Pawn should not move backwards", pawn.canMove(1, 0));
	}

	@Test
	public void testPawnCapturesDiagonallyBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 2, ChessPanel.BLACK);
		Pawn enemyPawn = new Pawn(board, 2, 3, ChessPanel.WHITE);
		board.getChessPieces().add(pawn);
		board.getChessPieces().add(enemyPawn);
		assertTrue("Pawn should be able to capture diagonally", pawn.canMove(2, 3));
	}

	@Test
	public void testPawnMoveIfBlockedBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 2, ChessPanel.BLACK);
		Pawn blockingPawn = new Pawn(board, 1, 3, ChessPanel.BLACK);
		board.getChessPieces().add(pawn);
		board.getChessPieces().add(blockingPawn);
		assertFalse("Pawn should not be able to move forward if blocked", pawn.canMove(1,3));
	}

	@Test
	public void testPawnTwoSquareMovementBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(board, 1, 1, ChessPanel.BLACK);
		pawn.hasPieceMoved = true; // Simulate that pawn has moved
		assertFalse("Pawn should not be able to move forward two squares if not the first move", pawn.canMove(1, 3));
	}

}
