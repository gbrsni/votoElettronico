package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificaCandidatoController extends ControllerGestore {

	
	private Candidato candidato;
	private List<Partito> partiti;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aggiungiButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField cognomeCandidatoTextField;

    @FXML
    private Label erroreLabel;

    @FXML
    private TextField nomeCandidatoTextField;

    @FXML
    private Label nomeGestore;

    @FXML
    private ComboBox<Partito> partitiComboBox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] data = (Object[]) parameter;
		gestore = (Gestore) data[0];
candidato = (Candidato) data[1];
		init();
    }
    
    @FXML
    void pressAggiungiButton(ActionEvent event) {
    	erroreLabel.setVisible(false);
    	if(nomeCandidatoTextField.getText().trim().equals("") || cognomeCandidatoTextField.getText().trim().equals("") ||partitiComboBox.getValue() == null ) {
    		erroreLabel.setVisible(true);
    	} else {
    		candidato.setNome(nomeCandidatoTextField.getText().trim());
    		candidato.setCognome(cognomeCandidatoTextField.getText().trim());
    		candidato.setPartito(partitiComboBox.getValue());
			CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
			candidatiDb.updateCandidato(candidato);
			navigate("GestioneListeView", gestore);
    	}
    }

    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestioneListeView", gestore);
    }
    
    private void init() {	
    	PartitoDAOImpl partitiDb = new PartitoDAOImpl();
        partiti = partitiDb.getAllPartito();
        ObservableList<Partito> listaPartiti = FXCollections.observableArrayList(partiti);
        partitiComboBox.setItems(listaPartiti);
        
        nomeGestore.setText(gestore.getUsername());
		nomeCandidatoTextField.setText(candidato.getNome());
		cognomeCandidatoTextField.setText(candidato.getCognome());
        partitiComboBox.setValue(candidato.getPartito());
    }
    
    @FXML
    void initialize() {
        assert aggiungiButton != null : "fx:id=\"aggiungiButton\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert cognomeCandidatoTextField != null : "fx:id=\"cognomeCandidatoTextField\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert nomeCandidatoTextField != null : "fx:id=\"nomeCandidatoTextField\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
        assert partitiComboBox != null : "fx:id=\"partitiComboBox\" was not injected: check your FXML file 'ModificaCandidatoView.fxml'.";
    }

}
