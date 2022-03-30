package com.gbrsni.votoelettronico.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class VotazioneCategoricoController extends Controller{
	
	private Elettore elettore;
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	private Map<Partito, List<Candidato>> candidati; 
	private List<Partito> partiti; 
	private ToggleGroup partitiRadioGroup = new ToggleGroup();
	
	private Partito partitoSelezionato = null; 
	private List<Candidato> candidatoSelezionato = new ArrayList<>();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label candidatiLabel;

    @FXML
    private VBox candidatiVbox;

    @FXML
    private Label modVotoLabel;
    
    @FXML
    private Button annullaButton;
    
    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

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
		nomeElettore.setText("Elettore: " + elettore.getNome());
		nomeLabel.setText("Sessione: " + sessione.getNome());
		modVotoLabel.setText("Mod Voto: " + sessione.getModVoto());
		try {
			gestore = (Gestore) data[2];
		}catch(Exception e) {
		
		}

		visualizzaPartiti();
	}
		
	public void visualizzaPartiti() {
		
		ToggleGroup radioGroup = new ToggleGroup();
		for (int i = 0 ; i < partiti.size(); i ++) {
			
			HBox partitiHbox = new HBox();
			RadioButton partitiRadio = new RadioButton();
			partitiRadio.setText(partiti.get(i).getNome());
			partitiRadio.setUserData(partiti.get(i));
			partitiRadio.setFont(new Font(15));
			partitiRadio.setToggleGroup(partitiRadioGroup);
			partitiRadio.setOnAction(visualizzaCandidati);
			partitiHbox.getChildren().add(partitiRadio);
			partitiVbox.getChildren().add(partitiHbox);
			
		}
	}
	
	EventHandler<ActionEvent> visualizzaCandidati = new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent e) {
	    	
	    	candidatoSelezionato.clear();
	    	partitoSelezionato = (Partito)((RadioButton)e.getSource()).getUserData();
	    	List <Candidato> opzioni = new ArrayList<>(candidati.get(partitoSelezionato));
	    	
	    	candidatiVbox.getChildren().clear();
	    	ToggleGroup candidatiRadioGroup = new ToggleGroup();
	    	for(int i = 0; i < opzioni.size(); i++) {
	    		
	    		
				HBox candidatiHbox = new HBox();
				if (sessione.getModVoto().equals(ModVoto.CATEGORICO)) {
					RadioButton candidatiRadio = new RadioButton();
					candidatiRadio.setText(opzioni.get(i).getNome() + " " + opzioni.get(i).getCognome());
					candidatiRadio.setUserData(opzioni.get(i));
					candidatiRadio.setFont(new Font(15));
					candidatiRadio.setToggleGroup(candidatiRadioGroup);
					candidatiRadio.setOnAction(sceltaCandidatiCategorico);
					candidatiHbox.getChildren().add(candidatiRadio);
					candidatiVbox.getChildren().add(candidatiHbox);
					
				} else {
					
					CheckBox candidatiRadio = new CheckBox();
					candidatiRadio.setText(opzioni.get(i).getNome() + " " + opzioni.get(i).getCognome());
					candidatiRadio.setUserData(opzioni.get(i));
					candidatiRadio.setFont(new Font(15));
					candidatiRadio.setOnAction(sceltaCandidatiCategoricoPreferenze);
					candidatiHbox.getChildren().add(candidatiRadio);
					candidatiVbox.getChildren().add(candidatiHbox);
				}
	    	}
	    }
	};
	
	EventHandler<ActionEvent> sceltaCandidatiCategorico = new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent e) {
	    	candidatoSelezionato.clear();
	    	candidatoSelezionato.add((Candidato)((RadioButton)e.getSource()).getUserData());
	    
	    }
	};
	
	EventHandler<ActionEvent> sceltaCandidatiCategoricoPreferenze = new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent e) {
	    	
	    	candidatoSelezionato.add((Candidato)((CheckBox)e.getSource()).getUserData());
	    
	    }
	};
	
	@FXML
	void pressAnnullaButton(ActionEvent event) {
		partitoSelezionato = null;
		candidatoSelezionato.clear();
		candidatiVbox.getChildren().clear();
		partitiRadioGroup.getSelectedToggle().setSelected(false);
	}
	
    @FXML
    void pressVotaButton(ActionEvent event) {
    	Object[] parameter = new Object[] {elettore, sessione, partitoSelezionato, candidatoSelezionato};
    	Home.newStage("Conferma Voto","ConfermaVotazioneView", parameter);
    }

    @FXML
    void initialize() {
    	assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert candidatiLabel != null : "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert partitiLabel != null : "fx:id=\"partitiLabel\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneCategoricoView.fxml'.";
        
        CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
        candidati = candidatiDb.getAllCandidato().stream()
      		   .collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));
        
        partiti = new ArrayList<>();
        partiti.addAll(candidati.keySet());
        
    }



}
