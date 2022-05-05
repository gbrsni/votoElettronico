package com.gbrsni.votoelettronico.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiReferendumDAOImpl;
import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class GestoreSessioniController  <A> extends Controller{
	
	private Gestore gestore ;
	private List<SessioneDiVoto> sessioni;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aggiungiSessioneButton;

    @FXML
    private Button cercaButton;

    @FXML
    private TextField cercaSessioneTextField;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label nomeGestore;

    @FXML
    private VBox sessioniVbox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML
    void pressAggiungiSessioneButton(ActionEvent event) {
    	navigate("AggiuntaSessioneView", gestore);
    }
   

    @FXML
    void pressCercaButton(ActionEvent event) {
    	String cerca = cercaSessioneTextField.getText();
    	if (cerca.equals("")) init(sessioni);
    	else {
    		List<SessioneDiVoto> sessioniCerca = new ArrayList<>();
	    	for (int i = 0 ; i < sessioni.size(); i++) {
	    		if (sessioni.get(i).getNome().toLowerCase().contains(cerca.toLowerCase())){
	    			sessioniCerca.add(sessioni.get(i));
	    		}
	    	}
        	init(sessioniCerca);
    	}
    }

    @FXML
    void pressLogoutButton(ActionEvent event) {
		Logging.infoMessage(this.getClass(), "Eseguito il logout dal gestore " + gestore);
    	gestore = null;
    	navigate("LoginView");
    }

    @FXML
    void pressMenuButton(ActionEvent event) {
    	navigate("DashBoardView", gestore);
    }

    
    
    // Avvia sessione chiusa
    private EventHandler<ActionEvent> pressAvviaSessioneButton = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};
            newStage("Avvio Sessione Di Voto", "AvvioSessioneView", parameter);
           
        }
    };
    
    // Modifica Sessione Chiusa
    private EventHandler<ActionEvent> pressModificaSessioneButton = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {            
        	SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};  
            navigate("ModificaSessioneView", parameter);
        } 
    };
    
    // Elimina sessione chiusa
    private EventHandler<ActionEvent> pressEliminaSessioneButton = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};
            newStage("Elimina Sessione Di Voto", "EliminaSessioneView", parameter);
        }
    };
    
    // Gestisci sessione in corso
    private EventHandler<ActionEvent> pressGestisciSessioneButton = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};  
            navigate("SessioneApertaView", parameter);
        }
    };
    
    //Avvia Scrutinio Sessione Conclusa
    private EventHandler<ActionEvent> pressScrutinioSessioneButton = new EventHandler<ActionEvent>() {
        public void   handle (ActionEvent e)
        {	
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            if(!(s instanceof com.gbrsni.votoelettronico.models.SessioneReferendum)) {
            	VotiCandidatiDAOImpl votiCandidatiDb = new VotiCandidatiDAOImpl();
                Map<Candidato,Integer> candidati = votiCandidatiDb.getVotiCandidatiBySessione(s);
            	Map<Candidato,Integer> vincitoreCandidati = s.calcolaVincitore(candidati);

            	//CONSIDERO ANCHE I VOTI DEI PARTITI
            	if(vincitoreCandidati.size() != 1) {            	
            		VotiPartitiDAOImpl votiPartitiDb = new VotiPartitiDAOImpl();
            		Map<Partito,Integer> partiti = votiPartitiDb.getVotiPartitiBySessione(s);
            		for (Entry<Candidato, Integer> entry : vincitoreCandidati.entrySet()) {
            		   vincitoreCandidati.replace(entry.getKey(), entry.getValue()  + partiti.get(entry.getKey().getPartito()));
            		}
            		vincitoreCandidati = s.calcolaVincitore(vincitoreCandidati);
            	} 
            	if(vincitoreCandidati.size() == 1) {
            		//UN SOLO VINCITORE --> CARICO IL VINCITORE NEL DB
            	} else {
            		//DUE VINCITORI --> CARICO NULL NEL DATABASE
            	}
           
            }else {
            	//CASO REFERENDUM --> COMPLETA
            	VotiReferendumDAOImpl votiReferendumDb = new VotiReferendumDAOImpl();
            	Map<OpzioneReferendum,Integer> voti= new HashMap<>();;
            	voti.put(OpzioneReferendum.favorevole,votiReferendumDb.getNVotiBySessioneOpzione(s, OpzioneReferendum.favorevole));
            	voti.put(OpzioneReferendum.contrario,votiReferendumDb.getNVotiBySessioneOpzione(s, OpzioneReferendum.contrario));
            	for (Entry<OpzioneReferendum, Integer> entry : voti.entrySet()) {
          		   System.out.println("REFERENDUM" + entry.getKey() + " " + entry.getValue());
            	}
            	Map<OpzioneReferendum,Integer> vincitoreReferendum = s.calcolaVincitore(voti);
            	OpzioneReferendum vincitore = null;
            	if (vincitoreReferendum.size() == 1) {
            		for (Entry<OpzioneReferendum, Integer> entry : vincitoreReferendum.entrySet()) {
             		   vincitore = entry.getKey();
             		}
            	}
            	votiReferendumDb.setVincitoreReferendum(s, vincitore);
            }
            
            setStatoScrutinata(s);
        }
    };
    
    
    public void setStatoScrutinata(SessioneDiVoto s) {
        s.setStatoSessione(StatoSessione.SCRUTINATA);
        SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        sessioniDb.updateSessioneDiVoto(s);
    }
    
    
    //Visualizza Risultati sessione Scrutinata
    private EventHandler<ActionEvent> pressVisualizzaRisultatiSessioneButton = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
        	SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {s,gestore};  
            navigate("RisultatiSessioneView", parameter);
        }
    };
    
    
    private void init() {
   	 	SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        sessioni = sessioniDb.getAllSessioneDiVoto();
        init(sessioni);
    }
    
    private void init(List<SessioneDiVoto> sessioni) {
        sessioniVbox.getChildren().clear();
        //sessioniVbox.setPrefWidth(950);
        for (int i = 0; i < sessioni.size(); i++) {
        		HBox sessioniHbox = new HBox();
        		Label sessioniLabel = new Label();
        		Label sessioniLabelInfo = new Label();
        		Region region1 = new Region();
        		Region region2 = new Region();
        		
                sessioniLabel.setText(sessioni.get(i).getNome());
                sessioniLabelInfo.setText(" data : " + sessioni.get(i).getData() + "   modVoto: " + sessioni.get(i).getModVoto() + "  stato:" + sessioni.get(i).getStatoSessione());
                sessioniLabel.setFont(new Font(25));
                sessioniLabelInfo.setFont(new Font(15)); 
                sessioniHbox.setAlignment(Pos.CENTER_LEFT);
                
            	sessioniHbox.setHgrow(region1, Priority.ALWAYS);
            	sessioniHbox.setHgrow(region2, Priority.ALWAYS);
            	sessioniHbox.getChildren().addAll(sessioniLabel,region1,sessioniLabelInfo,region2);
                
            	switch(sessioni.get(i).getStatoSessione()) {
            	case CHIUSA:
                	Button bottoneAvvia = new Button("Avvia");
                	Button bottoneModifica = new Button("Modifica");
                	Button bottoneElimina = new Button("Elimina");
                	bottoneAvvia.setUserData(sessioni.get(i));
                	bottoneModifica.setUserData(sessioni.get(i));
                	bottoneElimina.setUserData(sessioni.get(i));
                	bottoneAvvia.setFont(new Font(15));
                	bottoneModifica.setFont(new Font(15));
                	bottoneElimina.setFont(new Font(15));
                	bottoneAvvia.setOnAction(pressAvviaSessioneButton);
                	bottoneModifica.setOnAction(pressModificaSessioneButton);
                	bottoneElimina.setOnAction(pressEliminaSessioneButton);
                	sessioniHbox.getChildren().addAll(bottoneAvvia,bottoneModifica,bottoneElimina);
                break;
            	case IN_CORSO:	
                	Button bottoneGestisci = new Button("Gestisci");
                	bottoneGestisci.setOnAction(pressGestisciSessioneButton);
                	bottoneGestisci.setUserData(sessioni.get(i));
                	bottoneGestisci.setFont(new Font(15));
                	sessioniHbox.getChildren().add(bottoneGestisci);
                break;
            	case CONCLUSA:
                	Button bottoneScrutinio = new Button("Avvia Scrutinio");
                	bottoneScrutinio.setOnAction(pressScrutinioSessioneButton);
                	bottoneScrutinio.setUserData(sessioni.get(i));
                	bottoneScrutinio.setFont(new Font(15));
                	sessioniHbox.getChildren().add(bottoneScrutinio);
                break;
            	case SCRUTINATA:
                	Button bottoneRisultati = new Button("Visualizza Risultati");
                	bottoneRisultati.setOnAction(pressVisualizzaRisultatiSessioneButton);
                	bottoneRisultati.setUserData(sessioni.get(i));
                	bottoneRisultati.setFont(new Font(15));
                	sessioniHbox.getChildren().add(bottoneRisultati);
                	break;
                }
                sessioniVbox.getChildren().add(sessioniHbox);	
        	}
        }
     
    @FXML
    void initialize() {
    	assert aggiungiSessioneButton != null : "fx:id=\"aggiungiSessioneButton\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert cercaButton != null : "fx:id=\"cercaButton\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert cercaSessioneTextField != null : "fx:id=\"cercaSessioneTextField\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        assert sessioniVbox != null : "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
        init();
    }
  }


