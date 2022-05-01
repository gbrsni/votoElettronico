package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.GestoreDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class UscitaVotazioneController extends Controller{
	
	private Gestore gestore; 
	private SessioneDiVoto sessione;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label erroreLabel;
    
    @FXML
    private Button inviaButton;

    @FXML
    private PasswordField passwordTextField;
    
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] dati = (Object[]) parameter;
        gestore = (Gestore) dati[0];
        sessione = (SessioneDiVoto) dati[1];
    
    }
    
    
    @FXML
    void pressInviaButton(ActionEvent event) {
    	GestoreDAOImpl gestoreDb = new GestoreDAOImpl();
		erroreLabel.setVisible(false);
    	SaltedPassword password = gestoreDb.getPasswordGestoreByUsername(gestore.getUsername());
    	if (password.checkPassword(passwordTextField.getText().trim())) {
    		Object[] parameter = new Object[] {gestore, sessione};
    		Stage stage = (Stage) inviaButton.getScene().getWindow();
            closeStage(stage);
    		navigate("SessioneApertaView",parameter);
    	} else {
    		erroreLabel.setText("Password non Corretta");
    		erroreLabel.setVisible(true);
    	}
    }

    @FXML
    void initialize() {
    	assert erroreLabel != null : "fx:id=\"erroreLabel\" was not injected: check your FXML file 'UscitaVotazioneView.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'UscitaVotazioneView.fxml'.";	
        
    }

}
