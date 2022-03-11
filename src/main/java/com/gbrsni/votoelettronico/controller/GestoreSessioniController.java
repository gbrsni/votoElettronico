package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GestoreSessioniController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Sessionebottone;

    @FXML
    private Button cercaBottone;

    @FXML
    private Button logoutBottone;

    @FXML
    private Button menuButton;

    @FXML
    private Label nomeGestore;

    @FXML
    private TextField nomeSessioneText;

    @FXML
    void handleAggiungiSessione(ActionEvent event) {

    }

    @FXML
    void handleCerca(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void handleMenu(ActionEvent event) {

    }

    @FXML
    void handleNomeSessione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert Sessionebottone != null : "fx:id=\"Sessionebottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert cercaBottone != null : "fx:id=\"cercaBottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert nomeSessioneText != null : "fx:id=\"nomeSessioneText\" was not injected: check your FXML file 'SessioniView.fxml'.";

    }

}
