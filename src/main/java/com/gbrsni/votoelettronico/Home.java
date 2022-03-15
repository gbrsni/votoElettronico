package com.gbrsni.votoelettronico;

import java.io.IOException;
import java.sql.Connection;

import com.gbrsni.votoelettronico.controller.Controller;
import com.gbrsni.votoelettronico.data_access.*;
import com.gbrsni.votoelettronico.models.Gestore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Home extends Application {
	
	private static Scene scene ;//cambiare scena
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	
    	scene = new Scene(loadView("LoginView"));
    	primaryStage.setTitle("Voto Elettronico");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    
    public static Parent loadViewStart(String view) throws IOException {
    	FXMLLoader loader = new FXMLLoader(Home.class.getClassLoader().getResource(view + ".fxml"));
        return loader.load();
    }
    
	//gestisce il caricamento della view iniziale
	public static Parent loadView(String view) throws IOException {
        FXMLLoader loader = new FXMLLoader(Home.class.getClassLoader().getResource(view + ".fxml"));
        return loader.load();
    }
	
	//gestisce il caricamento della nuova view senza passaggio di parametri
    public static Parent loadView(Controller sender, String view) throws IOException {
        return loadView(sender, view, null);
    }
    
    //gestisceil caricamento della view con il passaggio di parametri
    public static Parent loadView(Controller sender, String view, Object parameter) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getClassLoader().getResource(view + ".fxml"));
        Parent parent = loader.load();
        Controller controller = loader.getController();
        controller.onNavigateFrom(sender,parameter);
        return parent;
    }

    public static void navigate(Controller sender, String view, Object parameter) throws IOException {
        Parent parent = loadView(sender, view, parameter);
        scene.setRoot(parent);
    }

    public static void navigate(Controller sender, String view) throws IOException {
        navigate(sender, view, null);
    }
    
    public static void main(String[] args) {
       launch(args);
    
    }
    
}