package com.gbrsni.votoelettronico.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfigurazioneSessioneController extends Controller{
	
	private Gestore gestore;
	private SessioneDiVoto sessione; 
	private List<Partito> partiti;
	private List<Candidato> candidati;
	private List<Partito> partitiSelezionati;
	private List<Candidato> candidatiSelezionati;
	
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private Button backBottone;

	    @FXML
	    private VBox candidatiVbox;

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
	    private ComboBox<String> partitoComboBox;

	    @FXML
	    private Button saveBottone;
    
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	
    	try {
	    	Object[] dati = (Object[]) parameter;
	        gestore = (Gestore) dati[0];
	        nomeGestore.setText(gestore.getNome());
	        sessione = (SessioneDiVoto) dati[1];
	        nomeTextField.setText(sessione.getNome());
	        dataDatePicker.setValue(sessione.getData());
	        descrizioneTextField.setText(sessione.getDescrizione());
	        modVotoComboBox.setValue(sessione.getModVoto().toString());
	        modVittoriaComboBox.setValue(sessione.getModVittoria().toString());
        	//IMPOSTARE CANDIDATI SELEZIONATI
	        //candidatiSelezionati = new ArrayList<>();
	        
    	} catch(Exception e) {
    		gestore = (Gestore) parameter;
    		nomeGestore.setText(gestore.getNome());
    		candidatiSelezionati = new ArrayList<>();
    		partitiSelezionati = new ArrayList<>();
    	}
    	
    	
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
    		int id = sessionedb.addSessioneDiVoto(sessione);
    		System.out.println("id inserito:" + id);
    		sessione.setId(id);
    		
    		for (int i = 0; i < candidatiSelezionati.size(); i++) {
    			if (!partitiSelezionati.contains(candidati.get(i).getPartito()))
    					partitiSelezionati.add(candidatiSelezionati.get(i).getPartito());
    		}

    		if (!modVotoComboBox.getValue().equals("REFERENDUM")) {
    			sessionedb.addVotiPartiti(sessione, partitiSelezionati );
    			sessionedb.addVotiCandidati(sessione, candidatiSelezionati);
    			sessionedb.addVotiAstenuti(sessione);
    		}
    	
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
    void selectPartitoComboBox(ActionEvent event) {
    	candidatiVbox.getChildren().clear();
    	for (int i = 0; i < candidati.size(); i++) {
    		if (candidati.get(i).getPartito().getNome().equals(partitoComboBox.getValue())){
    			HBox candidatiHbox = new HBox();
    			CheckBox candidatiCheckBox = new CheckBox();
    			candidatiCheckBox.setUserData(candidati.get(i));
    			//candidatiCheckBox.setId(candidati.get(i).getId() + ";" + candidati.get(i).getPartito().getId());
    			candidatiCheckBox.setText(candidati.get(i).getNome() + " " + candidati.get(i).getCognome());
    			candidatiCheckBox.setFont(new Font(20));
    			candidatiCheckBox.setOnAction(selezionaCandidati);
    			candidatiHbox.getChildren().add(candidatiCheckBox);
    			candidatiVbox.getChildren().add(candidatiHbox);
    			
    		}
    		
    	}
    	
    }
    
  //Seleziona CheckBox
    private EventHandler<ActionEvent> selezionaCandidati = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {	
        	Candidato c = (Candidato)((CheckBox)e.getSource()).getUserData();
        	if (((CheckBox)e.getSource()).isSelected()){
        		
        		System.out.println("hai selezionato " + c.getNome());
        		candidatiSelezionati.add(c);
        		
        	} else {
        		
        		System.out.println("hai deselezionato " + c);
        		candidatiSelezionati.remove(c);
        	}
        	System.out.println(candidatiSelezionati);
        }
    };

	@FXML
    void initialize() {
    	assert backBottone != null : "fx:id=\"backBottone\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert dataDatePicker != null : "fx:id=\"dataDatePicker\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert datiMancantiLabel != null : "fx:id=\"datiMancantiLabel\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert descrizioneTextField != null : "fx:id=\"descrizioneTextField\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert modVittoriaComboBox != null : "fx:id=\"modVittoriaComboBox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert modVotoComboBox != null : "fx:id=\"modVotoComboBox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert nomeTextField != null : "fx:id=\"nomeTextField\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert partitoComboBox != null : "fx:id=\"partitoComboBox\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
        assert saveBottone != null : "fx:id=\"saveBottone\" was not injected: check your FXML file 'ConfigurazioneSessioneView.fxml'.";
          ObservableList<String> dbTypeList = FXCollections.observableArrayList("ORDINALE","CATEGORICO","CATEGORICO_CON_PREFERENZE", "REFERENDUM");
          modVotoComboBox.setItems(dbTypeList);
          PartitoDAOImpl partitoDb = new PartitoDAOImpl();
          partiti = partitoDb.getAllPartito();
          List<String> partitiNome = new ArrayList<>();
          for (int i = 0; i < partiti.size(); i++) {
        	  partitiNome.add(partiti.get(i).getNome());
          }
          ObservableList<String> dbTypeList1 = FXCollections.observableArrayList(partitiNome);
          partitoComboBox.setItems(dbTypeList1);
          CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
          candidati = candidatiDb.getAllCandidato();
        
    }

}

        


