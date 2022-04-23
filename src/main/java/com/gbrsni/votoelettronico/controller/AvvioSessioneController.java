package com.gbrsni.votoelettronico.controller;


import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AvvioSessioneController extends Controller{
	
	private Gestore gestore ;
	private SessioneDiVoto sessione; 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button noButton;

    @FXML
    private Label avvioSessioneLabel;

    @FXML
    private Button siButton;


    public void onNavigateFrom(Controller sender, Object parameter) {
    	   
    	try {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        sessione = (SessioneDiVoto) dati[1];
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	avvioSessioneLabel.setText("Avviare fase di voto per la Sessione " + sessione.getNome() + " ?" );
    }	
    
    
    @FXML
    void pressNoButton(ActionEvent event) {
        Stage stage = (Stage) noButton.getScene().getWindow();
        closeStage(stage);
    }

    @FXML
    void pressSiButton(ActionEvent event) {
    	sessione.setStatoSessione(StatoSessione.IN_CORSO);
        SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
        sessioneDb.updateSessioneDiVoto(sessione);
        Stage stage = (Stage) siButton.getScene().getWindow();
        stage.close();
        Object[] parameter = new Object[] {gestore, sessione};
        navigate("SessioneApertaView", parameter);
    }
    
    @FXML
    void initialize() {
    	assert avvioSessioneLabel != null : "fx:id=\"avvioSessioneLabel\" was not injected: check your FXML file 'AvvioSessioneView.fxml'.";
        assert noButton != null : "fx:id=\"noButton\" was not injected: check your FXML file 'AvvioSessioneView.fxml'.";
        assert siButton != null : "fx:id=\"siButton\" was not injected: check your FXML file 'AvvioSessioneView.fxml'.";
    }

}
