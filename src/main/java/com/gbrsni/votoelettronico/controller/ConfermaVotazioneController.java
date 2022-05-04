package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
		
		for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
		    System.out.println("Candidato " + entry.getKey() + "/" + entry.getValue());
		}
		init();
	}
    
    private void init() {
    	if(partiti.size() == 0) {
    		HBox schedaBiancaHbox = new HBox();
    		Label schedaBiancaLabel = new Label("Scheda Bianca");
    		schedaBiancaHbox.getChildren().add(schedaBiancaLabel);
    		partitiVbox.getChildren().add(schedaBiancaHbox);
    		partitiVbox.setAlignment(Pos.CENTER_LEFT);
    	} else {	
    		LinkedHashMap<Partito, Integer> reversePartiti = new LinkedHashMap<>();
    		partiti.entrySet()
    		  .stream()
    		  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
    		  .forEachOrdered(x -> reversePartiti.put(x.getKey(), x.getValue()));
    		
    		HBox partitiHbox = new HBox();
    		Label partitiLabel = new Label("Partiti:");
    		partitiLabel.setFont(new Font(20));
    		partitiHbox.getChildren().add(partitiLabel);
    		partitiVbox.getChildren().add(partitiHbox);
    		for (Map.Entry<Partito, Integer> entry : reversePartiti.entrySet()) {
    			HBox partitoHbox = new HBox();
        		Label partitoLabel = new Label(entry.getKey().toString());
        		partitoLabel.setFont(new Font(20));
        		partitoHbox.getChildren().add(partitoLabel);
        		partitiVbox.getChildren().add(partitoHbox);
		    }
    		    			
    		LinkedHashMap<Candidato, Integer> reverseCandidati = new LinkedHashMap<>();
    		candidati.entrySet()
    		  .stream()
    		  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
    		  .forEachOrdered(x -> reverseCandidati.put(x.getKey(), x.getValue()));
    		
    		HBox candidatiHbox = new HBox();
    		Label candidatiLabel = new Label("Candidati:");
    		candidatiLabel.setFont(new Font(20));
    		candidatiHbox.getChildren().add(candidatiLabel);
    		candidatiVbox.getChildren().add(candidatiHbox);
    		if(candidati.size()!= 0) {
    			for (Map.Entry<Candidato, Integer> entry : reverseCandidati.entrySet()) {
    				HBox candidatoHbox = new HBox();
            		Label candidatoLabel = new Label(entry.getKey().toString());
            		candidatoLabel.setFont(new Font(20));
            		candidatoHbox.getChildren().add(candidatoLabel);
            		candidatiVbox.getChildren().add(candidatoHbox);
    		    }
    		}
    	}
    }
    
    @FXML
    void pressAnnullaButton(ActionEvent event) {
    	Stage stage = (Stage) annullaButton.getScene().getWindow();
		closeStage(stage);
    }

    @FXML
    void pressConfermaButton(ActionEvent event) {
    	
    	VotiPartitiDAOImpl votiPartitiDb = new VotiPartitiDAOImpl();
    	VotiCandidatiDAOImpl votiCandidatiDb = new VotiCandidatiDAOImpl();
    	VotiAstenutiDAOImpl votiAstenutiDb = new VotiAstenutiDAOImpl();
    	VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
    	
    	if (partiti.size() == 0) {
    		votiAstenutiDb.increaseVotiAstenutiBySessione(sessione);
    	} else {
    			for (Map.Entry<Partito, Integer> entry : partiti.entrySet()) {
    		        votiPartitiDb.increaseVotiPartitiBySessione(sessione, entry.getKey(), entry.getValue());
    		    }    	
    			for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
    				votiCandidatiDb.increaseVotiCandidatiBySessione(sessione, entry.getKey(), entry.getValue());
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

