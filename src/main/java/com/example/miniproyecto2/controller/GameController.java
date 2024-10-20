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
import java.util.Objects;
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
        showRulesAlert();
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
                gridPane.setStyle(
                                "-fx-background-image: url('" + Objects.requireNonNull(getClass().getResource("/com/example/miniproyecto2/img/board-bg.png")).toExternalForm() + "'); " +
                                        "-fx-background-repeat: repeat;"+
                                        "-fx-background-size: auto;      "// Esto hace que la imagen se repita
                );
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
                    int number = Integer.parseInt(inputText);
                    if (game.isValidMove(number, row, col)) {
                        game.makeMove(inputText, row, col);
                        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");

                        // Imprime la matriz después de hacer el movimiento
                    } else {
                        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
                        highlightConflictingNumbers(cell, row, col);

                    }
                } else {
                    cell.clear();
                    resetHighlighting();
                }

                if (game.isBoardFull()) {
                    if (game.isGameOver()){
                        showVictoryMessage();
                    }
                    else{
                        showErrorMessage();
                    }
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
                if (cellNode instanceof TextField cell) {
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
    /**
     * Handles the help button action.
     */
    @FXML
    private void handleHelpButton() {
        if (game.getHelpUsed() < 6 && !game.isBoardFull()) { // Verifica si aún quedan ayudas
            boolean numberPlaced = false;
            int[][] currentBoard = game.getBoard();
            int[][] answerBoard = game.getAnswerBoard();
            // Recorre el tablero buscando una posición vacía
            for (int row = 0; row < 6 && !numberPlaced; row++) {
                for (int col = 0; col < 6 && !numberPlaced; col++) {
                    if (currentBoard[row][col] == 0) { // Si la posición está vacía
                        int correctNumber = answerBoard[row][col];
                        System.out.println(correctNumber);// Obtiene el número correcto desde answerBoard
                        game.makeMove(String.valueOf(correctNumber), row, col); // Actualiza el tablero en el modelo
                        Node node = getNodeByRowColumnIndex(row, col, gridPane); // Encuentra el TextField correspondiente
                        if (node instanceof TextField cell) {
                            cell.setText(String.valueOf(correctNumber)); // Coloca el número en la interfaz
                            cell.setEditable(false);
                            cell.setStyle("-fx-text-fill: blue; -fx-background-color: null");// Bloquea la celda
                            game.incrementHelpUsed();
                        }
                        updateHelpLabel(); // Actualiza el label de ayudas restantes
                        numberPlaced = true; // Marca que se ha colocado un número
                    }
                }
            }
        }
        if (game.isBoardFull()) {
            if (game.isGameOver()){
                showVictoryMessage();
            }
            else{
                showErrorMessage();
            }
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

    private void showErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Error!");
        alert.setHeaderText(null);
        alert.setContentText("¡Tu sudoku esta lleno pero hay un error!");
        alert.showAndWait();
    }

    @FXML
    private void showRulesAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Sudoku 6x6");
        alert.setHeaderText("Cómo jugar al Sudoku 6x6");
        alert.setContentText(
                "El Sudoku 6x6 es un juego de lógica y números.\n\n" +
                        "Reglas:\n" +
                        "- El tablero tiene 6 filas y 6 columnas, divididas en bloques de 2x3.\n" +
                        "- Debes llenar las celdas vacías con números del 1 al 6.\n" +
                        "- Cada fila, columna y bloque debe contener todos los números del 1 al 6 sin repetir.\n\n" +
                        "Instrucciones:\n" +
                        "- Haz clic en una celda vacía y escribe un número del 1 al 6.\n" +
                        "- Si el número que ingresas es válido, se añadirá al tablero.\n" +
                        "- Si el número no es valido, se resaltará de color rojo indicando que debes reemplazarlo\n" +
                        "- Usa las ayudas disponibles si te quedas atascado.\n" +
                        "- Completa el tablero siguiendo las reglas para ganar el juego.\n\n" +
                        "¡Diviértete y buena suerte!"
        );
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
        game.setHelpUsed();
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
