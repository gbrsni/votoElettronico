package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Gestore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SessioneApertaController extends Controller {
	
	private Gestore gestore;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chiudiSessioneBottone;

    @FXML
    private Label dataLabel;

    @FXML
    private Label descrizioneLabel;

    @FXML
    private Button logoutBottone;

    @FXML
    private Button menuButton;

    @FXML
    private Label modVittoriaLabel;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeGestore;

    @FXML
    private Label nomeLabel;

    @FXML
    private Button votazioneBottone;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML
    void pressChiudiSessioneButton(ActionEvent event) {

    }

    @FXML
    void pressLogoutButton(ActionEvent event) {
    	gestore = null;
    	navigate("LoginView");
    }

    @FXML
    void pressMenuButton(ActionEvent event) {
    	navigate("DashBoardView", this.gestore);
    }


    @FXML
    void pressVotazioneButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert chiudiSessioneBottone != null : "fx:id=\"chiudiSessioneBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert dataLabel != null : "fx:id=\"dataLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert descrizioneLabel != null : "fx:id=\"descrizioneLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert modVittoriaLabel != null : "fx:id=\"modVittoriaLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";
        assert votazioneBottone != null : "fx:id=\"votazioneBottone\" was not injected: check your FXML file 'SessioneApertaView.fxml'.";

    }

}
