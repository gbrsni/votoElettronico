package com.gbrsni.votoelettronico.controller;

import java.net.URL;
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

public class EliminaSessioneController extends Controller{
	
	private Gestore gestore ;
	private SessioneDiVoto sessione; 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label eliminaSessioneLabel;

    @FXML
    private Button noButton;

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
    	eliminaSessioneLabel.setText("Eliminare la sessione " + sessione.getNome() + " ?" );
    }	
    
    @FXML
    void pressNoButton(ActionEvent event) {
    	Stage stage = (Stage) noButton.getScene().getWindow();
        closeStage(stage);
    }

    @FXML
    void pressSiButton(ActionEvent event) {
        SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
        sessioneDb.deleteSessioneDiVoto(sessione);
        Stage stage = (Stage) siButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        assert eliminaSessioneLabel != null : "fx:id=\"eliminaSessioneLabel\" was not injected: check your FXML file 'EliminaSessioneView.fxml'.";
        assert noButton != null : "fx:id=\"noButton\" was not injected: check your FXML file 'EliminaSessioneView.fxml'.";
        assert siButton != null : "fx:id=\"siButton\" was not injected: check your FXML file 'EliminaSessioneView.fxml'.";
    }

}
