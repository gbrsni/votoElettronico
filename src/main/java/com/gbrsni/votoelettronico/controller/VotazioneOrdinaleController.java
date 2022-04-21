package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class VotazioneOrdinaleController extends Controller {

	private Elettore elettore;
	private Gestore gestore;
	private SessioneDiVoto sessione;
	private Map<Partito, List<Candidato>> candidati;
	private List<Partito> partiti;

	private ToggleGroup radioGroup;
	private RadioButton[][] radioButtonVoto;

	private Map<Partito, Integer> partitoSelezionato ;
	private Map<Candidato, Integer> candidatoSelezionato ;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button annullaBottone;
	
	@FXML
	private Label erroreLabel;
	  
	@FXML
	private ScrollPane partitiScrollPane;

	@FXML
	private ScrollPane candidatiScrollPane;

	@FXML
	private RadioButton candidatiRadioButton;

	@FXML
	private Label candidatiLabel;

	@FXML
	private VBox candidatiVbox;

	@FXML
	private Label modVotoLabel;

	@FXML
	private Label nomeElettore;

	@FXML
	private Label nomeLabel;

	@FXML
	private RadioButton partitiRadioButton;

	@FXML
	private Label partitiLabel;

	@FXML
	private VBox partitiVbox;

	@FXML
	private Button votaBottone;

	@Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];
		nomeElettore.setText("Elettore: " + elettore.getNome());
		nomeLabel.setText("Sessione: " + sessione.getNome());
		modVotoLabel.setText("Mod Voto: " + sessione.getModVoto());
	

	}

	@FXML
	void pressPartitiRadioButton(ActionEvent event) {
		
		candidatoSelezionato.clear();
		partitoSelezionato.clear();
		
		partitiVbox.getChildren().clear();
		partitiScrollPane.setVisible(true);
		partitiScrollPane.setPrefWidth(600);
		candidatiVbox.getChildren().clear();
		candidatiScrollPane.setVisible(false);
		radioButtonVoto = new RadioButton[partiti.size()][partiti.size()];

		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			Label partitiLabel = new Label();
			partitiLabel.setText(partiti.get(i).getNome());
			Region partitiRegion = new Region();
			partitiHbox.setHgrow(partitiRegion, Priority.ALWAYS);

			partitiHbox.getChildren().add(partitiLabel);
			partitiHbox.getChildren().add(partitiRegion);
			ToggleGroup partitiGroup = new ToggleGroup();
			for (int j = 0; j < partiti.size(); j++) {
				radioButtonVoto[i][j] = new RadioButton();
				radioButtonVoto[i][j].setUserData(partiti.get(i));
				radioButtonVoto[i][j].setText(j + 1 + "");
				radioButtonVoto[i][j].setToggleGroup(partitiGroup);
				radioButtonVoto[i][j].setOnAction(votoPartiti);
				partitiHbox.getChildren().add(radioButtonVoto[i][j]);
			}

			partitiVbox.getChildren().add(partitiHbox);
		}
	}

	EventHandler<ActionEvent> votoPartiti = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			
			int size = partiti.size();
			Partito p = (Partito)((RadioButton)e.getSource()).getUserData();
			Integer v = size - Integer.valueOf(((RadioButton)e.getSource()).getText()) + 1 ;
			
			if(partitoSelezionato.containsKey(e)) {
				partitoSelezionato.replace(p,v);
			} else {
			partitoSelezionato.put(p,v);
			}
			
		}
	};

	@FXML
	void pressCandidatiRadioButton(ActionEvent event) {
		candidatoSelezionato.clear();
		partitoSelezionato.clear();
		
		partitiVbox.getChildren().clear();
		ToggleGroup partitiGroup = new ToggleGroup();
		partitiScrollPane.setVisible(true);
		partitiScrollPane.setPrefWidth(400);
		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			RadioButton partitiRadio = new RadioButton();
			partitiRadio.setText(partiti.get(i).getNome());
			partitiRadio.setOnAction(visualizzazioneCandidati);
			partitiRadio.setUserData(partiti.get(i));

			partitiRadio.setToggleGroup(partitiGroup);
			partitiHbox.getChildren().add(partitiRadio);
			partitiVbox.getChildren().add(partitiHbox);
		}
	}

	EventHandler<ActionEvent> visualizzazioneCandidati = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			
			candidatoSelezionato.clear();
			partitoSelezionato.clear();
			
			Partito p  = (Partito) ((RadioButton) e.getSource()).getUserData();
			partitoSelezionato.put(p, 1);
			
			List<Candidato> listCandidati = candidati.get(p);

			candidatiVbox.getChildren().clear();
			candidatiScrollPane.setVisible(true);
			candidatiScrollPane.setPrefWidth(600);
			radioButtonVoto = new RadioButton[listCandidati.size()][listCandidati.size()];

			for (int i = 0; i < listCandidati.size(); i++) {
				HBox candidatiHbox = new HBox();
				Label candidatiLabel = new Label();
				candidatiLabel.setText(listCandidati.get(i).getNome() + " " + listCandidati.get(i).getCognome());
				Region candidatiRegion = new Region();
				candidatiHbox.setHgrow(candidatiRegion, Priority.ALWAYS);

				candidatiHbox.getChildren().add(candidatiLabel);
				candidatiHbox.getChildren().add(candidatiRegion);
				ToggleGroup candidatiGroup = new ToggleGroup();
				for (int j = 0; j < listCandidati.size(); j++) {
					radioButtonVoto[i][j] = new RadioButton();
					radioButtonVoto[i][j].setUserData(listCandidati.get(i));
					radioButtonVoto[i][j].setText(j + 1 + "");
					radioButtonVoto[i][j].setOnAction(votoCandidati);
					radioButtonVoto[i][j].setToggleGroup(candidatiGroup);
					candidatiHbox.getChildren().add(radioButtonVoto[i][j]);
				}

				candidatiVbox.getChildren().add(candidatiHbox);
			}
		}

	};
	
	EventHandler<ActionEvent> votoCandidati = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			
			Candidato p = (Candidato)((RadioButton)e.getSource()).getUserData();
			int size = candidati.get(p.getPartito()).size();
			Integer v = size - Integer.valueOf(((RadioButton)e.getSource()).getText()) + 1 ;
			
			if(candidatoSelezionato.containsKey(e)) {
				candidatoSelezionato.replace(p,v);
			} else {
				candidatoSelezionato.put(p,v);
			}
			
		}
	};

	@FXML
	void pressAnnullaButton(ActionEvent event) {
		radioGroup.getSelectedToggle().setSelected(false);
		partitoSelezionato.clear();
		candidatoSelezionato.clear();
		partitiScrollPane.setVisible(false);
		candidatiScrollPane.setVisible(false);
		partitiVbox.getChildren().clear();
		candidatiVbox.getChildren().clear();
	}

	@FXML
	void pressVotaButton(ActionEvent event) {
		
		erroreLabel.setVisible(false);
		
		List<Integer> value = new ArrayList<>();
		
		boolean error = false;
		for (Map.Entry<Partito, Integer> entry : partitoSelezionato.entrySet()) {
		    System.out.println("Partito " + entry.getKey() + "/" + entry.getValue());
		    if (value.contains(entry.getValue())){
		    	erroreLabel.setVisible(true);
		    } else {
		    	value.add(entry.getValue());
		    	error = true;
		    	break;
		    }
		}
		
		value.clear();
		for (Map.Entry<Candidato, Integer> entry : candidatoSelezionato.entrySet()) {
		    System.out.println("Candidato " + entry.getKey() + "/" + entry.getValue());
		    if (value.contains(entry.getValue())){
		    	erroreLabel.setVisible(true);
		    } else {
		    	value.add(entry.getValue());
		    	error = true;
		    	break;
		    }
		}
		Object[] parameter = new Object[] {elettore, sessione, null, partitoSelezionato, candidatoSelezionato};
		newStage("Conferma Voto", "ConfermaVotazioneView", parameter);
	}

	@FXML
	void initialize() {
		 assert annullaBottone != null : "fx:id=\"annullaBottone\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert candidatiLabel != null : "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert candidatiRadioButton != null : "fx:id=\"candidatiRadioButton\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert candidatiScrollPane != null : "fx:id=\"candidatiScrollPane\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert partitiLabel != null : "fx:id=\"partitiLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert partitiRadioButton != null : "fx:id=\"partitiRadioButton\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert partitiScrollPane != null : "fx:id=\"partitiScrollPane\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
	        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
		radioGroup = new ToggleGroup();
		partitiRadioButton.setToggleGroup(radioGroup);
		candidatiRadioButton.setToggleGroup(radioGroup);

		CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
		candidati = candidatiDb.getAllCandidato().stream()
				.collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));

		partiti = new ArrayList<>();
		partiti.addAll(candidati.keySet());

		partitoSelezionato = new HashMap<>();
		candidatoSelezionato = new HashMap<>();
	}

}
