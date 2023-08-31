package com.example.chapter4debugging.model;


import com.example.chapter4debugging.Launcher;
import com.example.chapter4debugging.view.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Character extends Pane {
    public static final int CHARACTER_WIDTH =32;
    public static final int CHARACTER_HEIGHT=64;
    private static final Logger logger = LogManager.getLogger(Character.class);
    private Image characterImg;

    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
     private KeyCode upKey;
    int yVelocity = 0;
    int xVelocity = 0;
    int xAccelearation = 1;
    int yAccelearation = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    boolean isMoveLeft= false;
    boolean isMoveRight = false;
    boolean isFalling = true;
     boolean canJump = false;
    boolean isJumping = false;
   // int highestJump = 100;
    public Character (int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey){
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);

        this.characterImg =new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
        this.imageView =new AnimatedSprite(characterImg,4,4,1,offsetX,offsetY,16,32);
        this.leftKey =leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;

        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.getChildren().addAll(this.imageView);
    }



public void moveY(){
    setTranslateY(y);
    if(isFalling){
        yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAccelearation;
        y = y+ yVelocity;
    } else if (isJumping) {
        yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAccelearation;
        y =y - yVelocity;
    }
}
    public void moveX(){
     setTranslateX(x);
    if(isMoveLeft) {
        xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAccelearation;
        x = x - xVelocity;
    }
    if (isMoveRight) {
        xVelocity = xVelocity>= xMaxVelocity ? xMaxVelocity : xVelocity + xAccelearation;
        x = x + xVelocity;}
    }
public void moveLeft(){
   // x=x-xVelocity;
    isMoveLeft = true;
    isMoveRight = false;
}
    public void moveRight(){
       // x=x+xVelocity;
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void stop(){
        // xVelocity=0;
        isMoveLeft = false;
        isMoveRight = false;
    }
    public void repaint(){
        moveX();
        moveY();
    }
    public void checkReachFloor(){
        if(isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT){
        isFalling = false;
        canJump = true;
        yVelocity=0;
    }
}

 public void checkReachGameWall(){
    if(x <= 0){
        x = 0;
    } else if(x+getWidth() >= Platform.WIDTH){
        x= Platform.WIDTH -(int) getWidth();
    }
}
    public void checkReachHighest(){
        if(isJumping && yVelocity <=0)
            isJumping=false;
            isFalling=true;
            yVelocity=0;

        }
    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;


        }
    }
    public void trace(){
  //  System.out.println(String.format("x:%d y:%d vx:%d",x,y,xVelocity,yVelocity));
        logger.info("x:{} y:{} vx:{} vy:{}",x,y,xVelocity,yVelocity);

    }
    public KeyCode getUpKey() {
        return upKey;
    }

    public void setUpKey(KeyCode upKey) {
        this.upKey = upKey;
    }
    public KeyCode getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(KeyCode leftKey) {
        this.leftKey = leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public void setRightKey(KeyCode rightKey) {
        this.rightKey = rightKey;
    }

    public int getxAccelearation() {
        return xAccelearation;
    }

    public void setxAccelearation(int xAccelearation) {
        this.xAccelearation = xAccelearation;
    }

    public int getyAccelearation() {
        return yAccelearation;
    }

    public void setyAccelearation(int yAccelearation) {
        this.yAccelearation = yAccelearation;
    }

    public int getxMaxVelocity() {
        return xMaxVelocity;
    }

    public void setxMaxVelocity(int xMaxVelocity) {
        this.xMaxVelocity = xMaxVelocity;
    }

    public int getyMaxVelocity() {
        return yMaxVelocity;
    }

    public void setyMaxVelocity(int yMaxVelocity) {
        this.yMaxVelocity = yMaxVelocity;
    }


    public Image getCharacterImg() {
        return characterImg;
    }

    public void setCharacterImg(Image characterImg) {
        this.characterImg = characterImg;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public void setImageView(AnimatedSprite imageView) {
        this.imageView = imageView;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        isMoveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setMoveRight(boolean moveRight) {
        isMoveRight = moveRight;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }


}




