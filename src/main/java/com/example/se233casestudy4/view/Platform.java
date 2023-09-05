package com.example.se233casestudy4.view;

import com.example.se233casestudy4.Launcher;
import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.model.Keys;
import com.example.se233casestudy4.model.Name;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Platform extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int GROUND = 300;
    private Characters character;
    private Characters greenCharacter;
    private Image platformImg;
    private Keys keys;
    public Platform() {
        keys = new Keys();
        platformImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(platformImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        character = new Characters(30,30, new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png")), Name.Mario,0,0,KeyCode.A,KeyCode.D,KeyCode.W);
       greenCharacter = new Characters(730,30,new Image(Launcher.class.getResourceAsStream("assets/MarioSheetGreen.png")),Name.MarioGreen,0,0,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP);
        getChildren().addAll(backgroundImg,character,greenCharacter);
    }
    public Characters getCharacter()
    {
        return character;
    }

    public void setCharacter(Characters character) {
        this.character = character;
    }

    public Characters getGreenCharacter() {
        return greenCharacter;
    }


    public Image getPlatformImg() {
        return platformImg;
    }

    public void setPlatformImg(Image platformImg) {
        this.platformImg = platformImg;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public Keys getKeys() {
        return keys;
    }
}
