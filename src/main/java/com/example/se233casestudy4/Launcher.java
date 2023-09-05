package com.example.se233casestudy4;

import com.example.se233casestudy4.controller.GameLoop;
import com.example.se233casestudy4.controller.DrawingLoop;
import com.example.se233casestudy4.view.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch((args));
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform platform = new Platform();
        GameLoop gameLoop = new GameLoop(platform);
        DrawingLoop drawingLoop = new DrawingLoop(platform);
        Scene scene = new Scene(platform, Platform.WIDTH, Platform.HEIGHT);
        scene.setOnKeyPressed(event -> platform.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event -> platform.getKeys().remove(event.getCode()));
        primaryStage.setTitle("platformer");
        primaryStage.setScene(scene);
        primaryStage.show();
        try{
            (new Thread(gameLoop)).start();
            (new Thread(drawingLoop)).start();
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
