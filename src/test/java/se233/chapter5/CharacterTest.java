package se233.chapter5;

import com.example.se233casestudy4.controller.DrawingLoop;
import com.example.se233casestudy4.controller.GameLoop;
import com.example.se233casestudy4.model.Characters;
import com.example.se233casestudy4.view.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CharacterTest {
    private Characters floatingCharacter;
    private ArrayList<Characters> characterListUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private DrawingLoop drawingLoopUnderTest;
    private Method updateMethod,redrawMethod;
    @Before
    public void setup(){
        JFXPanel jfxPanel = new JFXPanel();
        floatingCharacter =new Characters(30,30,0,0, KeyCode.A,KeyCode.D,KeyCode.W);
        characterListUnderTest = new ArrayList<Characters>();
        characterListUnderTest.add(floatingCharacter);
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);
        try{
            updateMethod = GameLoop.class.getDeclaredMethod("update",ArrayList.class);
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint",ArrayList.class);
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
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
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Controller : Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model : Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View : Character is moving left ", characterUnderTest.getX()<startX);
    }
    @Test
    public void characterInitialValuesShouldMatchConstructorArguments(){
        assertEquals("Initial x",30,floatingCharacter.getX(),0);
        assertEquals("Initial y",30,floatingCharacter.getY(),0);
        assertEquals("offset x",0,floatingCharacter.getOffsetX(),0);
        assertEquals("offset y",0,floatingCharacter.getOffsetY(),0);
        assertEquals("leftKey",KeyCode.A,floatingCharacter.getLeftKey());
        assertEquals("RightKey",KeyCode.D,floatingCharacter.getRightKey());
        assertEquals("UpKey",KeyCode.W,floatingCharacter.getUpKey());

    }

}
