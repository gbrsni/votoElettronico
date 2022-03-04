package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GestioneListeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logoutBottone;

    @FXML
    private Label nomeGestore;

    @FXML
    void handleAggiungiCandidato(ActionEvent event) {

    }

    @FXML
    void handleAggiungiPartito(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'GestioneListeView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestioneListeView.fxml'.";

    }

}
