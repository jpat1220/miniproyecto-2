package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.IGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameController {
    private IGame game; // Instancia de IGame

    @FXML
    private GridPane gridPane; // GridPane donde se mostrará el tablero
    @FXML
    private TextField textField; // Campo de texto para la entrada del jugador
    @FXML
    private Button submitButton; // Botón para enviar el movimiento
    @FXML
    private Text messageText; // Text para mostrar mensajes

    public void setGame(IGame game) {
        this.game = game; // Establecer el juego
        initializeBoard(); // Inicializar el tablero
    }

    private void initializeBoard() {
        game.initializeBoard(); // Inicializar el tablero en el modelo
        ArrayList<ArrayList<Integer>> board = game.getBoard(); // Obtener el tablero

        // Rellenar el GridPane con TextFields para cada celda del tablero
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                TextField cell = new TextField();
                cell.setText(String.valueOf(board.get(row).get(col))); // Establecer el número
                cell.setEditable(false); // Hacer que las celdas no sean editables
                gridPane.add(cell, col, row); // Agregar al GridPane
            }
        }
    }

    @FXML
    public void initialize() {
        // Manejar el evento del botón de envío
        submitButton.setOnAction(event -> {
            final String move = textField.getText(); // Obtener el movimiento del campo de texto
            int row = 0; // Definir la fila (esto se puede modificar según la lógica)
            int col = 0; // Definir la columna (esto se puede modificar según la lógica)

            if (game.validateMove(move, textField)) {
                game.makeMove(move); // Realizar el movimiento
                updateBoard(); // Actualizar el tablero visualmente
                if (game.isGameOver()) {
                    messageText.setText("¡Juego terminado!");
                } else {
                    messageText.setText("Movimiento válido.");
                }
            } else {
                messageText.setText("Movimiento inválido.");
                // Resaltar el campo de texto si el movimiento es inválido
                textField.setStyle("-fx-background-color: red;"); // Resaltar en rojo
            }
        });
    }

    private void updateBoard() {
        // Método para actualizar el tablero visualmente después de cada movimiento
        ArrayList<ArrayList<Integer>> board = game.getBoard(); // Obtener el tablero actualizado

        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                TextField cell = (TextField) gridPane.getChildren().get(row * board.size() + col);
                cell.setText(String.valueOf(board.get(row).get(col))); // Actualizar el texto de la celda
            }
        }
    }
}
