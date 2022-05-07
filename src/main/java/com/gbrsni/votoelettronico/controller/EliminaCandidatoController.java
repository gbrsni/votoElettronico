package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EliminaCandidatoController extends Controller{
	
	private Gestore gestore;
	private Candidato candidato;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label eliminaCandidatoLabel;

    @FXML
    private Button noButton;

    @FXML
    private Button siButton;
    
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	try {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        candidato = (Candidato) dati[1];
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	eliminaCandidatoLabel.setText("Eliminare il candidato " + candidato.getNome() + " " + candidato.getCognome() + " ?");
    }	
    
    @FXML
    void pressNoButton(ActionEvent event) {
    	Stage stage = (Stage) noButton.getScene().getWindow();
        closeStage(stage);
        navigate("GestioneListeView",gestore);
    }

    @FXML
    void pressSiButton(ActionEvent event) {
    	 CandidatoDAOImpl candidatoDb = new CandidatoDAOImpl();
         candidatoDb.deleteCandidato(candidato);
         Stage stage = (Stage) siButton.getScene().getWindow();
         stage.close();
    }

    @FXML
    void initialize() {
        assert eliminaCandidatoLabel != null : "fx:id=\"eliminaCandidatoLabel\" was not injected: check your FXML file 'EliminaCandidatoButton.fxml'.";
        assert noButton != null : "fx:id=\"noButton\" was not injected: check your FXML file 'EliminaCandidatoButton.fxml'.";
        assert siButton != null : "fx:id=\"siButton\" was not injected: check your FXML file 'EliminaCandidatoButton.fxml'.";
    }

}
