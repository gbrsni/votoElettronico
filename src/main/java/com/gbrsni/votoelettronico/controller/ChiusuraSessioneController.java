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
import javafx.stage.Stage;

public class ChiusuraSessioneController extends Controller{
	
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button noButton;

    @FXML
    private Button siButton;

    public void onNavigateFrom(Controller sender, Object parameter) {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        sessione = (SessioneDiVoto) dati[1];
    }
    
    @FXML
    void pressNoButton(ActionEvent event) {
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pressSiButton(ActionEvent event) {
    	sessione.setStatoSessione(StatoSessione.CONCLUSA);
    	Stage stage = (Stage) noButton.getScene().getWindow();
        closeStage(stage);
        SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
        sessioneDb.updateSessioneDiVoto(sessione);
        navigate("GestoreSessioniView", gestore);
    }

    @FXML
    void initialize() {
        assert noButton != null : "fx:id=\"noBottone\" was not injected: check your FXML file 'ChiusuraSessioneView.fxml'.";
        assert siButton != null : "fx:id=\"siBottone\" was not injected: check your FXML file 'ChiusuraSessioneView.fxml'.";
    }

}
