package aryaman_Project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeTest {
    private TicTacToe ticTacToe;

    @Before
    public void setUp() {
        ticTacToe = new TicTacToe();
    }

    @Test
    public void testChoosePlayer() {
        // Test to see that the first move is random
        String player = ticTacToe.getStartGame();
        assertEquals("X".equals(player) || "O".equals(player), true);
    }

    @Test
    public void testHorizontalX() {
        // Test to see if player X wins in the first row and pop up message shows.
        ticTacToe.getMoves()[0] = 1;
        ticTacToe.getMoves()[1] = 1;
        ticTacToe.getMoves()[2] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testHorizontalX2() {
        // Test to see if player X wins in the second row and pop up message shows.
        ticTacToe.getMoves()[3] = 1;
        ticTacToe.getMoves()[4] = 1;
        ticTacToe.getMoves()[5] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testHorizontalX3() {
        // Test to see if player X wins in the third row and pop up message shows.
        ticTacToe.getMoves()[6] = 1;
        ticTacToe.getMoves()[7] = 1;
        ticTacToe.getMoves()[8] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testVerticalX() {
        // Test to see if player X wins in the first column and pop up message shows.
        ticTacToe.getMoves()[0] = 1;
        ticTacToe.getMoves()[3] = 1;
        ticTacToe.getMoves()[6] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testVerticalX2() {
        // Test to see if player X wins in the first column and pop up message shows.
        ticTacToe.getMoves()[1] = 1;
        ticTacToe.getMoves()[4] = 1;
        ticTacToe.getMoves()[7] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testVerticalX3() {
        // Test to see if player X wins in the first column and pop up message shows.
        ticTacToe.getMoves()[2] = 1;
        ticTacToe.getMoves()[5] = 1;
        ticTacToe.getMoves()[8] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testDiagonalX() {
        // Test to see if player X wins diagonally if they play move1-->move5-->move9
        ticTacToe.getMoves()[0] = 1;
        ticTacToe.getMoves()[4] = 1;
        ticTacToe.getMoves()[8] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    @Test
    public void testDiagonalX2() {
        // Test to see if player X wins diagonally if they play move3-->move5-->move7
        ticTacToe.getMoves()[2] = 1;
        ticTacToe.getMoves()[4] = 1;
        ticTacToe.getMoves()[6] = 1;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getxScore().getText());
    }

    // Tests for player O
    // Remaining tests for player O are similar to those for player X
    // with appropriate modifications in the setMoves array.
    
  
    @Test
    public void testHorizontalO() {
        ticTacToe.getMoves()[0] = 0;
        ticTacToe.getMoves()[1] = 0;
        ticTacToe.getMoves()[2] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testHorizontalO2() {
        ticTacToe.getMoves()[3] = 0;
        ticTacToe.getMoves()[4] = 0;
        ticTacToe.getMoves()[5] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testHorizontalO3() {
        ticTacToe.getMoves()[6] = 0;
        ticTacToe.getMoves()[7] = 0;
        ticTacToe.getMoves()[8] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testVerticalO() {
        ticTacToe.getMoves()[0] = 0;
        ticTacToe.getMoves()[3] = 0;
        ticTacToe.getMoves()[6] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testVerticalO2() {
        ticTacToe.getMoves()[1] = 0;
        ticTacToe.getMoves()[4] = 0;
        ticTacToe.getMoves()[7] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testVerticalO3() {
        ticTacToe.getMoves()[2] = 0;
        ticTacToe.getMoves()[5] = 0;
        ticTacToe.getMoves()[8] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testDiagonalO() {
        ticTacToe.getMoves()[0] = 0;
        ticTacToe.getMoves()[4] = 0;
        ticTacToe.getMoves()[8] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }

    @Test
    public void testDiagonalO2() {
        ticTacToe.getMoves()[2] = 0;
        ticTacToe.getMoves()[4] = 0;
        ticTacToe.getMoves()[6] = 0;
        ticTacToe.winningPlayer();
        assertEquals("1", ticTacToe.getoScore().getText());
    }


    @Test
    public void testGameDraw() {
        TicTacToe game = new TicTacToe();

        // Results in a draw
        game.getMoves()[0] = 1;
        game.getMoves()[1] = 0;
        game.getMoves()[2] = 1;
        game.getMoves()[3] = 0;
        game.getMoves()[4] = 1;
        game.getMoves()[5] = 0;
        game.getMoves()[6] = 0;
        game.getMoves()[7] = 1;
        game.getMoves()[8] = 0;

        game.winningPlayer();

        // Score should remain O if the game has resulted in a draw.
        assertEquals(0, Integer.parseInt(game.getxScore().getText()));
        assertEquals(0, Integer.parseInt(game.getoScore().getText()));
    }

    @Test
    public void testEnableButtons() {
        // Creating buttons 1-9 for testing purposes
        JButton btn1 = new JButton();
        JButton btn2 = new JButton();
        JButton btn3 = new JButton();
        JButton btn4 = new JButton();
        JButton btn5 = new JButton();
        JButton btn6 = new JButton();
        JButton btn7 = new JButton();
        JButton btn8 = new JButton();
        JButton btn9 = new JButton();
        List<JButton> buttons = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9);
        ticTacToe.disableButtons(buttons); // Disables the buttons first
        ticTacToe.enableButtons(buttons); // Enables the buttons

        // Checks if buttons are enabled
        assertTrue(btn1.isEnabled());
        assertTrue(btn2.isEnabled());
        assertTrue(btn3.isEnabled());
        assertTrue(btn4.isEnabled());
        assertTrue(btn5.isEnabled());
        assertTrue(btn6.isEnabled());
        assertTrue(btn7.isEnabled());
        assertTrue(btn8.isEnabled());
        assertTrue(btn9.isEnabled());
    }

    @Test
    public void testDisableButtons() {
        JButton btn1 = new JButton();
        JButton btn2 = new JButton();
        JButton btn3 = new JButton();
        JButton btn4 = new JButton();
        JButton btn5 = new JButton();
        JButton btn6 = new JButton();
        JButton btn7 = new JButton();
        JButton btn8 = new JButton();
        JButton btn9 = new JButton();
        List<JButton> buttons = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9);
        ticTacToe.disableButtons(buttons);

        // Checks if buttons are disabled
        assertFalse(btn1.isEnabled());
        assertFalse(btn2.isEnabled());
        assertFalse(btn3.isEnabled());
        assertFalse(btn4.isEnabled());
        assertFalse(btn5.isEnabled());
        assertFalse(btn6.isEnabled());
        assertFalse(btn7.isEnabled());
        assertFalse(btn8.isEnabled());
        assertFalse(btn9.isEnabled());
    }
    
    


       
}

    
   
    
    
   
