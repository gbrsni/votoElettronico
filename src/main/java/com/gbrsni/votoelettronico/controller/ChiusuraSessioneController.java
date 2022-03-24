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
    private Button noBottone;

    @FXML
    private Button siBottone;

    public void onNavigateFrom(Controller sender, Object parameter) {
    	
    	try {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        sessione = (SessioneDiVoto) dati[1];
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    }
    
    @FXML
    void pressNoButton(ActionEvent event) {
        Stage stage = (Stage) noBottone.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pressSiButton(ActionEvent event) {
    	sessione.setStatoSessione(StatoSessione.CONCLUSA);
    	Stage stage = (Stage) noBottone.getScene().getWindow();
        stage.close();
        SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
        sessioneDb.updateSessioneDiVoto(sessione);
        navigate("SessioneApertaView", gestore);
    }

    @FXML
    void initialize() {
        assert noBottone != null : "fx:id=\"noBottone\" was not injected: check your FXML file 'ChiusuraSessioneView.fxml'.";
        assert siBottone != null : "fx:id=\"siBottone\" was not injected: check your FXML file 'ChiusuraSessioneView.fxml'.";

    }

}
