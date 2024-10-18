package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Game implements IGame {

    private ArrayList<ArrayList<Integer>> board; // Tablero de juego 6x6
    private final int helpUsed; // Contador de las ayudas utilizadas

    // Constructor
    public Game() {
        this.board = new ArrayList<>();
        this.helpUsed = 0;
    }

    @Override
    public void initializeBoard() {
        Random random = new Random();

        // Tablero vacío inicial
        board = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                board.get(i).add(0); // Llena inicialmente con ceros (celdas vacías)
            }
        }

        // Coloca números aleatorios en algunas posiciones (pero no todos)
        // Aquí respetaremos las reglas del Sudoku (sin repeticiones en filas, columnas o bloques)
        for (int i = 0; i < 6; i++) {
            HashSet<Integer> usedNumbers = new HashSet<>(); // Para verificar que no se repitan en la fila
            for (int j = 0; j < 6; j++) {
                // Decidimos si la celda se llenará con un número aleatorio o quedará vacía
                if (random.nextBoolean()) {
                    int number;
                    do {
                        number = random.nextInt(6) + 1; // Números del 1 al 6
                    } while (usedNumbers.contains(number) || !isValidInBlock(i, j, number));

                    usedNumbers.add(number);
                    board.get(i).set(j, number); // Coloca el número en la celda
                }
            }
        }
    }

    @Override
    public boolean validateMove(String move, TextField textField) {
        try {
            int number = Integer.parseInt(move);

            if (number < 1 || number > 6) {
                textField.setStyle("-fx-background-color: red;");
                return false; // El número no es válido en el rango del Sudoku
            }

            // Aquí puedes agregar lógica para validar filas y columnas si es necesario
            textField.setStyle("-fx-background-color: green;");
            return true;
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-background-color: red;");
            return false; // El movimiento no es un número válido
        }
    }

    @Override
    public void makeMove(String move) {
        // La lógica de hacer un movimiento puede incluir actualizar el tablero con el nuevo valor
        System.out.println("Movimiento realizado: " + move);
    }

    @Override
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    @Override
    public int getHelpUsed() {
        return helpUsed;
    }

    @Override
    public boolean isGameOver() {
        // Lógica simple que podría verificar si todas las celdas están llenas y válidas
        for (ArrayList<Integer> row : board) {
            for (Integer cell : row) {
                if (cell == 0) {
                    return false; // El juego no ha terminado si hay celdas vacías
                }
            }
        }
        return true; // Si no hay celdas vacías, el juego ha terminado
    }

    /**
     * Verifica si el número es válido dentro de su bloque 2x3.
     *
     * @param row La fila de la celda.
     * @param col La columna de la celda.
     * @param number El número a verificar.
     * @return true si el número es válido en su bloque, false en caso contrario.
     */
    private boolean isValidInBlock(int row, int col, int number) {
        // Determina el bloque 2x3 en el que está la celda (row, col)
        int blockRow = (row / 2) * 2;
        int blockCol = (col / 3) * 3;

        // Verifica que el número no esté repetido en el bloque 2x3
        for (int i = blockRow; i < blockRow + 2; i++) {
            for (int j = blockCol; j < blockCol + 3; j++) {
                if (board.get(i).get(j) == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
