package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GestioneListeController extends Controller{
	
	private Gestore gestore;
	private List<Partito> partiti;
	private List<Candidato> candidati;
	
	@FXML
	private VBox candidatiVbox;

	@FXML
	private VBox partitiVbox;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button candidatoBottone;

    @FXML
    private Button logoutBottone;

    @FXML
    private Button menuButton;

    @FXML
    private Label nomeGestore;

    @FXML
    private Button partitoBottone;
    

    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML	
    void handleAggiungiCandidato(ActionEvent event) {
    	navigate("AggiuntaCandidatoView", gestore);
    }

    @FXML
    void handleAggiungiPartito(ActionEvent event) {
    	navigate("AggiuntaPartitoView", gestore);
    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void handleMenu(ActionEvent event) {
    	navigate("DashBoardView", gestore);
    }
    
    private void initPartito() {
    	partitiVbox.getChildren().clear();
        partitiVbox.setPrefWidth(400);
        
        for (int i = 0; i < partiti.size(); i++) {
	        HBox partitiHbox = new HBox();
	        Label partitiLabel = new Label();
	        partitiLabel.setFont(new Font(15));
	        Button partitiBottone = new Button("Modifica");
	        partitiBottone.setId(partiti.get(i).getId() + "");
	        partitiLabel.setText(partiti.get(i).getNome());
	        Region region1 = new Region();
	        partitiHbox.setHgrow(region1, Priority.ALWAYS);
	        partitiHbox.getChildren().add(partitiLabel);
	        partitiHbox.getChildren().add(region1);
	        partitiHbox.getChildren().add(partitiBottone);
	        partitiVbox.getChildren().add(partitiHbox);
        }
    }
    
    private void initCandidato() {
    	candidatiVbox.getChildren().clear();
        candidatiVbox.setPrefWidth(400);
        
        for (int i = 0; i < candidati.size(); i++) {
	        HBox candidatiHbox = new HBox();
	        Label candidatiLabel = new Label();
	        candidatiLabel.setFont(new Font(15));
	        Button candidatiBottone = new Button("Modifica");
	        candidatiBottone.setId(candidati.get(i).getId() + "");
	        candidatiLabel.setText(candidati.get(i).getNome() + " " + candidati.get(i).getCognome() + "  partito:" + candidati.get(i).getPartito().getNome() );
	        Region region1 = new Region();
	        candidatiHbox.setHgrow(region1, Priority.ALWAYS);
	        candidatiHbox.getChildren().add(candidatiLabel);
	        candidatiHbox.getChildren().add(region1);
	        candidatiHbox.getChildren().add(candidatiBottone);
	        candidatiVbox.getChildren().add(candidatiHbox);
        }
    }
    
    
    @FXML
    void initialize() {
        assert candidatoBottone != null : "fx:id=\"candidatoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert partitoBottone != null : "fx:id=\"partitoBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        PartitoDAOImpl partitoDb = new PartitoDAOImpl();
        partiti = partitoDb.getAllPartito();
        CandidatoDAOImpl candidatoDb = new CandidatoDAOImpl();
        candidati = candidatoDb.getAllCandidato();
        initPartito();
        initCandidato();
    }

}
