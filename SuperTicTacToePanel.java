/**
 * The SuperTicTacToePanel class creates the components of the GUI
 * and determines what events occur based on the choice of the user.
 * 
 * @author Cameron Ohrt
 * @version 1.0
 */
package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;

public class SuperTicTacToePanel extends JPanel {

	private JFrame frame;
	private JButton[][] board;
	private Cell[][] cells;
	private JButton quitButton;
	private ImageIcon xIcon;
	private ImageIcon oIcon;
	private ImageIcon emptyIcon;
	private int xWins, oWins, numTies;
	private JLabel xWin, oWin, ties;
	private JButton undoButton, newButton;
	private JButton saveButton, loadButton;
	private int boardSize;
	private JPanel oneP, twoP, threeP;

	private SuperTicTacToeGame game;

	/**
	 * Instantiates a JPanel with various other JPanels, as well as instantiates
	 * a new Tic-Tac-Toe game. Proper image files are retrieved and used as
	 * icons.
	 * 
	 * @param firstTurn
	 *            the player who has the first turn of each game
	 * @param size
	 *            the size of the board, length and width
	 * @param frame
	 *            the frame that displays/holds all of the JPanels for the game
	 */
	public SuperTicTacToePanel(String firstTurn, int size, JFrame frame) {
		boardSize = size;
		setLayout(new BorderLayout());

		xIcon = new ImageIcon("x.jpg");
		oIcon = new ImageIcon("o.jpg");
		emptyIcon = new ImageIcon("empty.jpg");

		oneP = createBoardPanel();
		twoP = createScorePanel();
		threeP = createButtonPanel();

		add(oneP, BorderLayout.NORTH);
		add(twoP, BorderLayout.CENTER);
		add(threeP, BorderLayout.SOUTH);

		game = new SuperTicTacToeGame(firstTurn, size);
		cells = game.getBoard();
		this.frame = frame;

	}

	/**
	 * Creates a JPanel that represents a board of JButtons and sets the display
	 * icon of each button to empty.
	 * 
	 * @return a JPanel that contains the playing board of JButtons
	 */
	private JPanel createBoardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(boardSize, boardSize));

		ButtonListener listener = new ButtonListener();

		board = new JButton[boardSize][boardSize];

		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].addActionListener(listener);
				panel.add(board[row][col]);

			}
		}

		return panel;
	}

	/**
	 * Creates a JPanel that displays how many wins each player has, along with
	 * the number of times they have tied.
	 * 
	 * @return a JPanel that contains labels to represent the score
	 */
	private JPanel createScorePanel() {
		JPanel panel = new JPanel();
		xWin = new JLabel("X has won " + xWins + " times.");
		oWin = new JLabel("O has won " + oWins + " times.");
		ties = new JLabel("The game has been tied " + numTies + " times.");
		panel.add(oWin);
		panel.add(xWin);
		panel.add(ties);

		return panel;
	}

	/**
	 * Sets the icon of each button to the appropriate icon; If the cell at that
	 * location has an X, the icon is updated to show the letter X. Likewise for
	 * O and empty spots.
	 */
	private void displayBoard() {
		cells = game.getBoard();
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				if (cells[row][col] == Cell.O)
					board[row][col].setIcon(oIcon);
				else if (cells[row][col] == Cell.X)
					board[row][col].setIcon(xIcon);
				else
					board[row][col].setIcon(emptyIcon);
			}
		}
	}

	/**
	 * Creates the JPanel that contains all of the buttons available to the
	 * user, which includes a new game, undo, quit, load, and save button.
	 * 
	 * @return a JPanel that contains all of the buttons available to the user.
	 */
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();
		newButton = new JButton("New Game");
		saveButton = new JButton("Save Game");
		loadButton = new JButton("Load Game");
		quitButton = new JButton("Quit");
		undoButton = new JButton("Undo");
		newButton.addActionListener(listener);
		quitButton.addActionListener(listener);
		undoButton.addActionListener(listener);
		saveButton.addActionListener(listener);
		loadButton.addActionListener(listener);
		panel.add(newButton);
		panel.add(undoButton);
		panel.add(quitButton);
		panel.add(saveButton);
		panel.add(loadButton);

		return panel;

	}

	/**
	 * Replaces the JPanel that contains all of the JButtons (the representation
	 * of the board) with a new JPanel that contains new information and updates
	 * the frame that holds all of the components; The method is called when the
	 * load feature is selected.
	 */
	private void updateBoardPanel() {
		remove(oneP);
		oneP = createBoardPanel();
		add(oneP, BorderLayout.NORTH);
		frame.pack();
		frame.validate();

	}

	/**
	 * The ButtonListener class is called upon when a button is pressed.
	 * 
	 * @author Cameron Ohrt
	 * 
	 */
	private class ButtonListener implements ActionListener {

		/**
		 * This method checks to see which button was pressed. An appropriate
		 * response is issued depending on what button was pressed (i.e. the
		 * undo button pressed activates the undo method of the game)
		 * 
		 * @param event
		 *            the event that was activated/fired
		 */
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == undoButton) {
				game.undo();
			}

			if (event.getSource() == quitButton) {
				int exit = JOptionPane.showConfirmDialog(null,
						"Exit the program?");
				if (exit == JOptionPane.YES_OPTION)
					System.exit(1);
			}

			if (event.getSource() == saveButton) {
				game.save();
			}

			if (event.getSource() == loadButton) {
				game.load();
				boardSize = game.getBoardSize();
				updateBoardPanel();
				xWins = 0;
				oWins = 0;
				numTies = 0;
				xWin.setText("X has won " + xWins + " times.");
				oWin.setText("O has won " + oWins + " times.");
				ties.setText("The game has been tied " + numTies + " times.");

			}

			if (event.getSource() == newButton) {
				int i = JOptionPane.showConfirmDialog(null,
						"Start a new game and abandon this one?");
				if (i == JOptionPane.YES_OPTION) {
					game.newGame();
				}
			}

			for (int row = 0; row < boardSize; row++) {
				for (int col = 0; col < boardSize; col++) {
					if (board[row][col] == event.getSource())
						game.select(row, col);
				}
			}
			displayBoard();

			if (game.getGameStatus() == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null, "X won and O lost!"
						+ "\nThe game will reset");

				game.newGame();
				xWins++;
				xWin.setText("X has won " + xWins + " times.");
				displayBoard();
			}

			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won and X lost!"
						+ "\nThe game will reset");

				game.newGame();
				oWins++;
				oWin.setText("O has won " + oWins + " times.");
				displayBoard();
			}

			if (game.getGameStatus() == GameStatus.TIE) {
				JOptionPane.showMessageDialog(null, "It is a tie!"
						+ "\nThe game will reset");
				game.newGame();
				numTies++;
				ties.setText("The game has been tied " + numTies + " times.");
				displayBoard();
			}
		}
	}

}
