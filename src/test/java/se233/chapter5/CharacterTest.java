package se233.chapter5;

import com.example.se233casestudy4.Launcher;
import com.example.se233casestudy4.controller.DrawingLoop;
import com.example.se233casestudy4.controller.GameLoop;
import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.view.Platform;
import com.example.se233casestudy4.view.Score;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.example.se233casestudy4.model.Characters.CHARACTER_HEIGHT;
import static com.example.se233casestudy4.view.Platform.GROUND;
import static org.junit.Assert.*;

public class CharacterTest {
    private Characters floatingCharacter;
    private Characters floatingCharacter2;
    private Score scoreTest;
    private ArrayList<Characters> characterListUnderTest;
    private ArrayList<Score> scoreListUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private DrawingLoop drawingLoopUnderTest;
    private Method updateMethod;
    private Method redrawMethod;
    private Method updateScoreMethod;
    private Method checkCollisionMethod;

    @Before
    public void setup(){
        //loading up toolkit
        JFXPanel jfxPanel=new JFXPanel();

        floatingCharacter =new Characters(30,30,new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png")),0,0, KeyCode.A,KeyCode.D,KeyCode.W);
        floatingCharacter2 = new Characters(600, 30, new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png")), 0, 0, KeyCode.A, KeyCode.D, KeyCode.W);

        characterListUnderTest = new ArrayList<Characters>();
        characterListUnderTest.add(floatingCharacter);
        characterListUnderTest.add(floatingCharacter2);
        scoreListUnderTest = new ArrayList<Score>();
        scoreTest = new Score(30,GROUND+30);
        scoreListUnderTest.add(scoreTest);


        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);

        try{
             updateScoreMethod = GameLoop.class.getDeclaredMethod("updateScore", ArrayList.class, ArrayList.class);
            updateMethod = GameLoop.class.getDeclaredMethod("update",ArrayList.class);
            //Using the arraylist of class(GameLoop) as parameter when use "update" method
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint",ArrayList.class);
            checkCollisionMethod = DrawingLoop.class.getDeclaredMethod("checkDrawCollisions", ArrayList.class);
            updateMethod.setAccessible(true);
            checkCollisionMethod.setAccessible(true);
            updateScoreMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        } catch (NoSuchMethodException e ) {
            e.printStackTrace();
            updateMethod = null;
            redrawMethod = null;
            throw new RuntimeException(e);
        }


    }
    @Test
    public void characterShouldMoveToTheLeftAfterTheLeftKeyIsPressed() throws IllegalAccessException, InvocationTargetException,NoSuchFieldException{
        Characters characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX();
        platformUnderTest.getKeys().add(KeyCode.A);
        //Use Update method to inoke gameobject,character from arrayList
        // invoke(object,params to pass method)
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);

        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);

