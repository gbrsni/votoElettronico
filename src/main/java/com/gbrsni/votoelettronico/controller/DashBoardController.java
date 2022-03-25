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

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class DashBoardController extends Controller{
	
	private Gestore gestore;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button candidatiBottone;

    @FXML
    private Label candidatiLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nomeGestore;

    @FXML
    private Label partitiLabel;

    @FXML
    private Label sessioniAperteLabel;

    @FXML
    private Button sessioniBottone;

    @FXML
    private Label sessioniChiuseLabel;

    @FXML
    private Label sessioniConcluseLabel;

    @FXML
    private Label sessioniScrutinateLabel;

    @FXML
    private Label sessioniTotaliLabel;


    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML
    void pressLogoutButton(ActionEvent event) {
    	gestore = null; 
    	navigate("LoginView");
    }

    
    @FXML
    void pressCandidatiButton(ActionEvent event) {
    	navigate("GestioneListeView", gestore);
    }

   
    @FXML
    void pressSessioniButton(ActionEvent event) {
    	navigate("GestoreSessioniView", gestore);
    }
   
    
    @FXML
    void initialize() {
    	 assert candidatiBottone != null : "fx:id=\"candidatiBottone\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert candidatiLabel != null : "fx:id=\"candidatiLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert partitiLabel != null : "fx:id=\"partitiLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniAperteLabel != null : "fx:id=\"sessioniAperteLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniBottone != null : "fx:id=\"sessioniBottone\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniChiuseLabel != null : "fx:id=\"sessioniChiuseLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniConcluseLabel != null : "fx:id=\"sessioniConcluseLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniScrutinateLabel != null : "fx:id=\"sessioniScrutinateLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         assert sessioniTotaliLabel != null : "fx:id=\"sessioniTotaliLabel\" was not injected: check your FXML file 'DashBoardView.fxml'.";
         SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
         sessioniTotaliLabel.setText("Sessioni di voto totali: " + sessioneDb.getTotalNumberSessioneDiVoto());
         sessioniChiuseLabel.setText("Sessioni di voto chiuse: " + sessioneDb.getNumberSessioneDiVotoByStato(StatoSessione.CHIUSA));
         sessioniAperteLabel.setText("Sessioni di voto aperte: " + sessioneDb.getNumberSessioneDiVotoByStato(StatoSessione.IN_CORSO));
         sessioniConcluseLabel.setText("Sessioni di voto concluse: " + sessioneDb.getNumberSessioneDiVotoByStato(StatoSessione.CONCLUSA));
         sessioniScrutinateLabel.setText("Sessioni di voto scrutinate: " + sessioneDb.getNumberSessioneDiVotoByStato(StatoSessione.SCRUTINATA));
    }
}