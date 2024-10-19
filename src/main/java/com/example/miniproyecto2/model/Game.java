package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;
import java.util.Random;

/**
 * Implements the game logic for the Sudoku application.
 */
public class Game implements IGame {
    private int[][] board;
    private int helpUsed;

    /**
     * Constructs a new Game instance, initializing the board and helpUsed.
     */
    public Game() {
        this.board = new int[6][6];
        this.helpUsed = 0;
    }

    /**
     * Initializes the Sudoku board with valid numbers.
     */
    @Override
    public void initializeBoard() {
        fillBoard();
    }

    /**
     * Validates the move made in the given TextField.
     *
     * @param move     the move to validate as a String.
     * @param textField the TextField associated with the move.
     * @return true if the move is valid (1 to 6), false otherwise.
     */
    @Override
    public boolean validateMove(String move, TextField textField) {
        if (move == null || move.isEmpty()) {
            return false; // Return false if move is null or empty
        }
        // Check if the move is a valid integer within the range 1 to 6
        return move.matches("[1-6]");
    }

    /**
     * Makes a move on the board at the specified row and column.
     *
     * @param move the value to place on the board.
     * @param row  the row where the move will be made.
     * @param col  the column where the move will be made.
     */
    @Override
    public void makeMove(String move, int row, int col) {
        int value = Integer.parseInt(move);
        if (isValidMove(value, row, col)) {
            board[row][col] = value;
        }
    }

    /**
     * Gets the current state of the board.
     *
     * @return a 2D array representing the board.
     */
    @Override
    public int[][] getBoard() {
        return board;
    }

    /**
     * Gets the number of help used in the game.
     *
     * @return the count of help used.
     */
    @Override
    public int getHelpUsed() {
        return helpUsed;
    }

    /**
     * Checks if the game is over by verifying if the board is full and valid.
     *
     * @return true if the game is over, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        if (isBoardFull()) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    int number = board[row][col];
                    if (isNumberInRow(row, number) ||
                            isNumberInCol(col, number) ||
                            isNumberInBlock((row / 2) * 2, (col / 3) * 3, number)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void fillBoard() {
        Random random = new Random();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                board[row][col] = 0;
            }
        }
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                placeNumbersInBlock(blockRow * 2, blockCol * 3, random);
            }
        }
    }

    private void placeNumbersInBlock(int startRow, int startCol, Random random) {
        int[] numbers = {1, 2, 3, 4, 5, 6};
        int count = 0;

        while (count < 2) {
            int number = numbers[random.nextInt(6)];
            boolean placedSuccessfully = false;

            for (int row = startRow; row < startRow + 2; row++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    if (board[row][col] == 0 &&
                            !isNumberInRow(row, number) &&
                            !isNumberInCol(col, number) &&
                            !isNumberInBlock(startRow, startCol, number)) {
                        board[row][col] = number;
                        placedSuccessfully = true;
                        count++;
                        break;
                    }
                }
                if (placedSuccessfully) {
                    break;
                }
            }
        }
    }

    private boolean isNumberInRow(int row, int number) {
        for (int col = 0; col < 6; col++) {
            if (board[row][col] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInCol(int col, int number) {
        for (int row = 0; row < 6; row++) {
            if (board[row][col] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBlock(int startRow, int startCol, int number) {
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (board[row][col] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes a help move by placing a number in a random empty position.
     */
    public void makeHelpMove() {
        Random rand = new Random();
        int row, col;

        for (int attempt = 0; attempt < 100; attempt++) {
            row = rand.nextInt(6);
            col = rand.nextInt(6);

            if (board[row][col] == 0) {
                int number = rand.nextInt(6) + 1;

                if (isValidMove(number, row, col)) {
                    board[row][col] = number;
                    helpUsed++;
                    break;
                }
            }
        }
    }

    /**
     * Checks if the given number can be placed in the specified row and column.
     *
     * @param number the number to check.
     * @param row    the row index.
     * @param col    the column index.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int number, int row, int col) {
        if (isNumberInRow(row, number) || isNumberInCol(col, number)) {
            return false;
        }

        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;

        return !isNumberInBlock(startRow, startCol, number);
    }

    /**
     * Increments the help used counter.
     */
    public void incrementHelpUsed() {
        helpUsed++;
    }

    /**
     * Checks if the board is full (contains no empty cells).
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the board and resets the help used counter.
     */
    public void clearBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = 0;
            }
        }
        helpUsed = 0;
    }
}
