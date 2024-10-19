package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.Game;
import com.example.miniproyecto2.view.GameStage;
import com.example.miniproyecto2.view.WelcomeStage;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for the Welcome stage of the Sudoku application.
 */
public class WelcomeController {

    /**
     * Invoked when the user presses the "Play" button.
     *
     * @throws IOException if an input or output error occurs.
     */
    @FXML
    public void handlePlayButton() throws IOException {
        Game game = new Game();
        WelcomeStage.deletedInstance();
        GameStage.getInstance().getGameController().setGame(game);
    }
}
