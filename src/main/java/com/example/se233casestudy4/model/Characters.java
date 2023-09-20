package com.example.se233casestudy4.model;

import com.example.se233casestudy4.view.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Characters extends Pane {
    private static final Logger logger = LogManager.getLogger(Characters.class);
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private Platform platform;
    private int x;
    private int y;
    private int startX;
    private int startY;
    private int offsetX;
    private int offsetY;

   private Name type;
   private int score = 0;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 5;
    int yMaxVelocity = 15;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    public Characters(int x, int y, Image characterImg, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.startX=x;
        this.startY=y;
        this.characterImg = characterImg;
        this.imageView = new AnimatedSprite(characterImg, 4,4,1,0,0,16, 32);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;

        this.getChildren().addAll(this.imageView);
    }

    public Characters(int x, int y, int offsetX, int offsetY, KeyCode keyCode, KeyCode keyCode1, KeyCode keyCode2) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.leftKey = keyCode;
        this.rightKey = keyCode1;
        this.upKey = keyCode2;
    }

    public void collided(Characters c){

        if(isMoveLeft){
            x = c.getX()+CHARACTER_WIDTH + 1;
            stop();
        } else if (isMoveRight) {
            x = c.getX() - CHARACTER_WIDTH -1;
            stop();
        }
        if( y < Platform.GROUND - CHARACTER_HEIGHT){
            if( isFalling && y < c.getY() &&
            Math.abs( y - c.getY()) <= CHARACTER_HEIGHT + 1 ){
                score++;
                y = Platform.GROUND - CHARACTER_HEIGHT - 5;
                repaint();
                c.collapsed();
                c.respawn();
            }
        }
    }

    private void collapsed() {
        imageView.setFitHeight(5);
        y = Platform.GROUND -5 ;
        repaint();
        try{
            TimeUnit.MILLISECONDS.sleep(500);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void respawn() {
        x = startX;
        y = startY;
        imageView.setFitWidth(CHARACTER_WIDTH);
        imageView.setFitHeight(CHARACTER_HEIGHT);
        isMoveLeft = false;
        isMoveRight = false;
        isFalling=true;
        canJump = false;
        isJumping = false;
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x+getWidth() >= Platform.WIDTH) {
            x = Platform.WIDTH - (int)getWidth();
        }
    }
    public void checkReachHighest() {
        if(isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor() {
        if (isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void jump() {

        if(canJump ) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }


     public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}",x,y,xVelocity,yVelocity);
 }


    public void moveY() {
        setTranslateY(y);
        if(isFalling) {
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity;
        } else if(isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }
    public void moveLeft() {
        isMoveLeft = true;
        isMoveRight = false;
    }


    public void moveRight() {
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void stop() {
        isMoveLeft = false;
        isMoveRight = false;
    }
    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity + xAcceleration;
            x = x - xVelocity;
        }
        if(isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity + xAcceleration;
            x = x + xVelocity;
        }

    }
    public KeyCode getLeftKey() {
        return leftKey;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }
    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public void setUpKey(KeyCode upKey) {
        this.upKey = upKey;
    }
    public Image getCharacterImg() {
        return characterImg;
    }

    public void setCharacterImg(Image characterImg) {
        this.characterImg = characterImg;
    }
    public void setImageView(AnimatedSprite imageView) {
        this.imageView = imageView;
    }

    public void setLeftKey(KeyCode leftKey) {
        this.leftKey = leftKey;
    }

    public Name getType() {
        return type;
    }

    public void setType(Name type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int  getScore() {
        return score;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setRightKey(KeyCode rightKey) {
        this.rightKey = rightKey;
    }

}
