package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfermaVotazioneController extends Controller{

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
        assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert confermaButton != null : "fx:id=\"confermaButton\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";
        assert votoVbox != null : "fx:id=\"votoVbox\" was not injected: check your FXML file 'ConfermaVotazioneView.fxml'.";

    }

}

