package com.example.miniproyecto2.model;

import javafx.scene.control.TextField;

public interface IGame {
    void initializeBoard();
    boolean validateMove(String move, TextField textField); // Debe tener esta firma
    void makeMove(String move, int row, int col); // Se añade row y col
    int[][] getBoard();
    int getHelpUsed();
    boolean isGameOver();
}
