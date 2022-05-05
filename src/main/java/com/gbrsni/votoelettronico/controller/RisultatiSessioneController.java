package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RisultatiSessioneController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private VBox candidatiVbox;

    @FXML
    private Label dataLabel;

    @FXML
    private Label descrizioneLabel;

    @FXML
    private Label modVIttoriaLabel;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private VBox partitiVbox;

    @FXML
    private Label vincitoreLabel;

    @FXML
    void pressBackButton(ActionEvent event) {

    }
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
	}
    
    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert dataLabel != null : "fx:id=\"dataLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert descrizioneLabel != null : "fx:id=\"descrizioneLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert modVIttoriaLabel != null : "fx:id=\"modVIttoriaLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert partitiVbox != null : "fx:id=\"partitiVbox\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";
        assert vincitoreLabel != null : "fx:id=\"vincitoreLabel\" was not injected: check your FXML file 'RisultatiSessioneView.fxml'.";

    }

	

}
