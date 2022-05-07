package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Timer;
import com.gbrsni.votoelettronico.models.TimerListener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Pair;

public class VotazioneReferendumController extends Controller {
	
	private Elettore elettore;
	private Gestore gestore; 
	private SessioneDiVoto sessione; 
	
	private Timer timer = new Timer(5);
	private Stage stage; 
	
	 @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton favorevoleRadioButton;

    @FXML
    private Button annullaSelezioneButton;

    @FXML
    private RadioButton contratioRadioButton;

    @FXML
    private Label modVotoLabel;

    @FXML
    private Label nomeElettore;

    @FXML
    private Label nomeLabel;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private Button votaButton;
    
    @FXML
    private Label timerLabel;


    @Override
	public void onNavigateFrom(Controller sender, Object parameter) {
		Object[] data = (Object[]) parameter;
		elettore = (Elettore) data[0];
		sessione = (SessioneDiVoto) data[1];
		gestore = (Gestore) data[2];
		nomeElettore.setText("Elettore: " + elettore.getNome() + " " + elettore.getCognome());
		nomeLabel.setText("Sessione:" + sessione.getNome());
		modVotoLabel.setText("Mod. Voto:" + sessione.getModVoto());
		timer.addListener(new TimerListener(){
			@Override
			public void onReandingChange() {
				updateTimer();
			}
			
		});
	}
    
    private void updateTimer() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Pair time = timer.getTimer();
				timerLabel.setText("Timer: " + time.getKey() + ":" + time.getValue());
				if((Integer)time.getKey() == 0 && (Integer)time.getValue() == 0) {
					VotiAstenutiDAOImpl votiAstenutiDb = new VotiAstenutiDAOImpl();
					VotiEspressiDAOImpl votiEspressiDb = new VotiEspressiDAOImpl();
					votiAstenutiDb.increaseVotiAstenutiBySessione(sessione);
					votiEspressiDb.addVotoEspresso(sessione, elettore);
					timer.shutdown();
					if(stage != null) stage.close();
					Object[] parameter = new Object[] {elettore,sessione,gestore};
					navigate("VotoRegistratoView",parameter);
				}
			}
			
		});
	}
    
    @FXML
    void pressAnnullaSelezioneButton(ActionEvent event) {
    	radioGroup.getSelectedToggle().setSelected(false);
    }
    
    @FXML
    void pressVotaButton(ActionEvent event) {
    	Boolean scelta = null;
    	if(favorevoleRadioButton.isSelected()) scelta = true;
    	else if (contratioRadioButton.isSelected()) scelta = false;
    	Object[] parameter = new Object[] {elettore, sessione, gestore, scelta, timer};
		stage = newStage("Conferma Voto", "ConfermaVotazioneReferendumView", parameter);
    }

    @FXML
    void initialize() {
    	assert favorevoleRadioButton != null : "fx:id=\"FavorevoleRadioButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert annullaSelezioneButton != null : "fx:id=\"annullaSelezioneButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert contratioRadioButton != null : "fx:id=\"contratioRadioButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert modVotoLabel != null : "fx:id=\"modVotoLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeElettore != null : "fx:id=\"nomeElettore\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert nomeLabel != null : "fx:id=\"nomeLabel\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert radioGroup != null : "fx:id=\"radioGroup\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert votaButton != null : "fx:id=\"votaButton\" was not injected: check your FXML file 'VotazioneReferendumView.fxml'.";
        assert timerLabel != null : "fx:id=\"timerLabel\" was not injected: check your FXML file 'VotazioneCategoricoPreferenzeView.fxml'.";

        
    }

}
