package com.gbrsni.votoelettronico.controller;
import com.gbrsni.votoelettronico.models.Partito;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AggiuntaCandidatoController extends Controller {
	
	private Gestore gestore;
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
    private Label nomeGestore;
    
    @FXML
    private TextField cognomeCandidatoTextField;

    @FXML
    private TextField nomeCandidatoTextField;
    
    @FXML
    private Label erroreLabel;
    
    @FXML
    private ComboBox<Partito> partitiComboBox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
		this.gestore = (Gestore) parameter;
		nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML
    void pressAggiungiButton(ActionEvent event) {
    	erroreLabel.setVisible(false);
    	if(nomeCandidatoTextField.getText().trim().equals("") || cognomeCandidatoTextField.getText().trim().equals("") ||partitiComboBox.getValue() == null ) {
    		erroreLabel.setVisible(true);
    	} else {
			CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
			Candidato c = new Candidato(0, nomeCandidatoTextField.getText().trim(), cognomeCandidatoTextField.getText().trim(), partitiComboBox.getValue());
			candidatiDb.addCandidato(c);
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
    }
    
    @FXML
    void initialize() {
    	assert aggiungiButton != null : "fx:id=\"aggiungiBottone\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert backButton != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert cognomeCandidatoTextField != null : "fx:id=\"cognomeCandidatoTextField\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert nomeCandidatoTextField != null : "fx:id=\"nomeCandidatoTextField\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert partitiComboBox != null : "fx:id=\"partitiComboBox\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        init();
    }

}
