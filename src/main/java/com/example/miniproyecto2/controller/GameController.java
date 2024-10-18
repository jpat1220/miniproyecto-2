package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Node;

public class GameController {
    private Game game; // Instancia de Game

    @FXML
    private GridPane gridPane; // GridPane donde se mostrará el tablero

    // Método para inicializar el juego
    public void setGame(Game game) {
        this.game = game; // Crear una instancia de Game
        initializeBoard(); // Inicializar el tablero
    }

    // Inicializa el tablero en la interfaz gráfica
    private void initializeBoard() {
        game.initializeBoard(); // Inicializar el tablero en el modelo
        int[][] board = game.getBoard(); // Obtener el tablero

        // Rellenar el GridPane con TextFields para cada celda del tablero
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                TextField cell = new TextField();
                if (board[row][col] != 0) {
                    cell.setText(String.valueOf(board[row][col]));
                    cell.setEditable(false); // No editable si el valor es mayor que 0
                } else {
                    cell.clear(); // Limpiar para celdas que pueden ser editadas
                    handleTextField(cell, row, col); // Manejar eventos para celdas editables
                }
                styleTextField(cell); // Aplicar estilos a las celdas
                gridPane.add(cell, col, row); // Agregar la celda al GridPane
            }
        }
    }

    // Método para manejar los eventos de las celdas
    private void handleTextField(TextField cell, int row, int col) {
        cell.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String inputText = cell.getText();

                if (inputText.matches("[1-6]")) { // Verificar si el número es válido (1-6)
                    int inputNumber = Integer.parseInt(inputText);

                    // Validar si el movimiento es permitido según las reglas del Sudoku
                    if (game.validateMove(inputText, cell)) {
                        game.makeMove(inputText); // Realizar el movimiento
                        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;"); // Texto blanco, sin fondo
                    } else {
                        cell.setStyle("-fx-text-fill: red; -fx-background-color: transparent;"); // Texto rojo si es inválido
                    }
                } else {
                    cell.clear(); // Limpiar el campo si no es un número válido
                }
            }
        });
    }

    // Método para aplicar estilos a los TextField
    private void styleTextField(TextField cell) {
        cell.setFont(Font.font("Berlin Sans FB", 18)); // Aplicar fuente y tamaño de texto
        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;"); // Texto blanco, sin fondo
        cell.setMaxWidth(50); // Limitar el tamaño para mejor visualización
        cell.setMaxHeight(50);
        cell.setAlignment(javafx.geometry.Pos.CENTER); // Alinear el texto al centro
    }

    // Método para actualizar el tablero visualmente después de cada movimiento
    private void updateBoard() {
        int[][] board = game.getBoard(); // Obtener el tablero actualizado

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                TextField cell = (TextField) getNodeByRowColumnIndex(row, col, gridPane);
                if (cell != null) {
                    cell.setText(board[row][col] == 0 ? "" : String.valueOf(board[row][col])); // Actualizar la celda
                }
            }
        }
    }

    // Método para obtener un nodo del GridPane por fila y columna
    private Node getNodeByRowColumnIndex(int row, int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null; // Retornar nulo si no se encuentra el nodo
    }
}
