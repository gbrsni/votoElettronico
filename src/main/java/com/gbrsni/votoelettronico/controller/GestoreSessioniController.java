package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GestoreSessioniController extends Controller{
	
	private Gestore gestore ; //new Gestore("marcox", "marcox","marcox","marcox"); //DA ELIMINARE
	
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
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    
    @FXML
    void handleAggiungiSessione(ActionEvent event) {
    	navigate("ConfigurazioneSessioneView", gestore);
    }

    @FXML
    void handleCerca(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {
    	gestore = null;
    	navigate("LoginView");
    }

    @FXML
    void handleMenu(ActionEvent event) {
    	navigate("DashBoardView", this.gestore);
    }

    @FXML
    void handleNomeSessione(ActionEvent event) {
    	
    }
    
    @FXML
    private VBox sessioniVbox = new VBox();
    
    
    @FXML
    void initialize() {
        assert Sessionebottone != null : "fx:id=\"Sessionebottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert cercaBottone != null : "fx:id=\"cercaBottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'SessioniView.fxml'.";
        assert nomeSessioneText != null : "fx:id=\"nomeSessioneText\" was not injected: check your FXML file 'SessioniView.fxml'.";
        SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        List<SessioneDiVoto> sessioni = new ArrayList<>();
        sessioni = sessioniDb.getAllSessioneDiVoto();
        
        for (int i = 0; i < sessioni.size(); i++) {
        		
        		HBox sessioniHbox = new HBox();
                Label sessioniLabel = new Label();
                sessioniLabel.setText(sessioni.get(i).getNome());
                sessioniLabel.setFont(new Font(18));
                sessioniHbox.getChildren().add(sessioniLabel);
                sessioniVbox.getChildren().add(sessioniHbox);
            
        	}
        }
    }


