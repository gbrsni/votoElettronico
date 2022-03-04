package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button candidatiBottone;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nomeGestore;

    @FXML
    private Button sessioniBottone;

    @FXML
    void HandleButtonCandidati(ActionEvent event) {

    }

    @FXML
    void handleButtonSessioni(ActionEvent event) {

    }
    	
    @FXML
    void handleLogout(ActionEvent event) {

    }
    
    //IMPOSTARE NOME DEL GESTORE
    public void setName(String name) {
    	nomeGestore.setText(name);
    	
    }
    
    @FXML
    void initialize() {
        assert candidatiBottone != null : "fx:id=\"candidatiBottone\" was not injected: check your FXML file 'DashBoardView.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'DashBoardView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'DashBoardView.fxml'.";
        assert sessioniBottone != null : "fx:id=\"sessioniBottone\" was not injected: check your FXML file 'DashBoardView.fxml'.";

    }
}