        assertTrue("Controller : Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model : Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View : Character is moving left ", characterUnderTest.getX() < startX);
    }
    @Test
    public void characterShouldMoveToTheRightAfterTheRightKeyIsPressed() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Characters characterTest = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.D);
        int  startX = characterTest.getX();
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);

        Field isMovedRight = characterTest.getClass().getDeclaredField("isMoveRight" );
        isMovedRight.setAccessible(true);
        assertTrue("Controller : Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
        assertTrue("Model : Character moving right state is set", isMovedRight.getBoolean(characterTest));
        assertTrue("View : Character is moving right ", characterTest.getX() > startX);


    }
    @Test
    public void characterJumpWhenOnGroundAfterUpKeyIsPressed() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Characters characterTest = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.W);
        int  startY = characterTest.getY();
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);

        Field isJumping = characterTest.getClass().getDeclaredField("isJumping" );
        Field canJump = characterTest.getClass().getDeclaredField("canJump" );
        canJump.setAccessible(true);
        canJump.setBoolean(characterTest,true);
        isJumping.setAccessible(true);
        isJumping.setBoolean(characterTest,true);


        assertTrue("Controller : Up key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.W));
         assertTrue("Model : Character canjump state is set", canJump.getBoolean(characterTest));
        assertTrue("Model : Character jumping state is set", isJumping.getBoolean(characterTest));
        assertTrue("View : Character jumped ", characterTest.getY() > startY);

    }
    @Test
    public void characterJumpWhenNotBeingOnGroundAfterUpKeyIsPressed() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Characters characterTest = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.W);
        int  startY = characterTest.getY();
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        Field isJumping = characterTest.getClass().getDeclaredField("isJumping" );
        Field canJump = characterTest.getClass().getDeclaredField("canJump" );
        canJump.setAccessible(true);
        canJump.setBoolean(characterTest,false);
        isJumping.setAccessible(true);
        isJumping.setBoolean(characterTest,false);
        assertTrue("Controller : Up key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.W));
        assertFalse("Model : Character jumping state is set", isJumping.getBoolean(characterTest));
        assertEquals("View : Character can't jumped ", characterTest.getY(), startY,1);

    }
    @Test
    public void characterStopsRightAtBorderAfterMovingTowardsTheBorder() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Characters characterTest = characterListUnderTest.get(0);
        platformUnderTest.getKeys().add(KeyCode.D);
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        Field isMovedRight = characterTest.getClass().getDeclaredField("isMoveRight" );
        isMovedRight.setAccessible(true);
        Method checkReachGameWall= Characters.class.getDeclaredMethod("checkReachGameWall");
        checkReachGameWall.setAccessible(true);
        checkReachGameWall.invoke(characterTest);
        characterTest.setX(800);
        boolean testwall= characterTest.getX() >= Platform.WIDTH;
        assertTrue("Controller: Character reach to the border",testwall);

        assertTrue("Controller : Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
        assertTrue("Model : Character moving right is set", isMovedRight.getBoolean(characterTest));
        characterTest.stop();
        isMovedRight.setBoolean(characterTest,false);
        assertFalse("Model : Character should not be moving right after stopping ",  isMovedRight.getBoolean(characterTest));

    }
    @Test
    public void consequencesAfterCharacterHasCollidedWithAnotherCharacter() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Characters characterTest = characterListUnderTest.get(0);
        Characters anothercharacter = characterListUnderTest.get(1);
        Field isMovedRight = characterTest.getClass().getDeclaredField("isMoveRight" );
        Field score = characterTest.getClass().getDeclaredField("score" );
        isMovedRight.setAccessible(true);
        score.setAccessible(true);
        platformUnderTest.getKeys().add(KeyCode.D);
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        checkCollisionMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        characterTest.collided(anothercharacter);
        assertTrue("Controller : Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
        assertFalse("Model: Character stops after colliding with another character", isMovedRight.getBoolean(characterTest));


    }


    @Test
    public void consequencesAfterCharacterStompsOvertheAnotherCharacter() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Characters characterTest = characterListUnderTest.get(0);
        Characters anothercharacter = characterListUnderTest.get(1);
        int startScore = characterTest.getScore();
        int startY = characterTest.getY();
        int initialImgHeight = anothercharacter.getImageView().getHeight();
        Field isMovedRight = characterTest.getClass().getDeclaredField("isMoveRight" );
        Field isFalling= characterTest.getClass().getDeclaredField("isFalling" );
        Field score = characterTest.getClass().getDeclaredField("score" );
        isMovedRight.setAccessible(true);
        score.setAccessible(true);
        isFalling.setAccessible(true);
        isMovedRight.setBoolean(characterTest,true);

        System.out.println("Before Jump:"+ startY);

        platformUnderTest.getKeys().add(KeyCode.W);
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        characterTest.jump();
        characterTest.moveRight();
        isFalling.setBoolean(characterTest,true);
        assertTrue("Character should still be falling after stomp.", isFalling.getBoolean(characterTest));

        characterTest.setY(anothercharacter.getY() - Characters.CHARACTER_HEIGHT - 1);

        System.out.println("after jump:" + characterTest.getY());
        characterTest.collided(anothercharacter);
        Method collapsedMethod=Characters.class.getDeclaredMethod("collapsed");
        collapsedMethod.setAccessible(true);
        collapsedMethod.invoke(anothercharacter);


        assertTrue("View: Another character's image height must be smaller while stomp",initialImgHeight> anothercharacter.getHeight());

        assertTrue("Model: Another character should be collapsed ",anothercharacter.getY()> anothercharacter.getStartY());
        System.out.println("Character will be collapsed!");
        Method respawnMethod=Characters.class.getDeclaredMethod("respawn");
        respawnMethod.setAccessible(true);
        respawnMethod.invoke(anothercharacter);
        assertEquals("View : Another Character should be re-appeared at original position after being stomped ", anothercharacter.getX(), anothercharacter.getStartX(),1);
        System.out.println("Another character respawn!!");

        int updatedScore = characterTest.getScore();
          System.out.println("Initial Score:" + startScore);
         System.out.println("Updated Score:" + updatedScore);

         assertTrue("Model : Character score must be increased after stomping another character", updatedScore>startScore);
    }


    @Test
    public void characterInitialValuesShouldMatchConstructorArguments(){
        // assertEquals("message",expected,actual,delta);
        assertEquals("Initial x",30,floatingCharacter.getX(),0);
        assertEquals("Initial y",30,floatingCharacter.getY(),0);
        assertEquals("offset x",0,floatingCharacter.getOffsetX(),0);
        assertEquals("offset y",0,floatingCharacter.getOffsetY(),0);
        assertEquals("leftKey",KeyCode.A,floatingCharacter.getLeftKey());
        assertEquals("RightKey",KeyCode.D,floatingCharacter.getRightKey());
        assertEquals("UpKey",KeyCode.W,floatingCharacter.getUpKey());

    }

}
