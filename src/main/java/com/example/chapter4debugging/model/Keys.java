package com.example.chapter4debugging.model;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.HashMap;

public class Keys  {
    private HashMap<KeyCode,Boolean> keys;
    public Keys(){
        keys = new HashMap<>();

    }
    public void add(KeyCode key)
    { keys.put(key,true);}
    public void remove(KeyCode key)
    { keys.put(key,false);}

    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }


}
