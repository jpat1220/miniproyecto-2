package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;

/**
 * Represents the game logic for the Sudoku application.
 * This interface defines the essential methods for managing the game state.
 */
public interface IGame {

    /**
     * Initializes the Sudoku board.
     */
    void initializeBoard();

    /**
     * Validates a move made by the player.
     *
     * @param move the move to validate.
     * @param textField the TextField associated with the move.
     * @return true if the move is valid, false otherwise.
     */
    boolean validateMove(String move, TextField textField);

    /**
     * Makes a move on the board.
     *
     * @param move the move to be made.
     * @param row the row index of the move.
     * @param col the column index of the move.
     */
    void makeMove(String move, int row, int col);

    /**
     * Retrieves the current state of the Sudoku board.
     *
     * @return a 2D array representing the board.
     */
    int[][] getBoard();

    /**
     * Returns the number of help requests used.
     *
     * @return the number of help used.
     */
    int getHelpUsed();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();
}
