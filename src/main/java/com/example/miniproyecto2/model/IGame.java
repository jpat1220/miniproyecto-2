package com.example.miniproyecto2.model;

/**
 * Represents the game logic for the Sudoku application.
 * This interface defines the essential methods for managing the game state.
 */
public interface IGame {

    /**
     * Selects a random board and its corresponding answer.
     */
    void initializeBoard();

    /**
     * Makes a move on the board.
     *
     * @param number the number to place.
     * @param row the row index.
     * @param col the column index.
     */
    void makeMove(String number, int row, int col);

    /**
     * Returns the current board being played.
     *
     * @return the current board.
     */
    int[][] getBoard();

    /**
     * Gets the number of helps used.
     *
     * @return the number of helps used.
     */
    int getHelpUsed();

    /**
     * Checks if the game is over by verifying if the board is full and
     * all rows, columns, and blocks are valid.
     *
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();
}
