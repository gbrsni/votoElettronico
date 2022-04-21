package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private List<Candidato> candidati;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label candidatiLabel;

	@FXML
	private VBox candidatiVbox;

	@FXML
	private Button candidatoButton;

	@FXML
	private Button cercaButton;

	@FXML
	private ComboBox<String> cercaComboBox;

	@FXML
	private TextField cercaTextField;

	@FXML
	private Button logoutButton;

	@FXML
	private Button menuButton;

	@FXML
	private Label nomeGestore;

	@FXML
	private VBox partitiVbox;

	@FXML
	private Button partitoButton;

	public void onNavigateFrom(Controller sender, Object parameter) {
		this.gestore = (Gestore) parameter;
		nomeGestore.setText(gestore.getUsername());
	}

	@FXML
	void pressAggiungiCandidatoButton(ActionEvent event) {
		navigate("AggiuntaCandidatoView", gestore);
	}

	@FXML
	void pressAggiungiPartitoButton(ActionEvent event) {
		navigate("AggiuntaPartitoView", gestore);
	}

	@FXML
	void pressLogoutButton(ActionEvent event) {
		gestore = null;
		navigate("LoginView");
	}

	@FXML
	void pressMenuButton(ActionEvent event) {
		navigate("DashBoardView", gestore);
	}

	@FXML
	void pressCercaButton(ActionEvent event) {
		
		
		String cerca = cercaTextField.getText().trim().toLowerCase();
		if(cerca.equals("")) {
			candidatiVbox.getChildren().clear();
			initPartito(partiti);}
		else {
			if(cercaComboBox.getValue().equals("Partito")){
				List<Partito> ricercaPartiti = new ArrayList<>();
				for (int i = 0; i < partiti.size(); i++) {
					if (partiti.get(i).getNome().toLowerCase().contains(cerca)) {
						ricercaPartiti.add(partiti.get(i));
					}
					candidatiVbox.getChildren().clear();
					initPartito(ricercaPartiti);
				}
			} else {
				if (candidati == null) {
					CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
					candidati = candidatiDb.getAllCandidato();
				}
				List<Candidato> ricercaCandidati = new ArrayList<>();
				for (int i = 0; i < candidati.size(); i++) {
					if((candidati.get(i).getNome().toLowerCase().contains(cerca)) || (candidati.get(i).getCognome().toLowerCase().contains(cerca))) { ricercaCandidati.add(candidati.get(i));}
				}
				candidatiLabel.setText("Elenco Candidati:");
				initPartito(partiti);
				initCandidati(ricercaCandidati);
			}
		}
	}

	private void initPartito(List<Partito> partiti) {
		partitiVbox.getChildren().clear();
		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			Label partitiLabel = new Label();
			partitiLabel.setFont(new Font(20));
			Button partitiBottone = new Button("Modifica");
			Button partitiElencoBottone = new Button(">>");
			partitiElencoBottone.setOnAction(visualizzaCandidati);
			partitiBottone.setUserData(partiti.get(i));
			partitiElencoBottone.setUserData(partiti.get(i));
			partitiBottone.setFont(new Font(15));
			partitiElencoBottone.setFont(new Font(15));
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
	
	private void initCandidati(List<Candidato> candidati) {
		candidatiVbox.getChildren().clear();

		for(int i=0;i<candidati.size();i++){
			HBox candidatiHbox=new HBox();
			Label candidatiLabel = new Label();
			Label partitiLabel = new Label();
			candidatiLabel.setFont(new Font(20));
			partitiLabel.setFont(new Font(15));
			Button candidatiBottone=new Button("Modifica");
			candidatiBottone.setFont(new Font(15));
			candidatiBottone.setUserData(candidati.get(i));
			
			candidatiLabel.setText(candidati.get(i).getNome()+" "+candidati.get(i).getCognome());
			partitiLabel.setText(candidati.get(i).getPartito().getNome());
		
			
			Region region1=new Region();
			Region region2 = new Region();
			candidatiHbox.setHgrow(region1,Priority.ALWAYS);
			candidatiHbox.setHgrow(region2,Priority.ALWAYS);
			
			candidatiHbox.getChildren().addAll(candidatiLabel, region1, partitiLabel, region2, candidatiBottone);
			candidatiVbox.getChildren().add(candidatiHbox);
		}
	}
	
	private EventHandler<ActionEvent> visualizzaCandidati=new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){

			Partito partito=(Partito)((Button)e.getSource()).getUserData();
			candidatiLabel.setText("Candidati partito "+partito.getNome());candidatiLabel.setVisible(true);
			CandidatoDAOImpl candidatiDb=new CandidatoDAOImpl();
			candidati=candidatiDb.getAllCandidatoByPartito(partito);
			
			initCandidati(candidati);
			
		}
	};

			@FXML
			void initialize() {
				assert candidatiLabel != null
						: "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert candidatiVbox != null
						: "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert candidatoButton != null
						: "fx:id=\"candidatoButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert cercaButton != null
						: "fx:id=\"cercaButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert cercaComboBox != null
						: "fx:id=\"cercaComboBox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert cercaTextField != null
						: "fx:id=\"cercaTextField\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert logoutButton != null
						: "fx:id=\"logoutButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert menuButton != null
						: "fx:id=\"menuButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert nomeGestore != null
						: "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert partitiVbox != null
						: "fx:id=\"partitiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
				assert partitoButton != null
						: "fx:id=\"partitoButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";

				ObservableList<String> dbTypeList = FXCollections.observableArrayList("Partito", "Candidato");
				cercaComboBox.setItems(dbTypeList);

				PartitoDAOImpl partitoDb = new PartitoDAOImpl();
				partiti = partitoDb.getAllPartito();
				initPartito(partiti);
			}

}
