package com.gbrsni.votoelettronico.controller;

import com.gbrsni.votoelettronico.Home;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
	
	public abstract void onNavigateFrom(Controller sender, Object parameter);
	
    public void navigate(String view, Object parameter) {
        try {
            Home.navigate(this, view, parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigate(String view) {
        navigate(view, null);
    }
    
    public Stage newStage(String title, String view, Object parameter) {
    	return Home.newStage(this,title,view,parameter);
    }
    
    public void closeStage(Stage stage) {
    	Home.closeStage(stage);
    }
}
