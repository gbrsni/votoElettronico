package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.Candidato;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToggleGroup;

public class VotazioneController extends Controller{
	
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	private Elettore elettore;
	private ModVoto modVoto ; 
	private Map<Partito, List<Candidato>> candidati;
	
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private HBox votazioneHbox;
    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;
   
    @FXML
    private Button votaBottone;
    
    /**
    @FXML
    private ScrollPane candidatiScrollPane;
    
    @FXML
    private VBox candidatiVbox;
    */
    //bisogna passare prima elettore, poi sessione, e infine gestore
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] dati = (Object[]) parameter;
    	   	elettore = (Elettore) dati[0];
	        sessione = (SessioneDiVoto) dati[1];
    	try {
	        gestore = (Gestore) dati[2];  
    	} catch(Exception e) {}
    	
    	modVoto = sessione.getModVoto();
    	init();
    }
    
    public void init() {
    	nomeElettore.setText(elettore.getUsername());
     	nomeLabel.setText(sessione.getNome());
     	modVotoLabel.setText("Modalità di Voto: " + sessione.getModVoto());
     	
     	List<Candidato> listCandidato = new ArrayList<>(sessione.getVotiCandidati().keySet());
     	candidati = listCandidato.stream()
     		   .collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));
     	
     	switch(modVoto) {
     		case ORDINALE:
     			System.out.println("Ordinale");
     			
     			break;
     		case CATEGORICO:
     			System.out.println("Categorico");
     			votoCategorico();
     			break;
     		case CATEGORICO_CON_PREFERENZE :
     			System.out.println("Categorico con preferenze");
     			votoCategoricoPreferenze();
     			break;
     		case REFERENDUM:
     			System.out.println("Referendum");
     			votoReferendum();
     			break;
     	
     	}
    }
    
    private void votoCategorico() {
    	
    	ScrollPane candidatiScrollPane = new ScrollPane();
    	VBox candidatiVbox = new VBox();
    	ComboBox partitiComboBox = new ComboBox();
    	
    	ObservableList<Partito> dbTypeList = FXCollections.observableArrayList(candidati.keySet());
        partitiComboBox.setItems(dbTypeList);
        partitiComboBox.setOnAction(selezionePartitoCategorico);
        
        votazioneHbox.getChildren().add(partitiComboBox);
        votazioneHbox.getChildren().add(candidatiScrollPane);
        candidatiScrollPane.setContent(candidatiVbox);
        candidatiScrollPane.setPrefSize(500, 500);
    }
    
    private void votoCategoricoPreferenze() {
    	ScrollPane candidatiScrollPane = new ScrollPane();
    	VBox candidatiVbox = new VBox();
    	ComboBox partitiComboBox = new ComboBox();
    	
    	ObservableList<Partito> dbTypeList = FXCollections.observableArrayList(candidati.keySet());
        partitiComboBox.setItems(dbTypeList);
        partitiComboBox.setOnAction(selezionePartitoCategoricoPreferenze);
        
        votazioneHbox.getChildren().add(partitiComboBox);
        votazioneHbox.getChildren().add(candidatiScrollPane);
        candidatiScrollPane.setContent(candidatiVbox);
        candidatiScrollPane.setPrefSize(500, 500);
    	
    }
    private void votoReferendum() {
    	
    }
    
    
    private EventHandler<ActionEvent> selezionePartitoCategorico = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {	
        	
        	VBox vbox = ((VBox)((ScrollPane)votazioneHbox.getChildren().get(1)).getContent());
        	vbox.getChildren().clear();
        	
        	ToggleGroup radioGroup = new ToggleGroup();
        	
        	Partito partito = (Partito)((ComboBox)e.getSource()).getValue();
        	List<Candidato> candidatiPartito = candidati.get(partito);
        	for(int i = 0; i < candidatiPartito.size(); i++) {
        		HBox candidatoHbox = new HBox();
        		RadioButton candidatoRadioButton = new RadioButton();
        		candidatoRadioButton.setText(candidatiPartito.get(i).getNome() + " " + candidatiPartito.get(i).getCognome());
        		candidatoRadioButton.setUserData(candidatiPartito.get(i));
        		candidatoRadioButton.setToggleGroup(radioGroup);
        		
        		candidatoHbox.getChildren().add(candidatoRadioButton);
        		vbox.getChildren().add(candidatoHbox);
        	}
        	
     
        }
    };
    
    private EventHandler<ActionEvent> selezionePartitoCategoricoPreferenze = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {	
        	
        	VBox vbox = ((VBox)((ScrollPane)votazioneHbox.getChildren().get(1)).getContent());
        	vbox.getChildren().clear();
        	
        	ToggleGroup checkGroup = new ToggleGroup();
        	
        	Partito partito = (Partito)((ComboBox)e.getSource()).getValue();
        	List<Candidato> candidatiPartito = candidati.get(partito);
        	for(int i = 0; i < candidatiPartito.size(); i++) {
        		HBox candidatoHbox = new HBox();
        		CheckBox candidatocheckButton = new CheckBox();
        		candidatocheckButton.setText(candidatiPartito.get(i).getNome() + " " + candidatiPartito.get(i).getCognome());

        		candidatoHbox.getChildren().add(candidatocheckButton);
        		vbox.getChildren().add(candidatoHbox);
        	}
        	
     
        }
    };
    
    @FXML
    void pressVotaButton(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
    	assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert votaBottone != null : "fx:id=\"votaBottone\" was not injected: check your FXML file 'VotazioneView.fxml'.";
        assert votazioneHbox != null : "fx:id=\"votazioneHBox\" was not injected: check your FXML file 'VotazioneView.fxml'.";
      
       
    }

}
