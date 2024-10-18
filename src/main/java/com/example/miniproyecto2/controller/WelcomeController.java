package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.model.Game;
import com.example.miniproyecto2.view.GameStage;
import com.example.miniproyecto2.view.WelcomeStage;
import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController {

    // Este método se invoca cuando el usuario presiona el botón "Jugar".
    @FXML
    public void handlePlayButton() throws IOException {
        Game game = new Game();
        WelcomeStage.deletedInstance();
        GameStage.getInstance().getGameController().setGame(game);

    }
}
