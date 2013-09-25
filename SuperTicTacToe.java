/**
 * The SuperTicTacToe class starts the Tic-Tac-Toe game using a GUI.
 * 
 * @author Cameron Ohrt
 * @version 1.0
 */
package project2;

import javax.swing.*;

public class SuperTicTacToe {

	/**
	 * Creates the main frame of the game and a new game. The user is asked what
	 * size of board they would like (between 3 and 10) and who they would like
	 * to start first. Default values of board size 3 and x starting first are
	 * used if incorrect input are received. If the user doesn't enter anything,
	 * the program exits.
	 */
	public static void main(String[] args) {
		String boardSize;
		String turn;
		int size = 0;
		boardSize = JOptionPane.showInputDialog(null,
				"Enter in the size of the board (between 3 and 10): ");
		if (boardSize == null)
			System.exit(1);
		if (boardSize.equals("3") || boardSize.equals("4")
				|| boardSize.equals("5") || boardSize.equals("6")
				|| boardSize.equals("7") || boardSize.equals("8")
				|| boardSize.equals("9") || boardSize.equals("10")) {
			size = Integer.parseInt(boardSize);
		} else {
			size = 3;
		}

		turn = JOptionPane.showInputDialog(null,
				"Who starts the game first? X or O?");
		if (turn == null)
			System.exit(1);
		if (turn.equals("X") == false && turn.equals("x") == false
				&& turn.equals("O") == false && turn.equals("o") == false) {
			JOptionPane.showMessageDialog(null,
					"X has been chosen as the default starting player.");
		}

		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SuperTicTacToePanel panel = new SuperTicTacToePanel(turn, size, frame);
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);

	}

}