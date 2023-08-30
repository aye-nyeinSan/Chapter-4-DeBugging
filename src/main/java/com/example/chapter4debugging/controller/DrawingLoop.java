package com.example.chapter4debugging.controller;

import com.example.chapter4debugging.model.Character;
import com.example.chapter4debugging.view.Platform;
import javafx.application.Application;
import javafx.stage.Stage;

public class DrawingLoop implements Runnable{
    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;
    public DrawingLoop(Platform platform){
        this.platform = platform;
        frameRate = 30;
        interval =1000.0f/frameRate; // 1000 ms=1 second
        running = true;
    }
    private void checkDrawCollisions(Character character){
        character.checkReachHighest();
        character.checkReachFloor();
        character.checkReachGameWall();
    }
    private void paint(Character character){
        character.repaint();
    }
    @Override
    public void run() {
        while(running){
            float time = System.currentTimeMillis();
            checkDrawCollisions(platform.getCharacter());
            paint (platform.getCharacter());
            time = System.currentTimeMillis()-time;
            if(time < interval){

                    try {
                        Thread.sleep((long)(interval - time));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
