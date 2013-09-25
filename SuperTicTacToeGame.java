/**
 * The SuperTicTacToeGame class represents the game of Tic-Tac-Toe being played.
 * 
 * @author Cameron Ohrt
 * @version 1.0
 */
package project2;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SuperTicTacToeGame {

	private Cell[][] board;
	private GameStatus status;
	private boolean playerOne;
	private String startingTurn;
	private ArrayList<Point> record;
	private int turnNumber;
	private int boardSize;

	/**
	 * Instantiates a Tic-Tac-Toe game with the specified values.
	 * 
	 * @param firstTurn
	 *            the player who has the first turn every game
	 * @param size
	 *            the length and width of the board to be used
	 */
	public SuperTicTacToeGame(String firstTurn, int size) {
		if (firstTurn == null) {
			playerOne = true;
			startingTurn = "X";
		} else if (firstTurn.equals("X") || firstTurn.equals("x")) {
			playerOne = true;
			startingTurn = firstTurn;
		} else if (firstTurn.equals("O") || firstTurn.equals("o")) {
			playerOne = false;
			startingTurn = firstTurn;
		} else {
			playerOne = true;
			startingTurn = "X";
		}

		boardSize = size;

		newGame();

	}

	/**
	 * This method takes two parameters, based on the button the user clicks on
	 * the board, and sets the cell to the appropriate value (if it was X who
	 * selected the spot, the spot gets a value of X)
	 * 
	 * @param row
	 *            the row number of the spot on the board (using index values)
	 * @param col
	 *            the column number of the spot on the board (using index
	 *            values)
	 */
	public void select(int row, int col) {
		if (board[row][col] != Cell.EMPTY)
			return;
		if (playerOne) {
			board[row][col] = Cell.X;
			recordTurn(row, col);
			playerOne = false;
			turnNumber++;
		} else {
			board[row][col] = Cell.O;
			recordTurn(row, col);
			playerOne = true;
			turnNumber++;
		}
	}

	/**
	 * This method is called whenever a change to the board is made (or a
	 * selection by a player has been made). A player wins when a row, column,
	 * or diagonal group of 3 of their markers is found, no matter what size the
	 * board is. If no group of three markers is found, the status is set to
	 * tied (if all slots are filled) or in progress (if not all slots are
	 * filled).
	 * 
	 * @return returns the status of the game
	 */
	public GameStatus getGameStatus() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length - 2; j++) {
				if (board[i][j] == Cell.X && board[i][j + 1] == Cell.X
						&& board[i][j + 2] == Cell.X)
					status = GameStatus.X_WON;
				if (board[i][j] == Cell.O && board[i][j + 1] == Cell.O
						&& board[i][j + 2] == Cell.O)
					status = GameStatus.O_WON;
			}
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length - 2; j++) {
				if (board[j][i] == Cell.X && board[j + 1][i] == Cell.X
						&& board[j + 2][i] == Cell.X)
					status = GameStatus.X_WON;
				if (board[j][i] == Cell.O && board[j + 1][i] == Cell.O
						&& board[j + 2][i] == Cell.O)
					status = GameStatus.O_WON;
			}
		}

		for (int i = 0; i < board.length - 2; i++) {
			for (int j = 0; j < board.length - 2; j++) {
				if (board[i][j] == Cell.X && board[i + 1][j + 1] == Cell.X
						&& board[i + 2][j + 2] == Cell.X)
					status = GameStatus.X_WON;
				if (board[i][j] == Cell.O && board[i + 1][j + 1] == Cell.O
						&& board[i + 2][j + 2] == Cell.O)
					status = GameStatus.O_WON;
			}
		}

		for (int i = 0; i < board.length - 2; i++) {
			for (int j = board.length - 1; j > 1; j--) {
				if (board[i][j] == Cell.X && board[i + 1][j - 1] == Cell.X
						&& board[i + 2][j - 2] == Cell.X)
					status = GameStatus.X_WON;
				if (board[i][j] == Cell.O && board[i + 1][j - 1] == Cell.O
						&& board[i + 2][j - 2] == Cell.O)
					status = GameStatus.O_WON;
			}
		}

		if (status != GameStatus.O_WON && status != GameStatus.X_WON) {
			int x = 0;
			for (int row = 0; row < boardSize; row++) {
				for (int col = 0; col < boardSize; col++) {
					if (board[row][col] != Cell.EMPTY)
						x++;

				}
			}

			if (x == (boardSize * boardSize))
				setStatus(GameStatus.TIE);
			else
				setStatus(GameStatus.IN_PROGRESS);
		}
		return status;
	}

	/**
	 * This method returns the current status of the game, which can be one of
	 * four values.
	 * 
	 * @return the current status of the game
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * This method sets the status of the game to the passed GameStatus.
	 * 
	 * @param status
	 *            the GameStatus that the current game should be set to.
	 */
	public void setStatus(GameStatus status) {
		this.status = status;
	}

	/**
	 * This method returns a multidimensional array of Cells, which represents
	 * each spot on the game board.
	 * 
	 * @return a multidimensional array of Cells that represents each spots,
	 *         either Empty or taken by an X or O
	 */
	public Cell[][] getBoard() {
		return board;
	}

	/**
	 * This method resets the game so that the same player goes first as did
	 * previously and so that each spot on the board is empty again.
	 */
	public void newGame() {
		status = GameStatus.IN_PROGRESS;
		board = new Cell[boardSize][boardSize];
		record = new ArrayList<Point>();
		turnNumber = 0;

		clearBoard();

		if (startingTurn.equals("X") || startingTurn.equals("x"))
			playerOne = true;
		else
			playerOne = false;

	}

	/**
	 * This method returns the size of the board to be played on.
	 * 
	 * @return the size of the board, or the value of the length and the value
	 *         of width of the board
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * This method is called whenever a player selects a spot on the board. It
	 * takes the value of the row and column of the spot and adds a point P with
	 * (x,y) as (row, column) to an ArrayList of Points
	 * 
	 * @param row
	 *            the value to be stored in the X spot in a point P
	 * @param col
	 *            the value to be stored in the Y spot in a point P
	 */
	private void recordTurn(int row, int col) {
		Point p = new Point(row, col);
		record.add(p);
	}

	/**
	 * This method is a helper method that sets every spot on the board to
	 * empty- mainly useful in creating a new game.
	 */
	private void clearBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = Cell.EMPTY;
			}
		}
	}

	/**
	 * This method is called whenever a player wishes to undo the previous
	 * selection on the board. It uses the ArrayList of Points to determine
	 * which spot should be reverted, and it keeps track of whose turn it was
	 * and will be after calling this method. Nothing is done if it is the first
	 * turn of the game.
	 */
	public void undo() {
		if (turnNumber > 0) {
			int index = turnNumber - 1;
			int row, col;
			row = (int) record.get(index).getX();
			col = (int) record.get(index).getY();
			board[row][col] = Cell.EMPTY;
			if (playerOne == true)
				playerOne = false;
			else
				playerOne = true;
			turnNumber--;
			record.remove(index);
		} else {
		}
	}

	/**
	 * This method is called when the user selects the save button. An
	 * appropriate FileChooser is used, and the user will save the game to a
	 * .txt file with a desired filename. A specific format is followed when
	 * saving the game to the .txt file. The following information is saved: the
	 * size of the board, the turn number the game is on, the player that
	 * started first, whose turn it currently is, and all of the occupied spaces
	 * on the game board.
	 * 
	 */
	public void save() {
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);
		if (status != JFileChooser.APPROVE_OPTION)
			JOptionPane.showMessageDialog(null, "The game has not been saved.");
		else {
			File filename = chooser.getSelectedFile();
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(filename)));
				out.println(boardSize);
				out.println(turnNumber);
				out.println(startingTurn);
				out.println(playerOne);
				for (Point i : record) {
					out.println((int) i.getX() + "," + (int) i.getY());
				}
				out.close();
			} catch (IOException error) {
				System.out.println("There was an error in writing the file.");
			}
		}

	}

	/**
	 * This method is called whenever the user wishes to load a game from a .txt
	 * file. An appropriate FileChooser is used. The data from the file is read
	 * in a specific format and can't be successfully loaded if the format is
	 * not followed. Only the fields of the game object are modified through
	 * this method.
	 */
	public void load() {
		String line;
		String delimiter = ",";
		int row, col;
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (status != JFileChooser.APPROVE_OPTION)
			JOptionPane.showMessageDialog(null, "No game has been loaded.");
		else {
			File file = chooser.getSelectedFile();
			try {
				Scanner scan = new Scanner(file);
				record = new ArrayList<Point>();
				boardSize = Integer.parseInt(scan.nextLine());
				turnNumber = Integer.parseInt(scan.nextLine());
				startingTurn = scan.nextLine();
				line = scan.nextLine();
				if (line.equals("true"))
					playerOne = true;
				else
					playerOne = false;
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					String[] point = line.split(delimiter);
					row = Integer.parseInt(point[0]);
					col = Integer.parseInt(point[1]);
					recordTurn(row, col);
				}

				boolean currentTurn;
				if (startingTurn.equals("x") || startingTurn.equals("X")) {
					currentTurn = true;
				} else {
					currentTurn = false;
				}

				board = new Cell[boardSize][boardSize];
				clearBoard();

				for (Point i : record) {
					row = (int) i.getX();
					col = (int) i.getY();
					if (currentTurn == true) {
						board[row][col] = Cell.X;
						currentTurn = false;
					} else {
						board[row][col] = Cell.O;
						currentTurn = true;
					}
				}

				this.status = GameStatus.IN_PROGRESS;
				scan.close();

			} catch (FileNotFoundException error) {
				JOptionPane.showMessageDialog(null, "File not found.");
			} catch (NoSuchElementException error) {
				JOptionPane.showMessageDialog(null,
						"There was an error reading the file.");
			}
		}
	}

	/**
	 * Briefly tests some of the methods without using a GUI.
	 */
	public static void main(String[] args) {
		SuperTicTacToeGame test = new SuperTicTacToeGame("X", 3);
		test.select(0, 0);
		test.getGameStatus();
		test.select(0, 1);
		test.getGameStatus();
		test.select(0, 2);
		test.getGameStatus();
		test.select(1, 1);
		test.getGameStatus();
		test.select(2, 1);
		test.getGameStatus();
		test.select(1, 0);
		test.getGameStatus();
		test.select(2, 0);
		test.getGameStatus();
		test.select(1, 2);
		test.getGameStatus();
		test.newGame();

		test.select(1, 1);
		test.getGameStatus();
		test.select(0, 0);
		test.getGameStatus();
		test.select(0, 1);
		test.getGameStatus();
		test.select(0, 2);
		test.getGameStatus();
		test.select(2, 1);
		test.getGameStatus();

	}

}
