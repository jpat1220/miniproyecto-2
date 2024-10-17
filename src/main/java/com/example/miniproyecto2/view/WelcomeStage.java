package com.example.miniproyecto2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the start stage for the "Sudoku!" application.
 * This class extends the Stage class and initializes the start view.
 */
public class WelcomeStage extends Stage {

    /**
     * Constructor for the WelcomeStage class.
     * Loads the FXML file for the start view and sets up the stage.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/miniproyecto2/welcome-view.fxml"
        ));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku!");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/miniproyecto2/img/favicon.png"))
        ));
        setResizable(false);
        show();
    }

    /**
     * Holds the singleton instance of WelcomeStage.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    /**
     * Returns the singleton instance of WelcomeStage.
     * If the instance does not exist, it creates one.
     *
     * @return the singleton WelcomeStage instance.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static WelcomeStage getInstance() throws IOException {
        WelcomeStageHolder.INSTANCE =
                WelcomeStageHolder.INSTANCE != null ? WelcomeStageHolder.INSTANCE : new WelcomeStage();
        return WelcomeStageHolder.INSTANCE;
    }

    /**
     * Closes the current instance of WelcomeStage.
     */
    public static void deletedInstance() {
        WelcomeStageHolder.INSTANCE.close();
    }
}
