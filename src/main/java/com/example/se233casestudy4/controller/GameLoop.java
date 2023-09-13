package com.example.se233casestudy4.controller;

import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.model.Name;
import com.example.se233casestudy4.view.Platform;
import com.example.se233casestudy4.view.Score;
import javafx.scene.control.Label;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.reflect.Array.get;


public class GameLoop implements Runnable {
    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;




    public GameLoop(Platform platform) {
        this.platform = platform;
        frameRate = 10;
        interval = 1000.0f / frameRate;
        running = true;

    }
    private void updateScore(ArrayList<Score> scoreList, ArrayList<Characters> characterList) {
        javafx.application.Platform.runLater(()->{
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setPoint(characterList.get(i).getScore());
            }
        });
    }

    private void update(ArrayList<Characters> characterList) {

        for (Characters character : characterList ) {
            if (platform.getKeys().isPressed(character.getLeftKey())) {
                character.setScaleX(-1);
                character.moveLeft();
            }
            if (platform.getKeys().isPressed(character.getRightKey())) {
                character.setScaleX(1);
                character.moveRight();
            }
            if (!platform.getKeys().isPressed(character.getLeftKey()) && !platform.getKeys().isPressed(character.getRightKey())) {
                character.stop();
            }
            if (platform.getKeys().isPressed(character.getLeftKey()) || platform.
                    getKeys().isPressed(character.getRightKey())) {
                character.getImageView().tick();
            }
            if (platform.getKeys().isPressed(character.getUpKey())) {
                character.jump();
            }
        }
    }




    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
           update(platform.getCharacterList());
           updateScore(platform.getScoreList(),platform.getCharacterList());

            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
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



