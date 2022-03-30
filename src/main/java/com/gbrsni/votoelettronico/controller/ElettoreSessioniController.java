package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ElettoreSessioniController extends Controller {

	private Elettore elettore;
	private List<SessioneDiVoto> sessioni;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button logoutBottone;

	@FXML
	private Label nomeElettore;

	@FXML
	private VBox sessioniVbox;

	@Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		elettore = (Elettore) parameter;
		nomeElettore.setText(elettore.getNome());
	}

	@FXML
	void pressLogoutButton(ActionEvent event) {
		elettore = null;
		navigate("LoginView");
	}

	// Visualizza Risultati sessione Scrutinata
	private EventHandler<ActionEvent> votazione = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			SessioneDiVoto sessione = (SessioneDiVoto) ((Button) e.getSource()).getUserData();
			System.out.println("Id Sessione di Voto:" + sessione.getId());
			Object[] parameter = new Object[] { elettore, sessione };
			switch (sessione.getModVoto()) {
				case ORDINALE:
					//navigate("VotazioneOrdinaleView", parameter);
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
		}
	};

	private void init() {
		sessioniVbox.setPrefWidth(850);
		for (int i = 0; i < sessioni.size(); i++) {
			VBox sessioniVboxInterna = new VBox();
			HBox sessioniHboxNome = new HBox();
			HBox sessioniHboxDati = new HBox();
			Label sessioniLabelNome = new Label();
			Label sessioniLabelDati = new Label();
			sessioniLabelNome.setFont(new Font(25));
			sessioniLabelNome.setText(sessioni.get(i).getNome());
			sessioniLabelDati.setFont(new Font(15));
			sessioniLabelDati.setText(sessioni.get(i).getDescrizione() + " Data: " + sessioni.get(i).getData()
					+ " modVoto: " + sessioni.get(i).getModVoto());
			Region region1 = new Region();
			sessioniHboxNome.setHgrow(region1, Priority.ALWAYS);
			Button votoBottone = new Button("Vota");
			votoBottone.setUserData(sessioni.get(i));
			votoBottone.setOnAction(votazione);

			sessioniHboxNome.getChildren().add(sessioniLabelNome);
			sessioniHboxNome.getChildren().add(region1);
			sessioniHboxNome.getChildren().add(votoBottone);
			sessioniHboxDati.getChildren().add(sessioniLabelDati);
			sessioniVboxInterna.getChildren().add(sessioniHboxNome);
			sessioniVboxInterna.getChildren().add(sessioniHboxDati);
			sessioniVbox.getChildren().add(sessioniVboxInterna);
		}

		// ORA BISOGNA INSERIRE IL CONTROLLO PER FAR COMPARIRE IL TASTO PER VOTARE SOLO
		// SE L'ELETTORE NON HA GIà VOTATO

	}

	@FXML
	void initialize() {
		assert logoutBottone != null
				: "fx:id=\"logoutBottone\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
		assert nomeElettore != null
				: "fx:id=\"nomeElettore\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
		assert sessioniVbox != null
				: "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
		SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
		sessioni = sessioniDb.getAllSessioneDiVotoByStato(StatoSessione.IN_CORSO);
		init();
	}

}
