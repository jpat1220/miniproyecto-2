package com.example.miniproyecto2.model;

import java.util.Random;

public class Game implements IGame {

    private int[][] currentBoard;
    private int[][] currentAnswer;
    private int[][][] boards = new int[5][6][6];
    private int[][][] answerBoards = new int[5][6][6];
    private int helpUsed;

    /**
     * Constructs a new Game instance, initializes the default boards,
     * selects a random board, and sets the initial help count to zero.
     */
    public Game() {
        initializeDefaultBoards();
        initializeBoard();
        helpUsed = 0;
    }


    /**
     * Initializes the default boards and their corresponding answers.
     */
    private void initializeDefaultBoards() {
        boards[0] = new int[][]{
                {2, 0, 0, 1, 6, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 3, 0, 0, 0, 4},
                {1, 0, 0, 0, 5, 0},
                {0, 0, 2, 0, 4, 0},
                {0, 0, 6, 0, 0, 2}
        };
        answerBoards[0] = new int[][]{
                {2, 4, 3, 1, 6, 5},
                {5, 6, 1, 4, 2, 3},
                {6, 3, 5, 2, 1, 4},
                {1, 2, 4, 3, 5, 6},
                {3, 5, 2, 6, 4, 1},
                {4, 1, 6, 5, 3, 2}
        };
        boards[1] = new int[][]{
                {4, 1, 0, 0, 6, 3},
                {0, 0, 0, 0, 0, 0},
                {5, 4, 0, 0, 3, 6},
                {0, 0, 0, 0, 0, 0},
                {1, 3, 0, 0, 2, 4},
                {0, 0, 0, 0, 0, 0}
        };
        answerBoards[1] = new int[][]{
                {4, 1, 2, 5, 6, 3},
                {3, 5, 6, 1, 4, 2},
                {5, 4, 1, 2, 3, 6},
                {2, 6, 3, 4, 1, 5},
                {1, 3, 5, 6, 2, 4},
                {6, 2, 4, 3, 5, 1}
        };
        boards[2] = new int[][]{
                {0, 2, 0, 0, 3, 0},
                {0, 4, 0, 0, 6, 0},
                {0, 3, 0, 0, 0, 6},
                {0, 5, 0, 0, 4, 0},
                {0, 6, 0, 1, 0, 4},
                {0, 1, 0, 0, 0, 0}
        };
        answerBoards[2] = new int[][]{
                {1, 2, 6, 4, 3, 5},
                {3, 4, 5, 2, 6, 1},
                {4, 3, 2, 5, 1, 6},
                {6, 5, 1, 3, 4, 2},
                {2, 6, 3, 1, 5, 4},
                {5, 1, 4, 6, 2, 3}
        };
        boards[3] = new int[][]{
                {1, 0, 0, 0, 0, 6},
                {0, 5, 0, 0, 3, 0},
                {0, 0, 3, 1, 0, 0},
                {0, 0, 1, 3, 0, 0},
                {0, 2, 0, 0, 1, 0},
                {3, 0, 0, 0, 0, 4}
        };
        answerBoards[3] = new int[][]{
                {1, 3, 2, 4, 5, 6},
                {4, 5, 6, 2, 3, 1},
                {2, 6, 3, 1, 4, 5},
                {5, 4, 1, 3, 6, 2},
                {6, 2, 4, 5, 1, 3},
                {3, 1, 5, 6, 2, 4}
        };
        boards[4] = new int[][]{
                {0, 2, 0, 0, 5, 0},
                {0, 0, 6, 0, 1, 0},
                {2, 0, 0, 3, 0, 0},
                {3, 0, 0, 0, 0, 4},
                {0, 3, 0, 6, 0, 2},
                {0, 0, 1, 0, 0, 0}
        };
        answerBoards[4] = new int[][]{
                {1, 2, 3, 4, 5, 6},
                {4, 5, 6, 1, 2, 3},
                {2, 1, 4, 3, 6, 5},
                {3, 6, 5, 2, 1, 4},
                {5, 3, 1, 6, 4, 2},
                {6, 4, 2, 5, 3, 1}
        };
    }

    /**
     * Selects a random board and its corresponding answer.
     */
    @Override
    public void initializeBoard() {
        initializeDefaultBoards();
        Random random = new Random();
        int boardIndex = random.nextInt(5);
        currentBoard = boards[boardIndex];
        currentAnswer = answerBoards[boardIndex];
    }

