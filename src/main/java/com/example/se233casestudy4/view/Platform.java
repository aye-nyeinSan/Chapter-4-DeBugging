package com.example.se233casestudy4.view;

import com.example.se233casestudy4.Launcher;
import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.model.Keys;
import com.example.se233casestudy4.model.Name;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Platform extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int GROUND = 300;


    private Image platformImg;
    private ArrayList<Characters> characterList;
    private ArrayList<Score> scoreList;
    private Keys keys;
    public Platform() {
        keys = new Keys();
        characterList = new ArrayList<>();
        scoreList = new ArrayList<>();
        platformImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(platformImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        characterList.add(new Characters(30,30, new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png")), Name.Mario,0,0,KeyCode.A,KeyCode.D,KeyCode.W));
        characterList.add(new Characters(730,30,new Image(Launcher.class.getResourceAsStream("assets/MarioSheetGreen.png")),Name.MarioGreen,0,0,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP));
      scoreList.add(new Score(30,GROUND+30));
      scoreList.add(new Score(Platform.WIDTH-60,GROUND+30));
       getChildren().addAll(backgroundImg);
       getChildren().addAll(characterList);
       getChildren().addAll(scoreList);
    }


    public ArrayList<Characters> getCharacterList( ) {
        return characterList;
    }

    public Keys getKeys() {
        return keys;
    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
