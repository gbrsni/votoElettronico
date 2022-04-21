package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
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

public class ConfermaVotazioneController extends Controller {

	private Elettore elettore;
	private SessioneDiVoto sessione;
	private Gestore gestore;
	private Map<Partito, Integer> partiti;
	private Map<Candidato, Integer> candidati;
	private boolean scelta;

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
		gestore = (Gestore) data[2];
		if (sessione.getModVoto().equals("REFERENDUM")) {
			scelta = (boolean) data[3];
		} else {
			partiti = (Map<Partito, Integer>) data[3];
			candidati = (Map<Candidato, Integer>) data[4];
		}
		setLabel();

	}

	public void setLabel() {
		String label = "";
		if (sessione.getModVoto().equals("REFERENDUM")) {
			if (scelta == true) {
				label = "favorevole";
			} else {
				label = "contrario";
			}
		} else {
			if (partiti.size() == 0) {
				label = "Scheda Bianca";
			} else {
				for (Map.Entry<Partito, Integer> entry : partiti.entrySet()) {
					label += "Partito: " + entry.getKey() + " " + entry.getValue() + "\n";
				}
				for (Map.Entry<Candidato, Integer> entry : candidati.entrySet()) {
					label += entry.getKey() + " " + entry.getValue() + "\n";
				}
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
		if(sessione.getModVoto().equals("REFERENDUM")) {}
		else {
			
		}
	}

	@FXML
	void initialize() {
		assert annullaBottone != null: "fx:id=\"annullaBottone\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
		assert confermaBottone != null: "fx:id=\"confermaBottone\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
		assert votoLabel != null: "fx:id=\"votoLabel\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
	}

}
