package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VotazioneController extends Controller{
	
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	private Elettore elettore;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private Button votaBottone;
    
    //bisogna passare prima elettore, poi sessione, e infine gestore
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] dati = (Object[]) parameter;
    	   	elettore = (Elettore) dati[0];
	        sessione = (SessioneDiVoto) dati[1];
    	try {
	        gestore = (Gestore) dati[2];  
    	} catch(Exception e) {}
    		init();
    }
    
    public void init() {
    	nomeElettore.setText(elettore.getUsername());
    	nomeLabel.setText(sessione.getNome());
    	modVotoLabel.setText("Modalità di Voto: " + sessione.getModVoto());
    }
    
    @FXML
    void pressVotaButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        
    }

}
