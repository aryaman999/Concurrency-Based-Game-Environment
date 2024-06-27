package aryaman_Project;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import pieces.Piece;

/**
 * This class controls mouse interaction within the ChessPanel,
 * handles piece selection, movement and actions based on user input.
 * This class listents to mouse events such as dragging,pressing and releasing to ensure seamless and interactive gameplay.
 * Class created and inspired from a forum page:https://www.gamedev.net/forums/topic/635935-chess-movement-using-mouse-listeners-java/.
 * Also used oracle documentation for help at https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseAdapter.html.
 */
public class MouseController extends MouseAdapter {
	public int x,y; // mouse position variables.
	public boolean pressed; // boolean variable which indicates if mouse is currently pressed or not.
	private ChessPanel chessPanel;


	/**
	 * Constructs a MouseController within the given ChessPanel.
	 * @param chessPanel The ChessPanel the controller will engage with.
	 */
	public MouseController(ChessPanel chessPanel) {
		this.chessPanel = chessPanel;
	}


	/**
	 * Updates mouse coordintates when mouse is dragged.
	 * It is used to track piece movement within the game during drag operation.
	 * @param e The MouseEvent which contains new mouse position coordinates.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}


	/**
	 * Sets variable to true when mouse button is pressed.This method
	 * is used to initiate piece selection/movement.
	 * @param e The MouseEvent triggered by pressing the mouse button.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	/**
	 * This method resets the pressed variable bac k to false when mouse is released.
	 * This method concludes piece movement or selection process.
	 * @param e The MouseEvent triggered by releasing the mouse button.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		Piece chosenPiece = chessPanel.getChosenPiece();
		if (chosenPiece != null) {
			chosenPiece.x = (x / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE;
			chosenPiece.y = (y / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE;
			chosenPiece = null; // Reset chosenPiece
		}
	}



	/**
	 * This method updates mouse coordinates when mouse is moved.
	 * @param e The MouseEvent that contains new mouse coordinates.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}
}
