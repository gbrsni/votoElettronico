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
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Home extends Application {
	
	private static Scene scene ;
	private static Stage primaryStage;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	this.primaryStage = primaryStage;
    	scene = new Scene(loadView("LoginView"));
    	primaryStage.setTitle("Voto Elettronico");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    
   
	//caricamento della view iniziale
	public static Parent loadView(String view) throws IOException {
        FXMLLoader loader = new FXMLLoader(Home.class.getClassLoader().getResource(view + ".fxml"));
        return loader.load();
    }
	
	//caricamento della view senza passaggio di parametri
    public static Parent loadView(Controller sender, String view) throws IOException {
        return loadView(sender, view, null);
    }
    
    //caricamento della view con passaggio di parametri
    public static Parent loadView(Controller sender, String view, Object parameter) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getClassLoader().getResource(view + ".fxml"));
        Parent parent = loader.load();
        Controller controller = loader.getController();
        controller.onNavigateFrom(sender,parameter);
        return parent;
    }
    
    //passaggio alla nuova view con passaggio di parametri
    public static void navigate(Controller sender, String view, Object parameter) throws IOException {
        Parent parent = loadView(sender, view, parameter);
        scene.setRoot(parent);
    }
    
    //passaggio alla nuova view con passaggio di parametri
    public static void navigate(Controller sender, String view) throws IOException {
        navigate(sender, view, null);
    }
    
    //blocca lo stage principale
    public static void blockPrimaryStage(Stage stage) {
    	stage.initModality(Modality.WINDOW_MODAL);
    	stage.initOwner(primaryStage);
    }
    
    //crea un nuovo stage
    public static void newStage(Controller sender,String title, String view, Object parameter) {
	    try {
	   	        	
	        Stage stage = new Stage();
	        stage.setTitle(title);
	        stage.setScene(new Scene(loadView(sender,view, parameter)));
	        stage.setResizable(false);
	        blockPrimaryStage(stage);
	        stage.show();
	    }
	    catch (IOException e1) {
	        e1.printStackTrace();
	    }
    }
    
    //chiude lo stage passato come argomento
    public static void closeStage(Stage stage) {
    	stage.close();
    }
    
    
    public static void main(String[] args) {
       launch(args);
    
    }
    
}