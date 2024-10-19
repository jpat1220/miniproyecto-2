package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label; // Importa Label para el helpLabel
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class GameController {
    private Game game; // Instancia de Game

    @FXML
    private GridPane gridPane; // GridPane donde se mostrará el tablero

    @FXML
    private Label helpLabel; // Label para mostrar las ayudas restantes

    // Método para inicializar el juego
    public void setGame(Game game) {
        this.game = game; // Crear una instancia de Game
        initializeBoard(); // Inicializar el tablero
        updateHelpLabel(); // Actualizar el label de ayuda al inicio
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
                    if (game.validateMove(inputText, cell)) {
                        game.makeMove(inputText, row, col); // Realizar el movimiento
                        cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;"); // Texto blanco, sin fondo
                        highlightConflictingNumbers(cell, row, col); // Resaltar conflictos
                    } else {
                        cell.setStyle("-fx-text-fill: red; -fx-background-color: transparent;"); // Texto rojo si es inválido
                    }
                } else {
                    cell.clear(); // Limpiar el campo si no es un número válido
                    resetHighlighting(); // Limpia todos los estilos
                }
            }
        });
    }

    // Método para resaltar números en conflicto
    private void highlightConflictingNumbers(TextField currentCell, int row, int col) {
        int currentValue;
        try {
            currentValue = Integer.parseInt(currentCell.getText());
        } catch (NumberFormatException e) {
            return; // Si no hay número, salir del método
        }

        // Limpiar el resaltado antes de verificar nuevos conflictos
        resetHighlighting();

        // Resaltar filas y columnas
        for (int i = 0; i < 6; i++) {
            if (i != col) {
                Node cellNode = getNodeByRowColumnIndex(row, i, gridPane);
                if (cellNode instanceof TextField) {
                    TextField cell = (TextField) cellNode;
                    if (cell.getText().equals(String.valueOf(currentValue))) {
                        cell.setStyle("-fx-background-color: red;"); // Resaltar celda en conflicto
                        currentCell.setStyle("-fx-background-color: red;"); // Resaltar la celda actual también
                    }
                }
            }
            if (i != row) {
                Node cellNode = getNodeByRowColumnIndex(i, col, gridPane);
                if (cellNode instanceof TextField) {
                    TextField cell = (TextField) cellNode;
                    if (cell.getText().equals(String.valueOf(currentValue))) {
                        cell.setStyle("-fx-background-color: red;"); // Resaltar celda en conflicto
                        currentCell.setStyle("-fx-background-color: red;"); // Resaltar la celda actual también
                    }
                }
            }
        }

        // Resaltar en el bloque 2x3
        int blockStartRow = (row / 2) * 2;
        int blockStartCol = (col / 3) * 3;
        for (int r = blockStartRow; r < blockStartRow + 2; r++) {
            for (int c = blockStartCol; c < blockStartCol + 3; c++) {
                if (r != row || c != col) {
                    Node cellNode = getNodeByRowColumnIndex(r, c, gridPane);
                    if (cellNode instanceof TextField) {
                        TextField cell = (TextField) cellNode;
                        if (cell.getText().equals(String.valueOf(currentValue))) {
                            cell.setStyle("-fx-background-color: red;"); // Resaltar celda en conflicto
                            currentCell.setStyle("-fx-background-color: red;"); // Resaltar la celda actual también
                        }
                    }
                }
            }
        }
    }

    // Método para obtener el nodo por su índice de fila y columna
    private Node getNodeByRowColumnIndex(int row, int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            Integer nodeColIndex = GridPane.getColumnIndex(node);

            // Comprobar si los índices son nulos y asignar un valor por defecto
            if (nodeRowIndex == null) {
                nodeRowIndex = 0; // O un valor que tenga sentido para ti
            }
            if (nodeColIndex == null) {
                nodeColIndex = 0; // O un valor que tenga sentido para ti
            }

            if (nodeRowIndex == row && nodeColIndex == col) {
                return node; // Retornar el nodo si coincide con la fila y columna
            }
        }
        return null; // Retornar nulo si no se encuentra el nodo
    }

    // Método para aplicar estilos a los TextFields
    private void styleTextField(TextField cell) {
        cell.setFont(Font.font("Berlin Sans FB", 24)); // Establecer la fuente
        cell.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"); // Estilo del TextField
        cell.setPrefSize(50, 50); // Tamaño preferido
        cell.setAlignment(javafx.geometry.Pos.CENTER); // Alinear texto al centro
    }

    // Método para restablecer resaltados
    private void resetHighlighting() {
        // Limpia todos los estilos de las celdas
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                node.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        }
    }

    // Método para solicitar ayuda
    public void handleHelpButton() {
        if (game.getHelpUsed() < 6) { // Verifica que no se hayan usado más de 6 ayudas
            // Obtener una posición aleatoria
            placeRandomHelpNumber(); // Llama al método para colocar el número de ayuda
            game.incrementHelpUsed(); // Incrementa el número de ayudas usadas
            updateHelpLabel(); // Actualiza el label de ayuda
        } else {
            // Aquí puedes agregar lógica si se intentan usar más de 6 ayudas, si lo deseas
            System.out.println("No quedan ayudas disponibles."); // Mensaje de aviso (opcional)
        }
    }

    // Método que coloca un número de ayuda en una celda aleatoria
    private void placeRandomHelpNumber() {
        int[][] board = game.getBoard(); // Obtener el tablero
        Random random = new Random();
        int row, col;
        int number;

        // Busca una celda vacía aleatoria donde colocar el número de ayuda
        do {
            row = random.nextInt(6); // Generar fila aleatoria
            col = random.nextInt(6); // Generar columna aleatoria
            number = getValidNumber(); // Obtener un número válido del 1 al 6
        } while (!game.isValidMove(number, row, col) || board[row][col] != 0); // Asegurarse que la celda esté vacía

        // Colocar el número en la celda
        game.makeMove(String.valueOf(number), row, col); // Hacer el movimiento en el modelo

        // Actualizar la interfaz gráfica
        TextField cell = (TextField) getNodeByRowColumnIndex(row, col, gridPane);
        if (cell != null) {
            cell.setText(String.valueOf(number)); // Mostrar el número en la celda
            cell.setEditable(false); // No editable
            cell.setStyle("-fx-text-fill: white; -fx-background-color: transparent;"); // Restablecer estilo
        }
    }

    // Método para obtener un número válido
    private int getValidNumber() {
        return new Random().nextInt(6) + 1; // Números del 1 al 6
    }

    // Método para actualizar el label de ayuda
    private void updateHelpLabel() {
        helpLabel.setText("Ayudas restantes: " + (6 - game.getHelpUsed())); // Actualizar el texto del label de ayuda
    }
}
