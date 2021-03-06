package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiReferendumDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfermaVotazioneReferendumController extends ControllerElettoreGestore{
	
	private SessioneDiVoto sessione;
	private Boolean scelta; 
	private Timer timer;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private VBox votoVbox;
    
    
    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
    	Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];	
		scelta = (Boolean) data[3];
		timer = (Timer) data[4];
		init();
	}
    
    private void init() {
    	Label voto = new Label();
    	voto.setFont(new Font(34));
    	votoVbox.setAlignment(Pos.CENTER_LEFT);
    	if(scelta == null)
    		voto.setText("Scheda Bianca");
    	else if(scelta)
    		voto.setText("Favorevole");
    	else
    		voto.setText("Contrario");
    	votoVbox.getChildren().add(voto);
    }
    
    @FXML
    void pressAnnullaButton(ActionEvent event) {
    	Stage stage = (Stage) annullaButton.getScene().getWindow();
		closeStage(stage);
    }

    @FXML
    void pressConfermaButton(ActionEvent event) {

    	VotiReferendumDAOImpl votiReferendumDb = new VotiReferendumDAOImpl();
    	VotiAstenutiDAOImpl votiAstenutiDb = new VotiAstenutiDAOImpl();
    	VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
    	
    	if (scelta == null) {
    		votiAstenutiDb.increaseVotiAstenutiBySessione(sessione);
    	} else {
    		if (scelta == true)
    			votiReferendumDb.increseVotiBySessioneOpzione(sessione, OpzioneReferendum.favorevole,1);
    		else
    			votiReferendumDb.increseVotiBySessioneOpzione(sessione, OpzioneReferendum.contrario,1);
    	}
    		
    	votiEspressiDb.addVotoEspresso(sessione, elettore);
    	
    	Stage stage = (Stage) confermaButton.getScene().getWindow();
        stage.close();
        timer.shutdown();
        Object[] parameter = new Object[] {elettore, sessione, gestore};
        navigate("VotoRegistratoView",parameter);
    }

    @FXML
    void initialize() {
        assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
        assert confermaButton != null : "fx:id=\"confermaButton\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
        assert votoVbox != null : "fx:id=\"votoVbox\" was not injected: check your FXML file 'ConfermaVotazioneReferendumView.fxml'.";
    }

}
