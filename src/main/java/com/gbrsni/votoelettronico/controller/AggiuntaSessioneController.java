package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.gbrsni.votoelettronico.data_access.CandidatoDAOImpl;
import com.gbrsni.votoelettronico.data_access.PartitoDAOImpl;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiReferendumDAOImpl;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.SessioneOrdinale;
import com.gbrsni.votoelettronico.models.SessioneCategorico;
import com.gbrsni.votoelettronico.models.SessioneCategoricoPreferenze;
import com.gbrsni.votoelettronico.models.SessioneReferendum;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class AggiuntaSessioneController extends Controller{
	
	private Gestore gestore;
	private List<Partito> partiti;
	private Map<Partito, List<Candidato>> candidati;
	private List<Partito> partitiSelezionati;
	private List<Candidato> candidatiSelezionati;
	
 	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
	private Button backButton;

    @FXML
    private ScrollPane candidatiScrollPane;

    @FXML
    private VBox candidatiVbox;

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private Label datiMancantiLabel;

    @FXML
    private TextArea descrizioneTextField;

	@FXML
	private ComboBox<ModVittoria> modVittoriaComboBox;

	@FXML
	private ComboBox<ModVoto> modVotoComboBox;

	@FXML
	private Label nomeGestore;

	@FXML
	private TextField nomeTextField;

	@FXML
	private ComboBox<Partito> partitoComboBox;

	@FXML
	private Button saveButton;

	@FXML
	private Label selezionaCandidatiLabel;
	    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getNome() + " " + gestore.getCognome());
    }

    @FXML
    void pressBackButton(ActionEvent event) {
    	navigate("GestoreSessioniView", gestore);
    }
   
    @FXML
    void pressSaveButton(ActionEvent event) throws Exception {
    	
    	datiMancantiLabel.setVisible(false);
    	String datiMancanti = "";
    	if (nomeTextField.getText().equals("")) { datiMancanti += "nome, ";}
    	if (dataDatePicker.getValue() == null ) { System.out.println("ciao");datiMancanti += "data, "; }
    	if (modVotoComboBox.getValue() == null) { datiMancanti += "modalità voto, "; }
    	if (modVittoriaComboBox.getValue() == null) { datiMancanti += "modalità vittoria, "; }
    	if (!datiMancanti.equals("")) {
    		datiMancantiLabel.setText("Dati mancanti : " + datiMancanti);
    		datiMancantiLabel.setVisible(true);
    	} else {
    		GetSessioneFactory sessioneFactory = new GetSessioneFactory();
    		SessioneDiVoto sessione = sessioneFactory.getSessione(modVotoComboBox.getValue(),0,nomeTextField.getText(), descrizioneTextField.getText(), dataDatePicker.getValue(), modVittoriaComboBox.getValue(), StatoSessione.CHIUSA, 0 );
    		SessioneDiVotoDAOImpl sessioneDb = new SessioneDiVotoDAOImpl();
    		int id = sessioneDb.addSessioneDiVoto(sessione);
    		System.out.println("id sessione aggiunta:" + id);
    		sessione.setId(id);
    		
    		for (int i = 0; i < candidatiSelezionati.size(); i++) {
    			if (!partitiSelezionati.contains(candidatiSelezionati.get(i).getPartito()))
    					partitiSelezionati.add(candidatiSelezionati.get(i).getPartito());
    		}
    		
    		if(!sessione.getModVoto().equals(ModVoto.REFERENDUM)) {
    				VotiPartitiDAOImpl partitiDb = new VotiPartitiDAOImpl();
    				VotiCandidatiDAOImpl candidatiDb = new VotiCandidatiDAOImpl();
    				VotiAstenutiDAOImpl astenutiDB = new VotiAstenutiDAOImpl();
    				for (int i = 0; i < partitiSelezionati.size(); i++) {
    					    partitiDb.addVotiPartitoBySessione(sessione,partitiSelezionati.get(i));
        			}
        			for (int i = 0; i < candidatiSelezionati.size(); i++) {
        				candidatiDb.addVotiCandidatoBySessione(sessione,candidatiSelezionati.get(i));
        			}
        			astenutiDB.addVotiAstenutiBySessione(sessione);
    		}else {
    			VotiReferendumDAOImpl referendumDb = new VotiReferendumDAOImpl();
    			referendumDb.addNewVotiSessioneReferendum(sessione);
    		}
    		navigate("GestoreSessioniView", gestore);
    	}	
    	
    }
    
    @FXML
    void selectModVoto(ActionEvent event) {
    	String modVoto = modVotoComboBox.getValue().toString();
    	if(!modVoto.equals("REFERENDUM")){
    		ObservableList<ModVittoria> modVittoriaValues = FXCollections.observableArrayList(Arrays.asList(ModVittoria.values()).subList(0, 2));
            modVittoriaComboBox.setItems(modVittoriaValues);
            candidatiVbox.setVisible(true);
            partitoComboBox.setVisible(true);
            partitoComboBox.setVisible(true);
            candidatiScrollPane.setVisible(true);
            selezionaCandidatiLabel.setPrefWidth(215);
            selezionaCandidatiLabel.setText("Seleziona candidati:");
    	} else {
    		ObservableList<ModVittoria> modVittoriaValues = FXCollections.observableArrayList(Arrays.asList(ModVittoria.values()).subList(2, 4));
    		modVittoriaComboBox.setItems(modVittoriaValues);
            candidatiVbox.setVisible(false);
            partitoComboBox.setVisible(false);
            candidatiScrollPane.setVisible(false);
            selezionaCandidatiLabel.setPrefWidth(1000);
            selezionaCandidatiLabel.setText("Non prevista selezione candidati per questa modalità di voto");
    	}
    }
    
    @FXML
    void selectPartitoComboBox(ActionEvent event) {
    	candidatiVbox.getChildren().clear();
    	List<Candidato> candidatiPartito = new ArrayList<>(candidati.get(partitoComboBox.getValue()));
    	for (int i = 0; i < candidatiPartito.size(); i++) {	
    			HBox candidatiHbox = new HBox();
    			CheckBox candidatiCheckBox = new CheckBox();
    			candidatiCheckBox.setUserData(candidatiPartito.get(i));
    			candidatiCheckBox.setText(candidatiPartito.get(i).getNome() + " " + candidatiPartito.get(i).getCognome());
    			candidatiCheckBox.setFont(new Font(20));
    			candidatiCheckBox.setOnAction(selezionaCandidato);
    			candidatiHbox.getChildren().add(candidatiCheckBox);
    			candidatiVbox.getChildren().add(candidatiHbox);
    	}
    }
    
  //Seleziona CheckBox
    private EventHandler<ActionEvent> selezionaCandidato = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {	
        	Candidato c = (Candidato)((CheckBox)e.getSource()).getUserData();
        	if (((CheckBox)e.getSource()).isSelected()){
                		System.out.println("hai selezionato " + c.getNome() + " " + c.getCognome());
        		candidatiSelezionati.add(c);		
        	} else {
        		System.out.println("hai deselezionato " + c.getNome() + " " + c.getCognome() );
        		candidatiSelezionati.remove(c);
        	}
        	System.out.println(candidatiSelezionati);
        }
    };
    
    private void init() {
    	ObservableList<ModVoto> modVotoValues = FXCollections.observableArrayList(Arrays.asList(ModVoto.values()));
        modVotoComboBox.setItems(modVotoValues);
        
        CandidatoDAOImpl candidatiDb = new CandidatoDAOImpl();
        candidati = candidatiDb.getAllCandidato().stream()
      		   .collect(Collectors.groupingBy(Candidato::getPartito, Collectors.toList()));
        
        partiti = new ArrayList<>();
        partiti.addAll(candidati.keySet());
        ObservableList<Partito> listaPartiti = FXCollections.observableArrayList(partiti);
        partitoComboBox.setItems(listaPartiti);	
        
        candidatiSelezionati = new ArrayList<>();
        partitiSelezionati = new ArrayList<>();
    }
    
	@FXML
    void initialize() {
		assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert candidatiScrollPane != null : "fx:id=\"candidatiScrollPane\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert candidatiVbox != null : "fx:id=\"candidatiVbox\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert dataDatePicker != null : "fx:id=\"dataDatePicker\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert datiMancantiLabel != null : "fx:id=\"datiMancantiLabel\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert descrizioneTextField != null : "fx:id=\"descrizioneTextField\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert modVittoriaComboBox != null : "fx:id=\"modVittoriaComboBox\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert modVotoComboBox != null : "fx:id=\"modVotoComboBox\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert nomeTextField != null : "fx:id=\"nomeTextField\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert partitoComboBox != null : "fx:id=\"partitoComboBox\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        assert selezionaCandidatiLabel != null : "fx:id=\"selezionaCandidatiLabel\" was not injected: check your FXML file 'AggiuntaSessioneView.fxml'.";
        init();          
    }

}

        


