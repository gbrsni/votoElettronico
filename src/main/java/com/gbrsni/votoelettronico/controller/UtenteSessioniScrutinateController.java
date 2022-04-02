package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class UtenteSessioniScrutinateController extends Controller{
	
	private List<SessioneDiVoto> sessioni;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button menuBottone;

    @FXML
    private VBox sessioniVbox;
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {}
    
    @FXML
    void pressMenuBottone(ActionEvent event) {
    	navigate("LoginView");
    }
    
    private void init() {
    	
    	for (int i = 0; i < sessioni.size(); i++) {
    		HBox sessioniHbox = new HBox();
    		Label sessioniLabel = new Label();
    		Button sessioniButton = new Button();
    		Region sessioniRegion = new Region();
    		
    		sessioniLabel.setText(sessioni.get(i).getNome());
    		sessioniLabel.setFont(new Font(25));
    		sessioniHbox.setHgrow(sessioniRegion, Priority.ALWAYS);
    		sessioniButton.setText("Visualizza Risultati");
    		sessioniButton.setFont(new Font(15));
    		
    		sessioniHbox.getChildren().add(sessioniLabel);
    		sessioniHbox.getChildren().add(sessioniRegion);
    		sessioniHbox.getChildren().add(sessioniButton);
    		sessioniVbox.getChildren().add(sessioniHbox);
    	}
    }
    
    @FXML
    void initialize() {
        assert menuBottone != null : "fx:id=\"menuBottone\" was not injected: check your FXML file 'UtenteSessioniScrutinateView.fxml'.";
        assert sessioniVbox != null : "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'UtenteSessioniScrutinateView.fxml'.";
        
        SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        sessioni = sessioniDb.getAllSessioneDiVotoByStato(StatoSessione.SCRUTINATA); 
        init();
    }

}
