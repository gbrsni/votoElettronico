package com.gbrsni.votoelettronico.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.gbrsni.votoelettronico.controller.GestioneListeController;

public class DashBoardController extends Controller{

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
    void HandleButtonCandidati(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GestioneListeView.fxml"));
        Parent root = loader.load();
        GestioneListeController controller = loader.getController();
        controller.setName("ciao");
        candidatiBottone.getScene().setRoot(root);
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