package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import com.gbrsni.votoelettronico.controller.DashBoardController;

public class LoginController {
	
	private Stage stage; 
	private Scene scene;
	private Parent root;
	
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
    private ComboBox<String> tipologiaUtente ;

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
    void pressButton(ActionEvent event) throws IOException {
    	
    	//CAMBIARE LA SCENA -> DASHBOARD.fxml
    	/**
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DashBoardView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        DashBoardController controller = (DashBoardController)
        stage.show();
        **/
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DashBoardView.fxml"));
        Parent root = loader.load();
        DashBoardController controller = loader.getController();
        controller.setName("ciao");
        button.getScene().setRoot(root);
    }

    @FXML
    void initialize() {
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert labelPassword != null : "fx:id=\"labelPassword\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert labelUsername != null : "fx:id=\"labelUsername\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert tipologiaUtente != null : "fx:id=\"tipologiaUtente\" was not injected: check your FXML file 'LoginView.fxml'.";
        ObservableList<String> dbTypeList = FXCollections.observableArrayList("Elettore","Impiegato");
        tipologiaUtente.setItems(dbTypeList);
    }

}
