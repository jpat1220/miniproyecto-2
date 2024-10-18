package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;

import java.util.ArrayList;

public interface IGame {
    /**
     * Inicializa el tablero del juego y lo llena con números válidos.
     */
    void initializeBoard();

    /**
     * Valida si el movimiento realizado es correcto.
     *
     * @param move El movimiento a validar.
     * @param textField El campo de texto asociado al movimiento.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    boolean validateMove(String move, TextField textField);

    /**
     * Realiza un movimiento en el juego.
     *
     * @param move El movimiento a realizar.
     */
    void makeMove(String move);

    /**
     * Devuelve el tablero del juego.
     *
     * @return El tablero como una lista de listas de enteros.
     */
    ArrayList<ArrayList<Integer>> getBoard();

    /**
     * Devuelve el número de ayudas utilizadas en el juego.
     *
     * @return El número de ayudas utilizadas.
     */
    int getHelpUsed();

    /**
     * Verifica si el juego ha terminado.
     *
     * @return true si el juego ha terminado, false en caso contrario.
     */
    boolean isGameOver();
}
