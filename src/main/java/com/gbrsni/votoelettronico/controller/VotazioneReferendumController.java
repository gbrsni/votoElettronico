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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class VotazioneReferendumController extends Controller {
	
	private Elettore elettore;
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	
	 @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton favorevoleRadioButton;

    @FXML
    private Button annullaSelezioneButton;

    @FXML
    private RadioButton contratioRadioButton;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private Button votaButton;



    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];
		nomeElettore.setText("Elettore: " + elettore.getNome() + " " + elettore.getCognome());
		nomeLabel.setText("Sessione:" + sessione.getNome());
		modVotoLabel.setText("Mod. Voto:" + sessione.getModVoto());
	}
    
    @FXML
    void pressAnnullaSelezioneButton(ActionEvent event) {
    	radioGroup.getSelectedToggle().setSelected(false);
    }
    
    @FXML
    void pressVotaButton(ActionEvent event) {
    	Boolean scelta = null;
    	if(favorevoleRadioButton.isSelected()) scelta = true;
    	else if (contratioRadioButton.isSelected()) scelta = false;
    	Object[] parameter = new Object[] {elettore, sessione, gestore, scelta};
		newStage("Conferma Voto", "ConfermaVotazioneReferendumView", parameter);
    }

    @FXML
    void initialize() {
    	assert favorevoleRadioButton != null : "fx:id=\"FavorevoleRadioButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert annullaSelezioneButton != null : "fx:id=\"annullaSelezioneButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert contratioRadioButton != null : "fx:id=\"contratioRadioButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert radioGroup != null : "fx:id=\"radioGroup\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert votaButton != null : "fx:id=\"votaButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        
    }

}