    /**
     * Returns the current board being played.
     *
     * @return the current board.
     */
    public int[][] getBoard() {
        return currentBoard;
    }

    /**
     * Returns the answer to the current board.
     *
     * @return the answer board.
     */
    public int[][] getAnswerBoard() {
        return currentAnswer;
    }

    /**
     * Validates if the move is valid.
     *
     * @param number the number to be placed.
     * @param row the row index.
     * @param col the column index.
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
     * Checks if a number is present in a specified row.
     *
     * @param row the row index.
     * @param number the number to check.
     * @return true if the number is in the row, false otherwise.
     */
    private boolean isNumberInRow(int row, int number) {
        for (int col = 0; col < 6; col++) {
            if (currentBoard[row][col] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a number is present in a specified column.
     *
     * @param col the column index.
     * @param number the number to check.
     * @return true if the number is in the column, false otherwise.
     */
    private boolean isNumberInCol(int col, int number) {
        for (int row = 0; row < 6; row++) {
            if (currentBoard[row][col] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a number is present in a specified block.
     *
     * @param startRow the starting row index of the block.
     * @param startCol the starting column index of the block.
     * @param number the number to check.
     * @return true if the number is in the block, false otherwise.
     */
    private boolean isNumberInBlock(int startRow, int startCol, int number) {
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (currentBoard[row][col] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes a move on the board.
     *
     * @param number the number to place.
     * @param row the row index.
     * @param col the column index.
     */
    @Override
    public void makeMove(String number, int row, int col) {
        currentBoard[row][col] = Integer.parseInt(number);
        printCurrentBoard();
    }

    /**
     * Prints the current board to the console.
     */
    public void printCurrentBoard() {
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                System.out.print(currentBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }


    /**
     * Checks if the game is over by verifying if the board is full and
     * all rows, columns, and blocks are valid.
     *
     * @return true if the game is over, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        if (!isBoardFull()) {
            return false;
        }

        for (int row = 0; row < 6; row++) {
            if (!isRowValid(row)) {
                return false;
            }
        }

        for (int col = 0; col < 6; col++) {
            if (!isColValid(col)) {
                return false;
            }
        }

        for (int row = 0; row < 6; row += 2) {
            for (int col = 0; col < 6; col += 3) {
                if (!isBlockValid(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a specified row is valid by ensuring all numbers from 1 to 6
     * appear only once.
     *
     * @param row the row index.
     * @return true if the row is valid, false otherwise.
     */
    private boolean isRowValid(int row) {
        boolean[] seen = new boolean[7]; // Using 7 because numbers range from 1 to 6
        for (int col = 0; col < 6; col++) {
            int number = currentBoard[row][col];
            if (number != 0) {
                if (seen[number]) {
                    return false;
                }
                seen[number] = true;
            }
        }
        return true;
    }

    /**
     * Checks if a specified column is valid by ensuring all numbers from 1 to 6
     * appear only once.
     *
     * @param col the column index.
     * @return true if the column is valid, false otherwise.
     */
    private boolean isColValid(int col) {
        boolean[] seen = new boolean[7];
        for (int row = 0; row < 6; row++) {
            int number = currentBoard[row][col];
            if (number != 0) {
                if (seen[number]) {
                    return false;
                }
                seen[number] = true;
            }
        }
        return true;
    }

    /**
     * Checks if a specified block is valid by ensuring all numbers from 1 to 6
     * appear only once.
     *
     * @param startRow the starting row index of the block.
     * @param startCol the starting column index of the block.
     * @return true if the block is valid, false otherwise.
     */
    private boolean isBlockValid(int startRow, int startCol) {
        boolean[] seen = new boolean[7];
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int number = currentBoard[row][col];
                if (number != 0) {
                    if (seen[number]) {
                        return false;
                    }
                    seen[number] = true;
                }
            }
        }
        return true;
    }


    /**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (currentBoard[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Increments the help used counter.
     */
    public void incrementHelpUsed() {
        helpUsed++;
    }

    /**
     * Gets the number of helps used.
     *
     * @return the number of helps used.
     */
    @Override
    public int getHelpUsed() {
        return helpUsed;
    }

    /**
     * Resets the help used counter to zero.
     */
    public void setHelpUsed() {
        this.helpUsed = 0;
    }

    /**
     * Clears the board by resetting it to a zero-filled state.
     */
    public void clearBoard() {
        currentBoard = new int[6][6];
    }

}
