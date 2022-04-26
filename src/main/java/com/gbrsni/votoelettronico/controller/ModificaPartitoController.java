package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificaPartitoController extends Controller{
	
	private Gestore gestore;
	private Partito partito;
	
	   @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Button backButton;

	    @FXML
	    private Label erroreLabel;

	    @FXML
	    private Label nomeGestore;

	    @FXML
	    private TextField nomePartitoTextField;

	    @FXML
	    private Button salvaButton;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] data = (Object[]) parameter;
		gestore = (Gestore) data[0];
		partito = (Partito) data[1];
    }
    
    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestioneListeView", gestore);
    }

    @FXML
    void pressSalvaButton(ActionEvent event) {
    	if(nomePartitoTextField.getText().trim().equals("")) {
    		erroreLabel.setVisible(true);
    	} else {
	    	partito.setNome(nomePartitoTextField.getText().trim());
	    	PartitoDAOImpl partitoDb = new PartitoDAOImpl();
	    	partitoDb.updatePartito(partito);
	    	navigate("GestioneListeView", gestore);
    	}
    }
    
    private void init() {
    	nomeGestore.setText(gestore.getUsername());
		nomePartitoTextField.setText(partito.getNome());
    }
    
    @FXML
    void initialize() {
    	 assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ModificaPartitoView.fxml'.";
         assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'ModificaPartitoView.fxml'.";
         assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'ModificaPartitoView.fxml'.";
         assert nomePartitoTextField != null : "fx:id=\"nomePartitoTextField\" was not injected: check your FXML file 'ModificaPartitoView.fxml'.";
         assert salvaButton != null : "fx:id=\"salvaButton\" was not injected: check your FXML file 'ModificaPartitoView.fxml'.";
    }

}
