package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VotoRegistratoController extends Controller{
	
	private Elettore elettore;
	private Gestore gestore;
	private SessioneDiVoto sessione;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button continuaButton;

    @FXML
    private Label messaggioLabel;
    
    @Override
   	public void onNavigateFrom(Controller sender, Object parameter) {
       	Object[] data = (Object[]) parameter;
   		elettore = (Elettore) data[0];
   		sessione = (SessioneDiVoto) data[1];
   		gestore = (Gestore) data[2];	
   		messaggioLabel.setText("Il voto dell'elettore " + elettore.getNome() + " " + elettore.getCognome() + " per la sessione " + sessione.getNome() + " è stato registrato");
   	}
       
    @FXML
    void pressContinuaButton(ActionEvent event) {
    	if (gestore == null) {
    		navigate("ElettoreSessioniView",elettore);
    	} else {
    		navigate("AutenticazioneVotazioneView",gestore);
    	}
    }

    @FXML
    void initialize() {
        assert continuaButton != null : "fx:id=\"continuaButton\" was not injected: check your FXML file 'VotoRegistratoView.fxml'.";
        assert messaggioLabel != null : "fx:id=\"messaggioLabel\" was not injected: check your FXML file 'VotoRegistratoView.fxml'.";
    }

}
