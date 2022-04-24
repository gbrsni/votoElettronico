package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VotazioneCategoricoPreferenzeController extends Controller{
	
	private Gestore gestore;
	private SessioneDiVoto sessione;
	private Elettore elettore;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaButton;

    @FXML
    private Label candidatiLabel;

    @FXML
    private VBox candidatiVbox;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label partitiLabel;

    @FXML
    private VBox partitiVbox;

    @FXML
    private Button votaButton;
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];
		nomeElettore.setText("Elettore: " + elettore.getNome() + " " + elettore.getCognome());
		nomeLabel.setText("Sessione: " + sessione.getNome());
		modVotoLabel.setText("Mod Voto: " + sessione.getModVoto());
		//init();
	}
    
    @FXML
    void pressAnnullaButton(ActionEvent event) {

    }

    @FXML
    void pressVotaButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert candidatiLabel != null : "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert partitiLabel != null : "fx:id=\"partitiLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";
        assert votaButton != null : "fx:id=\"votaButton\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";

    }

}
