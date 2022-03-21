package com.gbrsni.votoelettronico.controller;
import com.gbrsni.votoelettronico.models.Partito;

import java.net.URL;
import java.util.ArrayList;
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
    private Button aggiungiBottone;

    @FXML
    private Button backBottone;

    @FXML
    private Label nomeGestore;
    
    @FXML
    private TextField cognomeCandidatoTextField;

    @FXML
    private TextField nomeCandidatoTextField;

    
    @FXML
    private ComboBox<String> partitiComboBox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
		this.gestore = (Gestore) parameter;
		nomeGestore.setText(gestore.getUsername());
	
    }
    @FXML
    void pressAggiungiButton(ActionEvent event) {
    	CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
    	Partito p = null; 
    	for(int i = 0; i< partiti.size(); i++) {
    		if (partiti.get(i).getNome().equals(partitiComboBox.getValue()))
    			p = new Partito(partiti.get(i).getId(), partiti.get(i).getNome());
    	}
    	Candidato c = new Candidato(0, nomeCandidatoTextField.getText(), cognomeCandidatoTextField.getText(), p);
    	candidatiDb.addCandidato(c);
    	navigate("GestioneListeView", gestore);
    }
    
    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestioneListeView", gestore);
    }

  
    @FXML
    void initialize() {
    	assert aggiungiBottone != null : "fx:id=\"aggiungiBottone\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert backBottone != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert cognomeCandidatoTextField != null : "fx:id=\"cognomeCandidatoTextField\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert nomeCandidatoTextField != null : "fx:id=\"nomeCandidatoTextField\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        assert partitiComboBox != null : "fx:id=\"partitiComboBox\" was not injected: check your FXML file 'AggiuntaCandidatoView.fxml'.";
        PartitoDAOImpl partitiDb = new PartitoDAOImpl();
        partiti = partitiDb.getAllPartito();
        List<String> nomiPartiti = new ArrayList<>();
        for (int i = 0; i < partiti.size(); i++) {
        	nomiPartiti.add(partiti.get(i).getNome());
        }
        
        ObservableList<String> dbTypeList = FXCollections.observableArrayList(nomiPartiti);
        partitiComboBox.setItems(dbTypeList);
    }

}
