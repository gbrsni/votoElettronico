package com.gbrsni.votoelettronico.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SessioneApertaController extends Controller {
	
	private Gestore gestore;
	private SessioneDiVoto sessione;
	
	  @FXML
	    private Button backButton;

	    @FXML
	    private Button chiudiSessioneButton;

	    @FXML
	    private Label dataLabel;

	    @FXML
	    private Label descrizioneLabel;

	    @FXML
	    private Label modVittoriaLabel;

	    @FXML
	    private Label modVotoLabel;

	    @FXML
	    private Label nomeGestore;

	    @FXML
	    private Label nomeLabel;

	    @FXML
	    private Button votazioneButton;

    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] dati = (Object[]) parameter;
        gestore = (Gestore) dati[0];
        nomeGestore.setText(gestore.getNome());
        sessione = (SessioneDiVoto) dati[1];
        init();
    
    }
    
    private void init() {
        nomeLabel.setText("Nome: " + sessione.getNome());
        descrizioneLabel.setText("Descrizione: " + sessione.getDescrizione());
        dataLabel.setText("Data: " + sessione.getData().toString());
        modVotoLabel.setText("mod. Voto: " +sessione.getModVoto().toString());
        modVittoriaLabel.setText("mod. Vittoria: " + sessione.getModVittoria().toString());
       
    }
    
    @FXML
    void pressChiudiSessioneButton(ActionEvent event) {
    	 Object[] parameter = new Object[] {gestore,sessione}; 
    	 newStage("Chiusura Sessione di Voto", "ChiusuraSessioneView", parameter);
    }

    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestoreSessioniView",gestore);
    }

    @FXML
    void pressVotazioneButton(ActionEvent event) {
        Object[] parameter = new Object[] {gestore,sessione};  
    	navigate("AutenticazioneVotazioneView", parameter);
    }

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert chiudiSessioneButton != null : "fx:id=\"chiudiSessioneBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert dataLabel != null : "fx:id=\"dataLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert descrizioneLabel != null : "fx:id=\"descrizioneLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert modVittoriaLabel != null : "fx:id=\"modVittoriaLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert votazioneButton != null : "fx:id=\"votazioneBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
       
    }

}
