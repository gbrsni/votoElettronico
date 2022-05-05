package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.VincitoriDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiReferendumDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RisultatiSessioneController extends Controller{
	
	private SessioneDiVoto sessione;
	private Gestore gestore;
	
	@FXML
    private Label astenutiLabel;

    @FXML
    private Button backButton;

    @FXML
    private VBox candidatiVbox;

    @FXML
    private Label dataLabel;

    @FXML
    private Label descrizioneLabel;

    @FXML
    private Label gestoreLabel;

    @FXML
    private Label modVittoriaLabel;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private VBox partitiVbox;

    @FXML
    private Label vincitoreLabel;

    @FXML
    private Label votiEspressiLabel;

    
    @Override
  	public void onNavigateFrom(Controller sender, Object parameter) {
      	Object[] data = (Object[]) parameter;
      	sessione = (SessioneDiVoto) data[0];
      	gestore = (Gestore) data[1];
      	if(gestore != null) gestoreLabel.setText(gestore.getUsername() + "    ");
      	init();
  	}
    
    @FXML
    void pressBackButton(ActionEvent event) {
    	if(gestore == null) {
    		navigate("UtenteSessioniScrutinateView");
    	}else {
    		navigate("GestoreSessioniView",gestore);
    	}
    }
    
    private void init() {
    	nomeLabel.setText("Nome Sessione: " + sessione.getNome());
    	descrizioneLabel.setText("Descrizione: " + sessione.getDescrizione());
    	dataLabel.setText("Data: " + sessione.getData().toString());
    	modVotoLabel.setText("Mod. Voto: " + sessione.getModVoto());
    	modVittoriaLabel.setText("Mod. Vittoria: " + sessione.getModVittoria());
    	
    	
    	//NUMERO ASTENUTI
    	VotiAstenutiDAOImpl votiAstenutiDb = new VotiAstenutiDAOImpl();
    	astenutiLabel.setText("Numero voti astenuti: " + votiAstenutiDb.getVotiAstenutiBySessione(sessione) + "");
    	
    	//VOTI ESPRESSI
    	votiEspressiLabel.setText("Numero voti espressi: " + sessione.getNvoti());
    	
    	//ELEZIONE
    	if(!(sessione instanceof com.gbrsni.votoelettronico.models.SessioneReferendum)) {
	    	VotiCandidatiDAOImpl votiCandidatiDb = new VotiCandidatiDAOImpl();
	    	VotiPartitiDAOImpl votiPartitiDb = new VotiPartitiDAOImpl();
	    	Map<Candidato,Integer> candidati = votiCandidatiDb.getVotiCandidatiBySessione(sessione);
	    	Map<Partito,Integer> partiti = votiPartitiDb.getVotiPartitiBySessione(sessione);
	    	
	    	for (Map.Entry<Partito, Integer> entry : partiti.entrySet()) {
	    	    HBox partitoHbox = new HBox();
	    	    Label partitoLabel = new Label();
	    	    partitoLabel.setFont(new Font(20));
	    	    partitoLabel.setText(entry.getKey() + "   voti:" + entry.getValue());
	    	    
	    	    partitoHbox.getChildren().add(partitoLabel);
	    	    partitiVbox.getChildren().add(partitoHbox);
	    	}
	    	
	    	for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
	    	    HBox candidatoHbox = new HBox();
	    	    Label candidatoLabel = new Label();
	    	    candidatoLabel.setFont(new Font(20));
	    	    candidatoLabel.setText(entry.getKey() + "   voti:" + entry.getValue());
	    	    
	    	    candidatoHbox.getChildren().add(candidatoLabel);
	    	    candidatiVbox.getChildren().add(candidatoHbox);
	    	}
	    	
	    	//VINCITORE
	    	VincitoriDAOImpl vincitoriDb = new VincitoriDAOImpl();
	    	Candidato vincitore = vincitoriDb.getVincitori(sessione);
	    	if(vincitore == null) {
	    		vincitoreLabel.setText("Vincitore non disponibile"); 
	    	} else {
	    		vincitoreLabel.setText("Vincitore: " + vincitore );
	    	}
	    	
	    	
    	} else { 
    		//REFERENDUM
    		VotiReferendumDAOImpl votiReferendumDb = new VotiReferendumDAOImpl();
    		
    		HBox favorevoleHbox = new HBox();
    		Label favorevoleLabel = new Label();
    		favorevoleLabel.setFont(new Font(20));
    		favorevoleLabel.setText("Favorevole: " + votiReferendumDb.getNVotiBySessioneOpzione(sessione, OpzioneReferendum.favorevole) + "");
    		favorevoleHbox.getChildren().add(favorevoleLabel);
    		partitiVbox.getChildren().add(favorevoleHbox);
    		
    		HBox contrarioHbox = new HBox();
    		Label contrarioLabel = new Label();
    		contrarioLabel.setFont(new Font(20));
    		contrarioLabel.setText("Contrario: "+ votiReferendumDb.getNVotiBySessioneOpzione(sessione, OpzioneReferendum.contrario) + "");
    		contrarioHbox.getChildren().add(contrarioLabel);
    		partitiVbox.getChildren().add(contrarioLabel);
    		
    		//VINCITORE REFERENDUM
    		String vincitore = votiReferendumDb.getVincitoreReferendum(sessione);
    		if (vincitore == null) {
    			vincitoreLabel.setText("Vincitore non disponibile");
    		}
    			vincitoreLabel.setText("Vincitore: " + vincitore);
    		}
    	}
    	
    
    @FXML
    void initialize() {
    	assert astenutiLabel != null : "fx:id=\"astenutiLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert dataLabel != null : "fx:id=\"dataLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert descrizioneLabel != null : "fx:id=\"descrizioneLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert gestoreLabel != null : "fx:id=\"gestoreLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert modVittoriaLabel != null : "fx:id=\"modVittoriaLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert vincitoreLabel != null : "fx:id=\"vincitoreLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert votiEspressiLabel != null : "fx:id=\"votiEspressiLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
    }
}
