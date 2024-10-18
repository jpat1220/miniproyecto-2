package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;
import java.util.Random;

public class Game implements IGame {
    private int[][] board;
    private int helpUsed;

    public Game() {
        this.board = new int[6][6]; // Inicializa el tablero 6x6
        this.helpUsed = 0;
    }

    @Override
    public void initializeBoard() {
        fillBoard(); // Llenar el tablero con números válidos
    }

    @Override
    public boolean validateMove(String move, TextField textField) {
        // Aquí puedes implementar la validación del movimiento
        // Por ahora, podemos hacer una simple validación de que el número es del 1 al 6
        int value;
        try {
            value = Integer.parseInt(move);
            return value >= 1 && value <= 6; // Solo se permiten números del 1 al 6
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }

    @Override
    public void makeMove(String move) {
        // Aquí puedes implementar la lógica para hacer un movimiento
    }

    @Override
    public int[][] getBoard() {
        return board; // Retorna el tablero actual
    }

    @Override
    public int getHelpUsed() {
        return helpUsed; // Retorna el número de ayudas utilizadas
    }

    @Override
    public boolean isGameOver() {
        // Aquí puedes implementar la lógica para verificar si el juego ha terminado
        return false; // Retorna falso por ahora
    }

    private void fillBoard() {
        Random random = new Random();
        // Limpiar el tablero
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                board[row][col] = 0; // Inicializa todas las celdas en 0
            }
        }

        // Llenar el tablero asegurando que cada bloque 2x3 tenga solo 2 números
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                placeNumbersInBlock(blockRow * 2, blockCol * 3, random);
            }
        }
    }

    private void placeNumbersInBlock(int startRow, int startCol, Random random) {
        int[] numbers = {1, 2, 3, 4, 5, 6}; // Números del Sudoku
        int count = 0; // Contador de números colocados

        // Intentar colocar 2 números en posiciones válidas
        while (count < 2) {
            int number = numbers[random.nextInt(6)]; // Seleccionar un número aleatorio
            boolean placedSuccessfully = false; // Para verificar si se colocó exitosamente

            // Probar cada posición en el bloque 2x3
            for (int row = startRow; row < startRow + 2; row++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    // Verificar si la posición es válida
                    if (board[row][col] == 0 &&
                            !isNumberInRow(row, number) &&
                            !isNumberInCol(col, number) &&
                            !isNumberInBlock(startRow, startCol, number)) { // Verificar también en el bloque

                        // Colocar el número en la posición actual
                        board[row][col] = number;
                        placedSuccessfully = true; // Se colocó exitosamente
                        count++; // Incrementar el contador
                        break; // Salir del bucle de columnas
                    }
                }
                if (placedSuccessfully) {
                    break; // Salir del bucle de filas si se colocó el número
                }
            }
        }
    }

    private boolean isNumberInRow(int row, int number) {
        for (int col = 0; col < 6; col++) {
            if (board[row][col] == number) {
                return true; // El número ya está en la fila
            }
        }
        return false;
    }

    private boolean isNumberInCol(int col, int number) {
        for (int row = 0; row < 6; row++) {
            if (board[row][col] == number) {
                return true; // El número ya está en la columna
            }
        }
        return false;
    }

    private boolean isNumberInBlock(int startRow, int startCol, int number) {
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (board[row][col] == number) {
                    return true; // El número ya está en el bloque
                }
            }
        }
        return false;
    }
}
