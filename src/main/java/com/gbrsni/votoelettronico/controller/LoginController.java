package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import com.gbrsni.votoelettronico.data_access.GestoreDAOImpl;
import com.gbrsni.votoelettronico.data_access.ElettoreDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;
import com.gbrsni.votoelettronico.models.Utente;

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
    private PasswordField passwordTextField;

    @FXML
    private Button sessioniButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ComboBox<String> utenteComboBox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {}
    
    @FXML
    void pressLoginButton(ActionEvent event) throws IOException {
    	erroreLabel.setVisible(false);
    	String tipoutente = utenteComboBox.getValue();
    	String password = passwordTextField.getText().trim();
    	String username = usernameTextField.getText().trim();
    	if (tipoutente == null || username.equals("") || password.equals("")) {
    		erroreLabel.setText("Inserisci dati mancanti");
    		erroreLabel.setVisible(true);
    	} else {
    		switch (tipoutente) {
    			case "Gestore":
    				GestoreDAOImpl gestoreDb = new GestoreDAOImpl();
    				SaltedPassword passwordGestore = gestoreDb.getPasswordGestoreByUsername(username);
    				if(passwordGestore != null && passwordGestore.checkPassword(password) ) {
    					Gestore gestore = gestoreDb.getGestoreByUsername(username);
    					navigate("DashBoardView", gestore);
    				} else {
    					erroreLabel.setText("Dati inseriti non corretti");
    					erroreLabel.setVisible(true);
    				}
    				break; 
    			case "Elettore":
    				ElettoreDAOImpl elettoreDb = new ElettoreDAOImpl();
    				SaltedPassword passwordElettore = elettoreDb.getPasswordElettoreByUsername(username);
    				if(passwordElettore != null && passwordElettore.checkPassword(password)) {
    					Elettore elettore = elettoreDb.getElettoreByUsername(username);
    					navigate("ElettoreSessioniView", elettore);
    				} else {
    					erroreLabel.setText("Dati inseriti non corretti");
    					erroreLabel.setVisible(true);
    				}
    				break;
    		}
    	}	
    }

    @FXML
    void pressSessioniButton(ActionEvent event) {
    	navigate("UtenteSessioniScrutinateView");
    }
    
    private void init() {
    	 ObservableList<String> listComboBox = FXCollections.observableArrayList("Elettore","Gestore");
         utenteComboBox.setItems(listComboBox);
    }

    @FXML
    void initialize() {
        assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert sessioniButton != null : "fx:id=\"sessioniButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'LoginView.fxml'.";        
        assert utenteComboBox != null : "fx:id=\"utenteTextField\" was not injected: check your FXML file 'LoginView.fxml'.";
    }

}
