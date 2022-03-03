package com.gbrsni.votoelettronico.controller;

package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private PasswordField labelPassword;

    @FXML
    private TextField labelUsername;

    @FXML
    private ComboBox<?> tipologiaUtente;

    @FXML
    void handleName(ActionEvent event) {

    }

    @FXML
    void handlePassword(ActionEvent event) {

    }

    @FXML
    void handleSceltaUtente(ActionEvent event) {

    }

    @FXML
    void pressButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert labelPassword != null : "fx:id=\"labelPassword\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert labelUsername != null : "fx:id=\"labelUsername\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tipologiaUtente != null : "fx:id=\"tipologiaUtente\" was not injected: check your FXML file 'LoginView.fxml'.";

    }

}
