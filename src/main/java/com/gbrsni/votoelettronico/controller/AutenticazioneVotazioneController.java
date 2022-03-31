package com.gbrsni.votoelettronico.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.data_access.ElettoreDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AutenticazioneVotazioneController extends Controller {

	private Gestore gestore;
	private SessioneDiVoto sessione;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button esciBottone;

	@FXML
	private Label erroreLabel;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private TextField tesseraElettoraleTextField;

	@FXML
	private Button votaBottone;

	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] dati = (Object[]) parameter;
		gestore = (Gestore) dati[0];
		sessione = (SessioneDiVoto) dati[1];
	}

	@FXML
	void pressEsciBottone(ActionEvent event) {

		Object[] parameter = new Object[] { gestore, sessione };
		newStage("Uscita Votazione", "UscitaVotazioneView", parameter);
	}

	@FXML
	void pressVotaButton(ActionEvent event) {
		erroreLabel.setVisible(false);
		ElettoreDAOImpl elettoreDb = new ElettoreDAOImpl();
		Elettore elettore = elettoreDb.getElettoreByTesseraElettorale(tesseraElettoraleTextField.getText());
		if (elettore == null) {
			erroreLabel.setVisible(true);
		} else {
			SaltedPassword password = elettoreDb.getPasswordElettoreByUsername(elettore.getUsername());
			if (password.checkPassword(passwordTextField.getText())) {
				System.out.println("Accesso eseguito dall'elettore " + elettore.getUsername());
				Object[] parameter = new Object[] { elettore, sessione, gestore };
				switch (sessione.getModVoto()) {
				case ORDINALE:
					navigate("VotazioneOrdinaleView", parameter);
					break;
				case CATEGORICO:
					navigate("VotazioneCategoricoView", parameter);
					break;
				case CATEGORICO_CON_PREFERENZE:
					navigate("VotazioneCategoricoView", parameter);
					break;
				case REFERENDUM:
					navigate("VotazioneReferendumView", parameter);
					break;
				}
			} else {
				erroreLabel.setVisible(true);
			}
		}
	}

	@FXML
	void initialize() {
		assert erroreLabel != null
				: "fx:id=\"erroreLabel\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert esciBottone != null
				: "fx:id=\"esciBottone\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert passwordTextField != null
				: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert tesseraElettoraleTextField != null
				: "fx:id=\"tesseraElettoraleTextField\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert votaBottone != null
				: "fx:id=\"votaBottone\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		erroreLabel.setText("Dati non corretti");
	}

}
