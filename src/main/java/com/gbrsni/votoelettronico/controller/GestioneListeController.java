package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GestioneListeController extends Controller {

	private Gestore gestore;
	private List<Partito> partiti;
	private Map<Partito, List<Candidato>> candidati;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label candidatiLabel;

	@FXML
	private VBox candidatiVbox;

	@FXML
	private Button candidatoBottone;

	@FXML
	private Button cercaCandidatoBottone;

	@FXML
	private TextField cercaCandidatoTextField;

	@FXML
	private Button cercaPartitoBottone;

	@FXML
	private TextField cercaPartitoTextField;

	@FXML
	private Button logoutBottone;

	@FXML
	private Button menuButton;

	@FXML
	private Label nomeGestore;

	@FXML
	private VBox partitiVbox;

	@FXML
	private Button partitoBottone;

	public void onNavigateFrom(Controller sender, Object parameter) {
		this.gestore = (Gestore) parameter;
		nomeGestore.setText(gestore.getUsername());
	}

	@FXML
	void handleAggiungiCandidato(ActionEvent event) {
		navigate("AggiuntaCandidatoView", gestore);
	}

	@FXML
	void handleAggiungiPartito(ActionEvent event) {
		navigate("AggiuntaPartitoView", gestore);
	}

	@FXML
	void handleLogout(ActionEvent event) {

	}

	@FXML
	void handleMenu(ActionEvent event) {
		navigate("DashBoardView", gestore);
	}

	@FXML
	void pressCercaCandidatoButton(ActionEvent event) {

	}

	@FXML
	void pressCercaPartitoButton(ActionEvent event) {

	}

	private void initPartito() {

		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			Label partitiLabel = new Label();
			partitiLabel.setFont(new Font(15));
			Button partitiBottone = new Button("Modifica");
			Button partitiElencoBottone = new Button(">>");
			partitiElencoBottone.setOnAction(selezionePartito);
			partitiBottone.setUserData(partiti.get(i));
			partitiElencoBottone.setUserData(partiti.get(i));
			partitiLabel.setText(partiti.get(i).getNome());
			Region region1 = new Region();
			partitiHbox.setHgrow(region1, Priority.ALWAYS);
			partitiHbox.getChildren().add(partitiLabel);
			partitiHbox.getChildren().add(region1);
			partitiHbox.getChildren().add(partitiBottone);
			partitiHbox.getChildren().add(partitiElencoBottone);
			partitiVbox.getChildren().add(partitiHbox);
		}
	}

	private void initCandidato(List<Candidato> c) {
		candidatiVbox.getChildren().clear();

		for (int i = 0; i < c.size(); i++) {
			HBox candidatiHbox = new HBox();
			Label candidatiLabel = new Label();
			candidatiLabel.setFont(new Font(15));
			Button candidatiBottone = new Button("Modifica");
			candidatiBottone.setUserData(c.get(i));

			candidatiLabel
					.setText(c.get(i).getNome() + " " + c.get(i).getCognome() + "  partito:" + c.get(i).getPartito());
			Region region1 = new Region();
			candidatiHbox.setHgrow(region1, Priority.ALWAYS);
			candidatiHbox.getChildren().add(candidatiLabel);
			candidatiHbox.getChildren().add(region1);
			candidatiHbox.getChildren().add(candidatiBottone);
			candidatiVbox.getChildren().add(candidatiHbox);
		}
	}

	private EventHandler<ActionEvent> selezionePartito = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			Partito partito = (Partito) ((Button) e.getSource()).getUserData();

			candidatiLabel.setText("Candidati partito " + partito.getNome());
			List<Candidato> c = new ArrayList<>();
			List<Candidato> list = candidati.get(partito);
			if (list != null) {
				c.addAll(list);
			}
			initCandidato(c);
		}
	};

	@FXML
	void initialize() {
		assert candidatiLabel != null
				: "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert candidatiVbox != null
				: "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert candidatoBottone != null
				: "fx:id=\"candidatoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert cercaCandidatoBottone != null
				: "fx:id=\"cercaCandidatoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert cercaCandidatoTextField != null
				: "fx:id=\"cercaCandidatoTextField\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert cercaPartitoBottone != null
				: "fx:id=\"cercaPartitoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert cercaPartitoTextField != null
				: "fx:id=\"cercaPartitoTextField\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert logoutBottone != null
				: "fx:id=\"logoutBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert menuButton != null
				: "fx:id=\"menuButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert nomeGestore != null
				: "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert partitiVbox != null
				: "fx:id=\"partitiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
		assert partitoBottone != null
				: "fx:id=\"partitoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";

		PartitoDAOImpl partitoDb = new PartitoDAOImpl();
		partiti = partitoDb.getAllPartito();
		CandidatoDAOImpl candidatoDb = new CandidatoDAOImpl();
		candidati = candidatoDb.getAllCandidato().stream()
				.collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));
		candidatiLabel.setText("Tutti i candidati");
		List<List<Candidato>> lista = new ArrayList<>(candidati.values());
		List<Candidato> c = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			c.addAll(lista.get(i));
		}
		initPartito();
		initCandidato(c);
	}

}
