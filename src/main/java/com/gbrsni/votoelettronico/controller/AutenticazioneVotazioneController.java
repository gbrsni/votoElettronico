package com.gbrsni.votoelettronico.controller;


import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.ElettoreDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AutenticazioneVotazioneController extends ControllerGestore {

	private SessioneDiVoto sessione;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button esciButton;

	@FXML
	private Label erroreLabel;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private TextField tesseraElettoraleTextField;

	@FXML
	private Button votaButtone;

	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] dati = (Object[]) parameter;
		gestore = (Gestore) dati[0];
		sessione = (SessioneDiVoto) dati[1];
		erroreLabel.setText("Dati non corretti");
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
		Elettore elettore = elettoreDb.getElettoreByTesseraElettorale(tesseraElettoraleTextField.getText().trim());
		if (elettore == null) {
			erroreLabel.setText("Dati non corretti");
			erroreLabel.setVisible(true);
		} else {
			VotiEspressiDAOImpl votoEspressoDb = new VotiEspressiDAOImpl();
			boolean existVoto = votoEspressoDb.existsVotoEspresso(sessione, elettore);
			if (!existVoto) {
				SaltedPassword password = elettoreDb.getPasswordElettoreByUsername(elettore.getUsername());
				if (password.checkPassword(passwordTextField.getText().trim())) {
					Logging.infoMessage(this.getClass(), "Votazione avviata per l'elettore " + elettore);
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
					erroreLabel.setText("Dati non corretti");
					erroreLabel.setVisible(true);
					Logging.warnMessage(this.getClass(), "Dati di login non corretti per l'elettore " + elettore);
				}
			} else {
				erroreLabel.setText("hai gi? espresso il tuo voto in questa sessione");
				erroreLabel.setVisible(true);
			}
		}
	}

	@FXML
	void initialize() {
		assert erroreLabel != null: "fx:id=\"erroreLabel\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert esciButton != null: "fx:id=\"esciBottone\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert passwordTextField != null: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert tesseraElettoraleTextField != null: "fx:id=\"tesseraElettoraleTextField\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
		assert votaButtone != null: "fx:id=\"votaBottone\" was not injected: check your FXML file 'AutenticazioneVotazioneView.fxml'.";
	}
}
