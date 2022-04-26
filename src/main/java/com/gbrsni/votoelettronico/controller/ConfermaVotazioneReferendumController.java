package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfermaVotazioneReferendumController extends Controller{
	
	private Gestore gestore;
	private Elettore elettore;
	private SessioneDiVoto sessione;
	Boolean scelta; 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private VBox votoVbox;
    
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];	
		scelta = (Boolean) data[3];
		init();
	}
    
    private void init() {
    	Label voto = new Label();
    	voto.setFont(new Font(34));
    	votoVbox.setAlignment(Pos.CENTER_LEFT);
    	if(scelta == null)
    		voto.setText("Scheda Bianca");
    	else if(scelta)
    		voto.setText("Favorevole");
    	else
    		voto.setText("Contrario");
    	votoVbox.getChildren().add(voto);
    }
    
    @FXML
    void pressAnnullaButton(ActionEvent event) {
    	Stage stage = (Stage) annullaButton.getScene().getWindow();
		closeStage(stage);
    }

    @FXML
    void pressConfermaButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
        assert confermaButton != null : "fx:id=\"confermaButton\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
        assert votoVbox != null : "fx:id=\"votoVbox\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
    }

}
