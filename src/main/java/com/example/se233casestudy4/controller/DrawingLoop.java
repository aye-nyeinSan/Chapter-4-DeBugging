package com.example.se233casestudy4.controller;

import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.view.Platform;

import java.util.ArrayList;

public class DrawingLoop implements Runnable{
    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;
    public DrawingLoop(Platform platform) {
        this.platform = platform;
        frameRate = 30;
        interval = 1000.0f / frameRate;
        running = true;
    }
    private void checkDrawCollisions(ArrayList<Characters> characterList) {
        for (Characters character:characterList) {
            character.checkReachGameWall();
            character.checkReachHighest();
            character.checkReachFloor();
        }
        for (Characters cA:characterList) {
            for (Characters cB:characterList) {
                if(cA != cB){
                    if (cA.getBoundsInParent().intersects((cB.getBoundsInParent()))){
                        cA.collided(cB);
                        cB.collided(cA);
                        return;
                    }
                }
            }

        }


    }
    private void paint(ArrayList<Characters> characterlist)
    {
        for (Characters character: characterlist) {
            character.repaint();
        }

    }

    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            checkDrawCollisions(platform.getCharacterList());


            paint(platform.getCharacterList());

            time = System.currentTimeMillis() - time;
            if(time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
