package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AvvioSessioneController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button noBottone;

    @FXML
    private Button siBottone;
    
    public void onNavigateFrom(Controller sender, Object parameter) {}
    
    @FXML
    void pressNoButton(ActionEvent event) {

    }

    @FXML
    void pressSiButton(ActionEvent event) {

    }
    
    @FXML
    void initialize() {
        assert noBottone != null : "fx:id=\"noBottone\" was not injected: check your FXML file 'AvvioSessioneView.fxml'.";
        assert siBottone != null : "fx:id=\"siBottone\" was not injected: check your FXML file 'AvvioSessioneView.fxml'.";
    }

}
