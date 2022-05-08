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
import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Timer;
import com.gbrsni.votoelettronico.models.TimerListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;

public class VotazioneOrdinaleController extends Controller {

	private Elettore elettore;
	private Gestore gestore;
	private SessioneDiVoto sessione;
	private Map<Partito, List<Candidato>> candidati;
	private List<Partito> partiti;
	
	private Timer timer = new Timer(15*60);
	private RadioButton[][] radioButtonVoto;

	private Map<Partito, Integer> partitoSelezionato ;
	private Map<Candidato, Integer> candidatoSelezionato ;
	private Stage stage; 
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaBottone;

    @FXML
    private RadioButton candidatiRadioButton;

    @FXML
    private Label erroreLabel;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private ComboBox<Partito> partitiComboBox;

    @FXML
    private ToggleGroup radioGroup;
    
    @FXML
    private Label partitiLabel;

    @FXML
    private RadioButton partitiRadioButton;

    @FXML
    private ScrollPane sceltaScrollPane;

    @FXML
    private VBox sceltaVbox;

    @FXML
    private Button votaBottone;
    @FXML
    private Label timerLabel;


	@Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];
		nomeElettore.setText("Elettore: " + elettore.toString());
		nomeLabel.setText("Sessione: " + sessione.getNome());
		modVotoLabel.setText("Mod Voto: " + sessione.getModVoto());
		timer.addListener(new TimerListener(){
			@Override
			public void onReandingChange() {
				updateTimer();
			}
			
		});
		init();
	}
	
	private void updateTimer() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Pair time = timer.getTimer();
				timerLabel.setText("Tempo Rimasto: " + time.getKey() + ":" + time.getValue());
				if((Integer)time.getKey() == 0 && (Integer)time.getValue() == 0) {
					VotiAstenutiDAOImpl votiAstenutiDb = new VotiAstenutiDAOImpl();
					VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
					votiAstenutiDb.increaseVotiAstenutiBySessione(sessione);
					votiEspressiDb.addVotoEspresso(sessione, elettore);
					timer.shutdown();
					if(stage != null) stage.close();
					Object[] parameter = new Object[] {elettore,sessione,gestore};
					navigate("VotoRegistratoView",parameter);
				}
			}
			
		});
	}
	
	@FXML
	void pressPartitiRadioButton(ActionEvent event) {
		
		candidatoSelezionato.clear();
		partitoSelezionato.clear();
		sceltaVbox.getChildren().clear();
		partitiLabel.setVisible(false);
		partitiComboBox.setVisible(false);
		sceltaScrollPane.setVisible(true);
		radioButtonVoto = new RadioButton[partiti.size()][partiti.size()];

		for (int i = 0; i < partiti.size(); i++) {
			HBox partitiHbox = new HBox();
			Label partitiLabel = new Label();
			Region partitiRegion = new Region();
			
			partitiLabel.setText(partiti.get(i).getNome());
			partitiLabel.setFont(new Font(22));
			partitiHbox.setHgrow(partitiRegion, Priority.ALWAYS);
			
			partitiHbox.getChildren().add(partitiLabel);
			partitiHbox.getChildren().add(partitiRegion);
			ToggleGroup partitiGroup = new ToggleGroup();
			
			for (int j = 0; j < partiti.size(); j++) {
				radioButtonVoto[i][j] = new RadioButton();
				radioButtonVoto[i][j].setFont(new Font(15));
				radioButtonVoto[i][j].setUserData(partiti.get(i));
				radioButtonVoto[i][j].setText(j + 1 + "");
				radioButtonVoto[i][j].setToggleGroup(partitiGroup);
				radioButtonVoto[i][j].setOnAction(votoPartiti);
				partitiHbox.getChildren().add(radioButtonVoto[i][j]);
			}

			sceltaVbox.getChildren().add(partitiHbox);
		}
	}

	EventHandler<ActionEvent> votoPartiti = new EventHandler<ActionEvent>() {
		
		public void handle(ActionEvent e) {
			int size = partiti.size();
			Partito p = (Partito) ((RadioButton) e.getSource()).getUserData();
			Integer v = size - Integer.valueOf(((RadioButton) e.getSource()).getText()) + 1;
			if (partitoSelezionato.containsKey(e)) {
				partitoSelezionato.replace(p, v);
			} else {
				partitoSelezionato.put(p, v);
			}
			for (Map.Entry<Partito, Integer> entry : partitoSelezionato.entrySet()) {
				System.out.print(entry.getKey() + "/" + entry.getValue() + ";");
			}
			System.out.println("");
		}
		
	};

	@FXML
	void pressCandidatiRadioButton(ActionEvent event) {
		candidatoSelezionato.clear();
		partitoSelezionato.clear();
		sceltaVbox.getChildren().clear();
		sceltaScrollPane.setVisible(false);
		partitiLabel.setVisible(true);
		partitiComboBox.setVisible(true);
		partitiComboBox.setOnAction(pressPartitoComboBox);
	}

	EventHandler<ActionEvent> pressPartitoComboBox = new EventHandler<ActionEvent>() {
		
		public void handle(ActionEvent e) {
			candidatoSelezionato.clear();
			partitoSelezionato.clear();
			sceltaVbox.getChildren().clear();
			sceltaScrollPane.setVisible(true);
			Partito p  = (Partito)((ComboBox)e.getSource()).getValue();
			List<Candidato> listCandidati = candidati.get(p);
			partitoSelezionato.put(p, 1);
			
			radioButtonVoto = new RadioButton[listCandidati.size()][listCandidati.size()];
			for (int i = 0; i < listCandidati.size(); i++) {
				HBox candidatiHbox = new HBox();
				Label candidatiLabel = new Label();
				Region candidatiRegion = new Region();
						
				candidatiLabel.setText(listCandidati.get(i).getNome() + " " + listCandidati.get(i).getCognome());
				candidatiLabel.setFont(new Font(22));
				candidatiHbox.setHgrow(candidatiRegion, Priority.ALWAYS);
			
				candidatiHbox.getChildren().add(candidatiLabel);
				candidatiHbox.getChildren().add(candidatiRegion);
				ToggleGroup candidatiGroup = new ToggleGroup();
				for (int j = 0; j < listCandidati.size(); j++) {
					radioButtonVoto[i][j] = new RadioButton();
					radioButtonVoto[i][j].setFont(new Font(15));
					radioButtonVoto[i][j].setUserData(listCandidati.get(i));
					radioButtonVoto[i][j].setText(j + 1 + "");
					radioButtonVoto[i][j].setOnAction(votoCandidati);
					radioButtonVoto[i][j].setToggleGroup(candidatiGroup);
					candidatiHbox.getChildren().add(radioButtonVoto[i][j]);
				}
				sceltaVbox.getChildren().add(candidatiHbox);
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
			for (Map.Entry<Candidato, Integer> entry : candidatoSelezionato.entrySet()) {
				System.out.print(entry.getKey() + "/" + entry.getValue() + ";");
			}
			System.out.println("");
		}
	};


	@FXML
	void pressVotaButton(ActionEvent event) {
		
		erroreLabel.setVisible(false);
		List<Integer> value = new ArrayList<>();
		boolean error = false;
		
		for (Map.Entry<Partito, Integer> entry : partitoSelezionato.entrySet()) {
		    System.out.println("Partito " + entry.getKey() + "/" + entry.getValue());
		    if (value.contains(entry.getValue())){
		    	erroreLabel.setVisible(true);
		    	error = true;
		    	return;
		    } else {
		    	value.add(entry.getValue());
		    }
		}
		
		value.clear();
		for (Map.Entry<Candidato, Integer> entry : candidatoSelezionato.entrySet()) {
		    System.out.println("Candidato " + entry.getKey() + "/" + entry.getValue());
		    if (value.contains(entry.getValue())){
		    	erroreLabel.setVisible(true);
		    	error = true;
		    	return;
		    } else {
		    	value.add(entry.getValue());
		    }
		}
		Object[] parameter = new Object[] {elettore, sessione, gestore, partitoSelezionato, candidatoSelezionato, timer};
    	stage = newStage("Conferma Voto","ConfermaVotazioneView", parameter);
	}
	
	@FXML
	void pressAnnullaButton(ActionEvent event) {
		radioGroup.getSelectedToggle().setSelected(false);
		partitiComboBox.setValue(null);
		partitiComboBox.setVisible(false);
		partitiLabel.setVisible(false);
		partitoSelezionato.clear();
		candidatoSelezionato.clear();
		sceltaScrollPane.setVisible(false);
		sceltaVbox.getChildren().clear();	
	}
	
	private void init() {
		VotiCandidatiDAOImpl votiCandidatoDb = new VotiCandidatiDAOImpl();
		candidati = votiCandidatoDb.getVotiCandidatiBySessione(sessione).keySet().stream()
				.collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));
		partiti = new ArrayList<>();
		partiti.addAll(candidati.keySet());
		ObservableList<Partito> listaPartiti = FXCollections.observableArrayList(partiti);
	    partitiComboBox.setItems(listaPartiti);	
		partitoSelezionato = new HashMap<>();
		candidatoSelezionato = new HashMap<>();
	}
	
	@FXML
	void initialize() {
		assert annullaBottone != null : "fx:id=\"annullaBottone\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert candidatiRadioButton != null : "fx:id=\"candidatiRadioButton\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert partitiComboBox != null : "fx:id=\"partitiComboBox\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert partitiLabel != null : "fx:id=\"partitiLabel\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert partitiRadioButton != null : "fx:id=\"partitiRadioButton\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert radioGroup != null : "fx:id=\"radioGroup\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert sceltaScrollPane != null : "fx:id=\"sceltaScrollPane\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert sceltaVbox != null : "fx:id=\"sceltaVbox\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneOrdinaleView.fxml'.";
        assert timerLabel != null : "fx:id=\"timerLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";

	}

}
