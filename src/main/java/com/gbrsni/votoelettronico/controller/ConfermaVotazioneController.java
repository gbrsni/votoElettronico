package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotazioniPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfermaVotazioneController extends Controller{
	
	private Elettore elettore;
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	Map<Partito,Integer> partiti;
	Map<Candidato,Integer> candidati;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private VBox candidatiVbox;
    
    @FXML
    private VBox partitiVbox;
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];	
		partiti = (Map<Partito,Integer>) data[3];
		candidati = (Map<Candidato,Integer>) data[4];
		System.out.println(elettore.getNome() + " " + elettore.getCognome());
		init();
	}
    
    private void init() {
    	Label votoPartito = new Label();
    	Label votoCandidato = new Label();
    	votoPartito.setFont(new Font(20));
    	votoCandidato.setFont(new Font(20));
    	String testoPartito = "";
    	String testoCandidato = "";
    	
    	if(partiti.size() == 0) {
    		testoPartito = "Scheda Bianca";
    		partitiVbox.setAlignment(Pos.CENTER_LEFT);
    	} else {
    		testoPartito += "Partiti:\n";
    		for (Map.Entry<Partito, Integer> entry : partiti.entrySet()) {
		        testoPartito += entry.getKey() + "\n";
		    }
    		testoCandidato += "Candidati:\n";
    		if(candidati.size()!= 0) {
    			for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
    		        testoCandidato += entry.getKey() + "\n";
    		    }
    		}
    	}
    	votoPartito.setText(testoPartito);
    	votoCandidato.setText(testoCandidato);
    	partitiVbox.getChildren().add(votoPartito);
    	candidatiVbox.getChildren().add(votoCandidato);
    }
    
    @FXML
    void pressAnnullaButton(ActionEvent event) {
    	Stage stage = (Stage) annullaButton.getScene().getWindow();
		closeStage(stage);
    }

    @FXML
    void pressConfermaButton(ActionEvent event) {
    	
    	VotazioniPartitiDAOImpl votazioniPartitiDb = new VotazioniPartitiDAOImpl();
    	VotazioniCandidatiDAOImpl votazioniCandidatiDb = new VotazioniCandidatiDAOImpl();
    	VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
    	
    	if (partiti.size() == 0 && candidati.size() == 0 ) {
    		//IMPLEMENTARE SALVATAGGIO SCHEDA BIANCA///////////////////////////////////////////////////////////////////////////
    	} else {
    		for (Map.Entry<Partito, Integer> entry : partiti.entrySet()) {
		        votazioniPartitiDb.addVotazioniPartitiBySessione(sessione, entry.getKey(), entry.getValue());
		    }
    		for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
		        votazioniCandidatiDb.addVotazioniCandidatiBySessione(sessione, entry.getKey(), entry.getValue());
		    }
    	}   		
    	votiEspressiDb.addVotoEspresso(sessione, elettore);
    	
    	Stage stage = (Stage) confermaButton.getScene().getWindow();
        stage.close();
        Object[] parameter = new Object[] {elettore, sessione, gestore};
        navigate("VotoRegistratoView",parameter);
    }

    @FXML
    void initialize() {
    	assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert confermaButton != null : "fx:id=\"confermaButton\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
    }

}

