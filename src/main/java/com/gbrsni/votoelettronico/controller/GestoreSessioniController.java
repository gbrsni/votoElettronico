package com.gbrsni.votoelettronico.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;
import com.gbrsni.votoelettronico.Home;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestoreSessioniController extends Controller{
	
	private Gestore gestore ;//= new Gestore("marcox", "marcox","marcox","marcox"); //DA ELIMINARE
	private List<SessioneDiVoto> sessioni;
	
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
    private TextField nomeSessioneTextField;

    @FXML
    private VBox sessioniVbox;
    
    public void onNavigateFrom(Controller sender, Object parameter) {
    	this.gestore = (Gestore) parameter;
    	nomeGestore.setText(gestore.getUsername());
    }
    
    @FXML
    void pressAggiungiSessioneButton(ActionEvent event) {
    	navigate("ConfigurazioneSessioneView", gestore);
    }
   

    @FXML
    void pressCercaButton(ActionEvent event) {
    	
    	String cerca = nomeSessioneTextField.getText();
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
    	gestore = null;
    	navigate("LoginView");
    }

    @FXML
    void pressMenuButton(ActionEvent event) {
    	navigate("DashBoardView", this.gestore);
    }

    
    
    // Avvia sessione chiusa
    private EventHandler<ActionEvent> avviaSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};
            newStage("Avvio Sessione Di Voto", "AvvioSessioneView", parameter);
           
        }
    };
    
    // Modifica Sessione Chiusa
    private EventHandler<ActionEvent> modificaSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((SessioneDiVoto)((Button)e.getSource()).getUserData()).getId());
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};  
            navigate("ConfigurazioneSessioneView", parameter);
          
        }
       
    };
    
    // Elimina sessione chiusa
    private EventHandler<ActionEvent> eliminaSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
        }
    };
    
    // Gestisci sessione in corso
    private EventHandler<ActionEvent> gestisciSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
            SessioneDiVoto s = (SessioneDiVoto)((Button)e.getSource()).getUserData();
            Object[] parameter = new Object[] {gestore,s};  
            navigate("SessioneApertaView", parameter);
        }
    };
    
    //Avvia Scrutinio Sessione Conclusa
    private EventHandler<ActionEvent> scrutinioSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
        }
    };
    
    //Visualizza Risultati sessione Scrutinata
    private EventHandler<ActionEvent> visualizzaRisultatiSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
        }
    };
    
    private void init(List<SessioneDiVoto> sessioni) {
    	
        sessioniVbox.getChildren().clear();
        sessioniVbox.setPrefWidth(950);
        for (int i = 0; i < sessioni.size(); i++) {
        		
        		HBox sessioniHbox = new HBox();
        		Label sessioniLabel = new Label();
        		Label sessioniLabelInfo = new Label();
        		Region region1 = new Region();
        		Region region2 = new Region();
        		
                sessioniLabel.setText(sessioni.get(i).getNome());
                sessioniLabelInfo.setText("   data : " + sessioni.get(i).getData() + "   modVoto: " + sessioni.get(i).getModVoto() + "  stato:" + sessioni.get(i).getStatoSessione());
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
                	bottoneAvvia.setOnAction(avviaSessione);
                	bottoneModifica.setOnAction(modificaSessione);
                	bottoneElimina.setOnAction(eliminaSessione);
                	sessioniHbox.getChildren().addAll(bottoneAvvia,bottoneModifica,bottoneElimina);
                break;
            	case IN_CORSO:	
                	Button bottoneAperta = new Button("Gestisci");
                	bottoneAperta.setOnAction(gestisciSessione);
                	bottoneAperta.setUserData(sessioni.get(i));
                	bottoneAperta.setFont(new Font(15));
                	sessioniHbox.getChildren().add(bottoneAperta);
                break;
            	case CONCLUSA:
                	Button bottoneScrutinio = new Button("Avvia Scrutinio");
                	bottoneScrutinio.setUserData(sessioni.get(i));
                	bottoneScrutinio.setFont(new Font(15));
                	sessioniHbox.getChildren().add(bottoneScrutinio);
            	case SCRUTINATA:
                	Button bottoneRisultati = new Button("Visualizza Risultati");
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
    	 assert Sessionebottone != null : "fx:id=\"Sessionebottone\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert cercaBottone != null : "fx:id=\"cercaBottone\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert logoutBottone != null : "fx:id=\"logoutBottone\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert nomeGestore != null : "fx:id=\"nomeGestore\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert nomeSessioneTextField != null : "fx:id=\"nomeSessioneTextField\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         assert sessioniVbox != null : "fx:id=\"sessioniVbox\" was not injected: check your FXML file 'GestoreSessioniView.fxml'.";
         SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
         sessioni = sessioniDb.getAllSessioneDiVoto();
         init(sessioni);
    }
  }


