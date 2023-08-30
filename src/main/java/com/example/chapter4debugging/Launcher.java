package com.example.chapter4debugging;

import com.example.chapter4debugging.controller.DrawingLoop;
import com.example.chapter4debugging.controller.GameLoop;
import com.example.chapter4debugging.view.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Platform platform = new Platform();
        GameLoop gameLoop =new GameLoop(platform);
        DrawingLoop drawingLoop = new DrawingLoop(platform);


        Scene scene = new Scene(platform, Platform.WIDTH, Platform.HEIGHT);
        scene.setOnKeyPressed(event -> platform.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event -> platform.getKeys().remove(event.getCode()));
        stage.setTitle("PLATFORMER!");
        stage.setScene(scene);
        stage.show();
        (new Thread(gameLoop)).start();
        (new Thread(drawingLoop)).start();

    }
}
