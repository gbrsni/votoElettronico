package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class VotazioneReferendumController extends Controller {
	
	private Elettore elettore;
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox contrarioCheckBox;

    @FXML
    private CheckBox favorevoleCheckBox;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private Button votaBottone;
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		nomeElettore.setText(elettore.getNome());
		try {
			gestore = (Gestore) data[2];
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    void pressVotaButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert contrarioCheckBox != null : "fx:id=\"contrarioCheckBox\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert favorevoleCheckBox != null : "fx:id=\"favorevoleCheckBox\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        
    }

	

}
