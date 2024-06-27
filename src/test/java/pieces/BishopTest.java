package pieces;

import static org.junit.Assert.*;
import org.junit.Test;

import aryaman_Project.ChessBoard;
import aryaman_Project.ChessPanel;

/**
 * Tests for the Bishop piece movements and captures. It verifies valid and invalid moves,
 * including capturing enemy pieces and avoiding capturing ally pieces, for both white and black Bishops.
 */
public class BishopTest {



    // White Piece Testing:
	@Test
    public void testValidMoveWhite() {
        ChessBoard board = new ChessBoard(); // Create a new ChessBoard instance for the test
        board.getChessPieces().clear(); // Clear existing pieces for the test scenario
        Bishop bishop = new Bishop(board, 3, 3, ChessPanel.WHITE); // Create a Bishop with reference to the board
        assertTrue("Bishop should be able to move", bishop.canMove(5, 5));
    }

    @Test
    public void testInvalidMoveWhite() {
    	ChessBoard board = new ChessBoard(); // Create a new ChessBoard instance for the test
        board.getChessPieces().clear();
        Bishop bishop = new Bishop(board, 3, 3, ChessPanel.WHITE);
        assertFalse("Invalid move", bishop.canMove(4, 5));
    }

    @Test
    public void testBishopCaptureWhite() {
    	ChessBoard board = new ChessBoard(); // Create a new ChessBoard instance for the test
        board.getChessPieces().clear();
    	Bishop bishop = new Bishop(board, 3, 3, ChessPanel.WHITE);
    	board.getChessPieces().add(bishop); 
        Pawn pawn = new Pawn(board, 5, 5, ChessPanel.BLACK);
        board.getChessPieces().add(pawn); // Use a method to add a piece to the board
        assertTrue("Bishop should be able to capture", bishop.canMove(5, 5)); 
    }

    @Test
    public void testBlockedPathWhite() {
        ChessBoard board = new ChessBoard(); // Create a new ChessBoard instance for the test
        board.getChessPieces().clear();
        Bishop bishop = new Bishop(board, 4, 4, ChessPanel.WHITE);
        Pawn pawn = new Pawn(board, 5, 5, ChessPanel.WHITE);
        board.getChessPieces().add(pawn); // Corrected way to add a pawn to the board
        assertFalse("Bishop should not be able to move through or capture ally piece", bishop.canMove(6, 6)); 
    }


    @Test
    public void testMoveOutOfBoundsWhite() {
        ChessBoard board = new ChessBoard(); // Create a new ChessBoard instance for the test
        Bishop bishop = new Bishop(board, 4, 4, ChessPanel.WHITE);
        assertFalse("Move should be out of bounds", bishop.canMove(-1, -1));
    }
    
    // Black Piece Testing:
    @Test
    public void testValidMoveBlack() {
        ChessBoard board = new ChessBoard(); 
        board.getChessPieces().clear();
        Bishop bishop = new Bishop(board, 2, 0, ChessPanel.BLACK);
        assertTrue("Bishop should be able to move", bishop.canMove(0, 2)); 
    }

    
}
