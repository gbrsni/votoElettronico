package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UtenteSessioniScrutinateController extends Controller{
	
	private List<SessioneDiVoto> sessioni;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button menuButton;

	@FXML
	private VBox sessioniVbox;
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {}
    
    @FXML
    void pressMenuButton(ActionEvent event) {
    	navigate("LoginView");
    }
    
    private void init() {
    	SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        sessioni = sessioniDb.getAllSessioneDiVotoByStato(StatoSessione.SCRUTINATA);
        
    	for (int i = 0; i < sessioni.size(); i++) {
    		HBox sessioniHbox = new HBox();
    		Label sessioniNomeLabel = new Label();
    		Label sessioniDescrizioneLabel = new Label();
    		Button sessioniButton = new Button();
    		Region sessioniRegion = new Region();
    		
    		sessioniNomeLabel.setText(sessioni.get(i).getNome());
    		sessioniNomeLabel.setFont(new Font(25));
    		sessioniDescrizioneLabel.setText("       Data: " + sessioni.get(i).getData());
    		sessioniDescrizioneLabel.setFont(new Font(15));
    		sessioniHbox.setHgrow(sessioniRegion, Priority.ALWAYS);
    		sessioniHbox.setAlignment(Pos.CENTER_LEFT);
    		sessioniButton.setText("Visualizza Risultati");
    		sessioniButton.setUserData(sessioni.get(i));
    		sessioniButton.setOnAction(pressVisualizzaRisultati);
    		sessioniButton.setFont(new Font(18));
    		
    		sessioniHbox.getChildren().addAll(sessioniNomeLabel,sessioniDescrizioneLabel,sessioniRegion,sessioniButton);
    		sessioniVbox.getChildren().add(sessioniHbox);
    	}
    }
    
	EventHandler<ActionEvent> pressVisualizzaRisultati = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				SessioneDiVoto sessione = (SessioneDiVoto)((Button)e.getSource()).getUserData();
				Object[] parameter = new Object[] {sessione, null};
				navigate("RisultatiSessioneView", parameter);
			}
		};

    
    @FXML
    void initialize() {
    	assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'UtenteSessioniScrutinateView.fxml'.";
        assert sessioniVbox != null : "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'UtenteSessioniScrutinateView.fxml'."; 
        init();
    }

}