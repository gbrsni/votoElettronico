package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EliminaPartitoController extends Controller{
	
	private Gestore gestore;
	private Partito partito;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label eliminaPartitoLabel;

    @FXML
    private Button noButton;

    @FXML
    private Button siButton;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	try {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        partito = (Partito) dati[1];
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	eliminaPartitoLabel.setText("Eliminare il partito " + partito.getNome() + " ?");
    }	
    
    
    @FXML
    void pressNoButton(ActionEvent event) {
    	Stage stage = (Stage) noButton.getScene().getWindow();
        closeStage(stage);
        navigate("GestioneListeView",gestore);
    }

    @FXML
    void pressSiButton(ActionEvent event) {
    	 PartitoDAOImpl partitoDb = new PartitoDAOImpl();
    	 partitoDb.deletePartito(partito);
         Stage stage = (Stage) siButton.getScene().getWindow();
         stage.close();
    }

    @FXML
    void initialize() {
        assert eliminaPartitoLabel != null : "fx:id=\"eliminaPartitoLabel\" was not injected: check your FXML file 'EliminaPartitoView.fxml'.";
        assert noButton != null : "fx:id=\"noButton\" was not injected: check your FXML file 'EliminaPartitoView.fxml'.";
        assert siButton != null : "fx:id=\"siButton\" was not injected: check your FXML file 'EliminaPartitoView.fxml'.";
    }

}
