package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.Game;
import com.example.miniproyecto2.view.GameStage;
import com.example.miniproyecto2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Random;

/**
 * Controller for the Game stage of the Sudoku application.
 */
public class GameController {
    private Game game;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label helpLabel;

    /**
     * Initializes the game.
     *
     * @param game the Game instance to be set.
     */
    public void setGame(Game game) {
        this.game = game;
        initializeBoard();
        updateHelpLabel();
    }

    /**
     * Initializes the board in the graphical interface.
     */
    private void initializeBoard() {
        game.initializeBoard();
        int[][] board = game.getBoard();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                TextField cell = new TextField();
                if (board[row][col] != 0) {
                    cell.setText(String.valueOf(board[row][col]));
                    cell.setEditable(false);
                } else {
                    cell.clear();
                    handleTextField(cell, row, col);
                }
                styleTextField(cell);
                gridPane.add(cell, col, row);
                gridPane.setGridLinesVisible(true);
            }
        }
    }

    /**
     * Handles the events of the cells.
     *
     * @param cell the TextField representing the cell.
     * @param row  the row index of the cell.
     * @param col  the column index of the cell.
     */
    private void handleTextField(TextField cell, int row, int col) {
        cell.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String inputText = cell.getText();

                if (inputText.matches("[1-6]")) {
                    if (game.validateMove(inputText, cell)) {
                        game.makeMove(inputText, row, col);
                        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
                        highlightConflictingNumbers(cell, row, col);

                        if (game.isGameOver()) {
                            showVictoryMessage();
                        }
                    } else {
                        cell.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");
                    }
                } else {
                    cell.clear();
                    resetHighlighting();
                }
            }
        });
    }

    /**
     * Highlights conflicting numbers.
     *
     * @param currentCell the TextField representing the current cell.
     * @param row         the row index of the current cell.
     * @param col         the column index of the current cell.
     */
    private void highlightConflictingNumbers(TextField currentCell, int row, int col) {
        int currentValue;
        if (!currentCell.getText().isEmpty()) {
            currentValue = Integer.parseInt(currentCell.getText());
        } else {
            return;
        }

        resetHighlighting();

        for (int i = 0; i < 6; i++) {
            if (i != col) {
                Node cellNode = getNodeByRowColumnIndex(row, i, gridPane);
                if (cellNode instanceof TextField) {
                    TextField cell = (TextField) cellNode;
                    if (cell.getText().equals(String.valueOf(currentValue))) {
                        cell.setStyle("-fx-background-color: red;");
                        currentCell.setStyle("-fx-background-color: red;");
                    }
                }
            }
            if (i != row) {
                Node cellNode = getNodeByRowColumnIndex(i, col, gridPane);
                if (cellNode instanceof TextField) {
                    TextField cell = (TextField) cellNode;
                    if (cell.getText().equals(String.valueOf(currentValue))) {
                        cell.setStyle("-fx-background-color: red;");
                        currentCell.setStyle("-fx-background-color: red;");
                    }
                }
            }
        }

        int blockStartRow = (row / 2) * 2;
        int blockStartCol = (col / 3) * 3;
        for (int r = blockStartRow; r < blockStartRow + 2; r++) {
            for (int c = blockStartCol; c < blockStartCol + 3; c++) {
                if (r != row || c != col) {
                    Node cellNode = getNodeByRowColumnIndex(r, c, gridPane);
                    if (cellNode instanceof TextField) {
                        TextField cell = (TextField) cellNode;
                        if (cell.getText().equals(String.valueOf(currentValue))) {
                            cell.setStyle("-fx-background-color: red;");
                            currentCell.setStyle("-fx-background-color: red;");
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the node by its row and column index.
     *
     * @param row      the row index of the node.
     * @param col      the column index of the node.
     * @param gridPane the GridPane containing the nodes.
     * @return the Node if found, null otherwise.
     */
    private Node getNodeByRowColumnIndex(int row, int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            Integer nodeColIndex = GridPane.getColumnIndex(node);

            if (nodeRowIndex == null) {
                nodeRowIndex = 0;
            }
            if (nodeColIndex == null) {
                nodeColIndex = 0;
            }

            if (nodeRowIndex == row && nodeColIndex == col) {
                return node;
            }
        }
        return null;
    }

    /**
     * Applies styles to the TextFields.
     *
     * @param cell the TextField to be styled.
     */
    private void styleTextField(TextField cell) {
        gridPane.setGridLinesVisible(true);
        cell.setFont(Font.font("Berlin Sans FB", 24));
        cell.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        cell.setPrefSize(50, 50);
        cell.setAlignment(javafx.geometry.Pos.CENTER);
    }

    /**
     * Resets the highlighting of cells.
     */
    private void resetHighlighting() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                node.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        }
    }

    /**
     * Handles the help button action.
     */
    public void handleHelpButton() {
        if (game.getHelpUsed() < 6) {
            if (!game.isBoardFull()) {
                placeRandomHelpNumber();
                game.incrementHelpUsed();
                updateHelpLabel();
            }
        } else {
            System.out.println("No quedan ayudas disponibles.");
        }
    }

    /**
     * Places a help number in a random empty cell.
     */
    private void placeRandomHelpNumber() {
        int[][] board = game.getBoard();
        Random random = new Random();
        int row, col;
        int number;

        do {
            row = random.nextInt(6);
            col = random.nextInt(6);
            number = getValidNumber();
        } while (!game.isValidMove(number, row, col) || board[row][col] != 0);

        TextField cell = (TextField) getNodeByRowColumnIndex(row, col, gridPane);
        if (cell != null) {
            cell.setText(String.valueOf(number));
            game.makeMove(String.valueOf(number), row, col);
            highlightConflictingNumbers(cell, row, col);
        }
    }

    /**
     * Gets a valid number between 1 and 6.
     *
     * @return a random number between 1 and 6.
     */
    private int getValidNumber() {
        return new Random().nextInt(6) + 1;
    }

    /**
     * Updates the help label.
     */
    private void updateHelpLabel() {
        helpLabel.setText("Ayudas restantes: " + (6 - game.getHelpUsed()));
    }

    /**
     * Shows a victory message.
     */
    private void showVictoryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡Felicidades, has completado el Sudoku!");
        alert.showAndWait();
    }

    /**
     * Handles the restart button action.
     *
     * @param event the ActionEvent triggered by the button.
     * @throws IOException if an input or output error occurs.
     */
    @FXML
    public void handleRestartButton(ActionEvent event) throws IOException {
        gridPane.getChildren().clear();
        game.clearBoard();
        game.initializeBoard();
        initializeBoard();
        updateHelpLabel();
        gridPane.setGridLinesVisible(true);
    }

    /**
     * Handles the exit button action.
     *
     * @param event the ActionEvent triggered by the button.
     * @throws IOException if an input or output error occurs.
     */
    @FXML
    public void handleExitButton(ActionEvent event) throws IOException {
        GameStage.deletedInstance();

    }
}
