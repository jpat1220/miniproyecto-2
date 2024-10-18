package com.example.miniproyecto2.view;

import com.example.miniproyecto2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the game stage for the "El Sol Eclipsado" application.
 * This class extends the Stage class and initializes the main game view.
 */
public class GameStage extends Stage {
    private final GameController gameController;

    /**
     * Constructor for the GameStage class.
     * Loads the FXML file for the game view and sets up the stage.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/miniproyecto2/game-view.fxml"
        ));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("El Sol Eclipsado");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/miniproyecto2/img/favicon.png"))
        ));
        setResizable(false);
        show();
    }

    /**
     * Returns the GameController associated with this stage.
     *
     * @return the GameController instance.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Holds the singleton instance of GameStage.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of GameStage.
     * If the instance does not exist, it creates one.
     *
     * @return the singleton GameStage instance.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static GameStage getInstance() throws IOException {
        GameStage.GameStageHolder.INSTANCE =
                GameStage.GameStageHolder.INSTANCE != null ? GameStage.GameStageHolder.INSTANCE : new GameStage();
        return GameStage.GameStageHolder.INSTANCE;
    }

    /**
     * Closes the current instance of GameStage.
     */
    public static void deletedInstance() {
        GameStage.GameStageHolder.INSTANCE.close();
    }
}
