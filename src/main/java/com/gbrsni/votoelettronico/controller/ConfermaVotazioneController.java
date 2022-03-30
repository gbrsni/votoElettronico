package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfermaVotazioneController extends Controller{
	
	private Elettore elettore;
	private SessioneDiVoto sessione;
	private Gestore gestore;
	private Partito partito; 
	private List<Candidato> candidato;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaBottone;

    @FXML
    private Button confermaBottone;

    @FXML
    private Label votoLabel;

	@Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		 elettore = (Elettore) data[0];
		 sessione = (SessioneDiVoto) data[1];
		 partito = (Partito) data[2];
		 candidato = (List<Candidato>) data[3];
		try {
			 gestore = (Gestore) data[4];
		}catch(Exception e) {}
		
		setLabel();
		
	}

	public void setLabel() {
		String label ; 
		if(partito == null) {
			label = "Scheda Bianca";
		} else {
			label = "Partito: " + partito.getNome() + "\n";
			for (int i = 0; i < candidato.size();  i++) {
				label += candidato.get(i).getNome() + " " + candidato.get(i).getCognome() + "\n";
			}
		}
		votoLabel.setText(label);
	}
	
    @FXML
    void pressAnnullaButton(ActionEvent event) {
    	Stage stage = (Stage) annullaBottone.getScene().getWindow();
        closeStage(stage);
    }

    @FXML
    void pressConfermaButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert annullaBottone != null : "fx:id=\"annullaBottone\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert confermaBottone != null : "fx:id=\"confermaBottone\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert votoLabel != null : "fx:id=\"votoLabel\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";

    }


}
