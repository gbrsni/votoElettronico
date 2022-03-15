package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConfigurazioneSessioneController extends Controller{
	
	private Gestore gestore;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBottone;

    @FXML
    private DatePicker dataDatePicker;
    
    @FXML
    private Label datiMancantiLabel;
    
    @FXML
    private TextArea descrizioneTextField;

    @FXML
    private ComboBox<String> modVittoriaComboBox;

    @FXML
    private ComboBox<String> modVotoComboBox;

    @FXML
    private Label nomeGestore;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button pressScaltaCandidatiButton;

    @FXML
    private Button saveBottone;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    
    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestoreSessioniView", gestore);
    }

    @FXML
    void pressSaveButton(ActionEvent event) {
    	datiMancantiLabel.setVisible(false);
    	
    	String datiMancanti = "";
    	
    	if (nomeTextField.getText().equals("")) { datiMancanti += "nome, ";}
    	if (dataDatePicker.getValue() == null ) { System.out.println("ciao");datiMancanti += "data, "; }
    	if (modVittoriaComboBox.getValue() == null) { datiMancanti += "modalità voto, "; }
    	if (modVittoriaComboBox.getValue() == null) { datiMancanti += "modalità vittoria, "; }
    	if (!datiMancanti.equals("")) {
    		datiMancantiLabel.setText("Dati mancanti : " + datiMancanti);
    		datiMancantiLabel.setVisible(true);
    	} else {
    		SessioneDiVoto sessione = new SessioneDiVoto(0,nomeTextField.getText(),descrizioneTextField.getText(),dataDatePicker.getValue(), modVotoComboBox.getValue(), modVittoriaComboBox.getValue(), "CHIUSA", 0);
    		SessioneDiVotoDAOImpl sessionedb = new SessioneDiVotoDAOImpl();
    		sessionedb.addSessioneDiVoto(sessione);
    		navigate("GestoreSessioniView", gestore);
    	}	
    }
    
    @FXML
    void selectModVoto(ActionEvent event) {
    	String modVoto = modVotoComboBox.getValue();
    	ObservableList<String> list = FXCollections.observableArrayList("");
    	modVittoriaComboBox.setItems(list);
    	if(!modVoto.equals("REFERENDUM")){
    		ObservableList<String> list1 = FXCollections.observableArrayList("MAGGIORANZA", "MAGGIORANZA_ASSOLUTA");
            modVittoriaComboBox.setItems(list1);
    	} else {
    		ObservableList<String> list1 = FXCollections.observableArrayList("REFERENDUM_CON_QUORUM", "REFERENDUM_SENZA_QUORUM");
            modVittoriaComboBox.setItems(list1);
    	}
    	
    }
    
    @FXML
    void initialize() {
        assert backBottone != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert dataDatePicker != null : "fx:id=\"dataDatePicker\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert descrizioneTextField != null : "fx:id=\"descrizioneTextField\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert modVittoriaComboBox != null : "fx:id=\"modVIttoriaComboBox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert modVotoComboBox != null : "fx:id=\"modVotoComboBox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert nomeTextField != null : "fx:id=\"nomeTextField\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert pressScaltaCandidatiButton != null : "fx:id=\"pressScaltaCandidatiButton\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert saveBottone != null : "fx:id=\"saveBottone\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        ObservableList<String> dbTypeList = FXCollections.observableArrayList("ORDINALE","CATEGORICO","CATEGORICO_CON_PREFERENZE", "REFERENDUM");
        modVotoComboBox.setItems(dbTypeList);
        

    }

}

        


