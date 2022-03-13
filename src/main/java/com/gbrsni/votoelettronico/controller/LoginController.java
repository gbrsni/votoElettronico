package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;


import com.gbrsni.votoelettronico.data_access.GestoreDAOImpl;

public class LoginController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label erroreLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordLabel;

    @FXML
    private Button sessioniButton;

    @FXML
    private TextField usernameLabel;

    @FXML
    private ComboBox<String> utenteComboBox;

    @FXML
    void pressLoginButton(ActionEvent event) throws IOException {
    	GestoreDAOImpl gestoreDb = new GestoreDAOImpl();
    	List<Gestore> lista = gestoreDb.getAllGestore();
    }

    @FXML
    void pressSessioniButton(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert passwordLabel != null : "fx:id=\"passwordLabel\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert sessioniButton != null : "fx:id=\"sessioniButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert utenteComboBox != null : "fx:id=\"utenteTextField\" was not injected: check your FXML file 'LoginView.fxml'.";
        ObservableList<String> dbTypeList = FXCollections.observableArrayList("Elettore","Impiegato");
        utenteComboBox.setItems(dbTypeList);
    }

}
