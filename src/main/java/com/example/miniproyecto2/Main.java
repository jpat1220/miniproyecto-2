package com.example.miniproyecto2;

import com.example.miniproyecto2.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeStage.getInstance();
    }
}
