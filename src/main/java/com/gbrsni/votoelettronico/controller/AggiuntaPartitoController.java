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

public class AggiuntaPartitoController extends Controller {
	
	private Gestore gestore;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBottone;

    @FXML
    private Label nomeGestore;

    @FXML
    private TextField nomePartitoTextField;

    @FXML
    private Button salvaBottone;
    

    public void onNavigateFrom(Controller sender, Object parameter) {
    		this.gestore = (Gestore) parameter;
    		nomeGestore.setText(gestore.getUsername());
    	
    }
    
    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestioneListeView", gestore);
    }

    @FXML
    void pressSalvaButton(ActionEvent event) {
    	Partito p = new Partito(0,nomePartitoTextField.getText());
    	PartitoDAOImpl partitoDb = new PartitoDAOImpl();
    	partitoDb.addPartito(p);
    	navigate("GestioneListeView",gestore);
    }

    @FXML
    void initialize() {
        assert backBottone != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'AggiuntaPartitoView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'AggiuntaPartitoView.fxml'.";
        assert nomePartitoTextField != null : "fx:id=\"nomePartitoTextField\" was not injected: check your FXML file 'AggiuntaPartitoView.fxml'.";
        assert salvaBottone != null : "fx:id=\"salvaBottone\" was not injected: check your FXML file 'AggiuntaPartitoView.fxml'.";

    }

}
