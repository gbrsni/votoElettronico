package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
		Logging.infoMessage(this.getClass(), "Eseguito il logout dal gestore " + gestore);
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
			candidatiLabel.setText("");
			initPartito(partiti);
		}else {
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
	
	
	private void init() {
		ObservableList<String> listaCercaComboBox = FXCollections.observableArrayList("Partito", "Candidato");
		cercaComboBox.setItems(listaCercaComboBox);
		PartitoDAOImpl partitoDb = new PartitoDAOImpl();
		partiti = partitoDb.getAllPartito();
		initPartito(partiti);
	}

	private void initPartito(List<Partito> partiti) {
		partitiVbox.getChildren().clear();
		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			Label partitiLabel = new Label();
			Button eliminaBottone= new Button("Elimina");
			Button modificaBottone = new Button("Modifica");
			Button elencoBottone = new Button(">>");
			Region region1 = new Region();
			
			partitiLabel.setFont(new Font(20));
			partitiLabel.setText(partiti.get(i).getNome());
			eliminaBottone.setFont(new Font(12));
			modificaBottone.setFont(new Font(12));
			elencoBottone.setFont(new Font(12));
			
			eliminaBottone.setUserData(partiti.get(i));
			modificaBottone.setUserData(partiti.get(i));
			elencoBottone.setUserData(partiti.get(i));
			eliminaBottone.setOnAction(pressEliminaPartitoButton);
			modificaBottone.setOnAction(pressModificaPartitoButton);
			elencoBottone.setOnAction(pressVisualizzaCandidatiButton);
			partitiHbox.setHgrow(region1, Priority.ALWAYS);
			
			partitiHbox.getChildren().addAll(partitiLabel,region1,eliminaBottone,modificaBottone,elencoBottone);
			partitiVbox.getChildren().add(partitiHbox);
		}
	}
	
	private void initCandidati(List<Candidato> candidati) {
		candidatiVbox.getChildren().clear();
		for(int i=0;i<candidati.size();i++){
			HBox candidatiHbox=new HBox();
			Label candidatiLabel = new Label();
			Label partitiLabel = new Label();
			Button modificaBottone=new Button("Modifica");
			Button eliminaBottone=new Button("Elimina");
			Region region1=new Region();
			Region region2 = new Region();
			
			candidatiLabel.setFont(new Font(20));
			partitiLabel.setFont(new Font(12));
			eliminaBottone.setFont(new Font(12));
			modificaBottone.setFont(new Font(12));
			candidatiLabel.setText(candidati.get(i).getNome()+" "+candidati.get(i).getCognome());
			partitiLabel.setText(candidati.get(i).getPartito().getNome());
			candidatiHbox.setHgrow(region1,Priority.ALWAYS);
			candidatiHbox.setHgrow(region2,Priority.ALWAYS);
			
			eliminaBottone.setOnAction(pressEliminaCandidatoButton);
			modificaBottone.setOnAction(pressModificaCandidatoButton);
			eliminaBottone.setUserData(candidati.get(i));
			modificaBottone.setUserData(candidati.get(i));
	
			candidatiHbox.getChildren().addAll(candidatiLabel, region1, partitiLabel, region2, eliminaBottone,modificaBottone);
			candidatiVbox.getChildren().add(candidatiHbox);
		}
	}
	
	private EventHandler<ActionEvent> pressEliminaPartitoButton =new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){
	            Partito p = (Partito)((Button)e.getSource()).getUserData();
	            System.out.println("Id partito:" + p.getId());
	            Object[] parameter = new Object[] {gestore,p};
	            newStage("Elimina Partito", "EliminaPartitoView", parameter);
		}
	};
	
	private EventHandler<ActionEvent> pressModificaPartitoButton =new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){
	            Partito p = (Partito)((Button)e.getSource()).getUserData();
	            System.out.println("Id partito:" + p.getId());
	            Object[] parameter = new Object[] {gestore,p};
	            navigate("ModificaPartitoView", parameter);
		}
	};
	
	private EventHandler<ActionEvent> pressVisualizzaCandidatiButton=new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){
			Partito partito=(Partito)((Button)e.getSource()).getUserData();
			candidatiLabel.setText("Candidati partito "+partito.getNome());candidatiLabel.setVisible(true);
			CandidatoDAOImpl candidatiDb=new CandidatoDAOImpl();
			candidati=candidatiDb.getAllCandidatoByPartito(partito);
			initCandidati(candidati);	
		}
	};
	
	private EventHandler<ActionEvent> pressEliminaCandidatoButton =new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){
	            Candidato c = (Candidato)((Button)e.getSource()).getUserData();
	            System.out.println("Id candidato:" + c.getId());
	            Object[] parameter = new Object[] {gestore,c};
	            newStage("Elimina Candidato", "EliminaCandidatoView", parameter);
		}
	};
	
	private EventHandler<ActionEvent> pressModificaCandidatoButton =new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e){
	            Candidato c = (Candidato)((Button)e.getSource()).getUserData();
	            System.out.println("Id candidato:" + c.getId());
	            Object[] parameter = new Object[] {gestore,c};
	            navigate("ModificaCandidatoView", parameter);
		}
	};
	
	
	@FXML
	void initialize() {
		assert candidatiLabel != null : "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert candidatoButton != null : "fx:id=\"candidatoButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert cercaButton != null : "fx:id=\"cercaButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert cercaComboBox != null : "fx:id=\"cercaComboBox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert cercaTextField != null : "fx:id=\"cercaTextField\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert partitoButton != null : "fx:id=\"partitoButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        init();
	}

}
