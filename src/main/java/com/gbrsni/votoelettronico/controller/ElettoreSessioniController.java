package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ElettoreSessioniController extends ControllerElettore {

	private List<SessioneDiVoto> sessioni;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button logoutButton;

	@FXML
	private Label nomeElettore;

	@FXML
	private VBox sessioniVbox;

	@Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		elettore = (Elettore) parameter;
		nomeElettore.setText(elettore.getNome());
		init();
	}

	@FXML
	void pressLogoutButton(ActionEvent event) {
		Logging.infoMessage(this.getClass(), "Eseguito il logout dall'elettore " + elettore);
		elettore = null;
		navigate("LoginView");
	}

	//esprimi voto
	private EventHandler<ActionEvent> pressVotaButton = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			SessioneDiVoto sessione = (SessioneDiVoto) ((Button) e.getSource()).getUserData();
			Object[] parameter = new Object[] { elettore, sessione , null};
			switch (sessione.getModVoto()) {
			case ORDINALE:
				navigate("VotazioneOrdinaleView", parameter);
				break;
			case CATEGORICO:
				navigate("VotazioneCategoricoView", parameter);
				break;
			case CATEGORICO_CON_PREFERENZE:
				navigate("VotazioneCategoricoPreferenzeView", parameter);
				break;
			case REFERENDUM:
				navigate("VotazioneReferendumView", parameter);
				break;
			}
		}
	};
	
	//Visualizza Risultati Sessione Scrutinata
		private EventHandler<ActionEvent> pressVisualizzaRisultati = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				SessioneDiVoto sessione = (SessioneDiVoto) ((Button) e.getSource()).getUserData();
				Object[] parameter = new Object[] {sessione, elettore};
				navigate("RisultatiSessioneView", parameter);
			}
		};
	
	private void init() {
		SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
		sessioni = sessioniDb.getAllSessioneDiVotoByStato(StatoSessione.IN_CORSO);
		VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
		List<SessioneDiVoto> votiEspressi = votiEspressiDb.allExistsVotoEspressoByElettore(elettore);
		
		for (int i = 0; i < sessioni.size(); i++) {
			if (!contains(votiEspressi,sessioni.get(i))) {
				HBox sessioniHbox = new HBox();
				Label sessioniLabelNome = new Label();
				Label sessioniLabelDati = new Label();
				Region region1 = new Region();
				Button votoBottone = new Button("Vota");
				
				sessioniHbox.setAlignment(Pos.CENTER_LEFT);
				sessioniLabelNome.setFont(new Font(25));
				sessioniLabelNome.setText(sessioni.get(i).getNome());
				sessioniLabelDati.setFont(new Font(15));
				sessioniLabelDati.setText("      Data: " + sessioni.get(i).getData() + " modVoto: " + sessioni.get(i).getModVoto());
				votoBottone.setFont(new Font(18));
				sessioniHbox.setHgrow(region1, Priority.ALWAYS);
				
				votoBottone.setUserData(sessioni.get(i));
				votoBottone.setOnAction(pressVotaButton);
				
				sessioniHbox.getChildren().addAll(sessioniLabelNome, sessioniLabelDati,region1,votoBottone);
				sessioniVbox.getChildren().add(sessioniHbox);
			}
		}
		
		sessioni = sessioniDb.getAllSessioneDiVotoByStato(StatoSessione.SCRUTINATA);
		for (int i = 0; i < sessioni.size(); i++) {
				HBox sessioniHbox = new HBox();
				Label sessioniLabelNome = new Label();
				Label sessioniLabelDati = new Label();
				Region region1 = new Region();
				Button risultatiBottone = new Button("Visualizza Risultati");
				
				sessioniHbox.setAlignment(Pos.CENTER_LEFT);
				sessioniLabelNome.setFont(new Font(25));
				sessioniLabelNome.setText(sessioni.get(i).getNome());
				sessioniLabelDati.setFont(new Font(15));
				sessioniLabelDati.setText("      Data: " + sessioni.get(i).getData() + " modVoto: " + sessioni.get(i).getModVoto());
				risultatiBottone.setFont(new Font(18));
				sessioniHbox.setHgrow(region1, Priority.ALWAYS);
				
				risultatiBottone.setUserData(sessioni.get(i));
				risultatiBottone.setOnAction(pressVisualizzaRisultati);
				
				sessioniHbox.getChildren().addAll(sessioniLabelNome, sessioniLabelDati,region1,risultatiBottone);
				sessioniVbox.getChildren().add(sessioniHbox);
		}
	}
	
	private boolean contains(List<SessioneDiVoto> votiEspressi, SessioneDiVoto sessione) {
		for(int i = 0; i < votiEspressi.size(); i++) {
			if(votiEspressi.get(i).getId() == sessione.getId()) return true;
		}
		return false;
	}
	
	@FXML
	void initialize() {
		assert logoutButton != null: "fx:id=\"logoutBottone\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
		assert nomeElettore != null: "fx:id=\"nomeElettore\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
		assert sessioniVbox != null : "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'ElettoreSessioniView.fxml'.";
	}

}
