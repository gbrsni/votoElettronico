package com.gbrsni.votoelettronico.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GestoreSessioniController extends Controller{
	
	private Gestore gestore ; //new Gestore("marcox", "marcox","marcox","marcox"); //DA ELIMINARE
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
        SessioneDiVotoDAOImpl sessioniDb = new SessioneDiVotoDAOImpl();
        if (nomeSessioneTextField.getText().equals("")) sessioni = sessioniDb.getAllSessioneDiVoto();
        else sessioni = sessioniDb.getSessioneDiVotoByName(nomeSessioneTextField.getText());
        init();
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
        }
    };
    
    // Modifica Sessione Chiusa
    private EventHandler<ActionEvent> modificaSessione = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            System.out.println("Id Sessione di Voto:" + ((Button)e.getSource()).getId());
            SessioneDiVoto s = null; 
            for (int i = 0; i < sessioni.size(); i++) {
            	if (sessioni.get(i).getId() == Integer.valueOf(((Button)e.getSource()).getId())){
            		s = sessioni.get(i);
            	}
            }
            ConfigurazioneSessioneController controller = new ConfigurazioneSessioneController();
            controller.impostaDati(s);
            navigate("ConfigurazioneSessioneView", gestore);
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
    
    private void init() {
    	
        sessioniVbox.getChildren().clear();
        sessioniVbox.setPrefWidth(950);
        for (int i = 0; i < sessioni.size(); i++) {
        		
        		HBox sessioniHbox = new HBox();
        		Label sessioniLabel = new Label();
                sessioniLabel.setText(sessioni.get(i).getNome() + "   data : " + sessioni.get(i).getData() + "   modVoto: " + sessioni.get(i).getModVoto() + "  stato:" + sessioni.get(i).getStatoSessione());
                sessioniLabel.setFont(new Font(15));
                Region region1 = new Region();
            	sessioniHbox.setHgrow(region1, Priority.ALWAYS);
                sessioniHbox.getChildren().add(sessioniLabel);
                sessioniHbox.getChildren().add(region1);
                
                if (sessioni.get(i).getStatoSessione() == StatoSessione.CHIUSA) {
                	Button bottoneAvvia = new Button("Avvia");
                	Button bottoneModifica = new Button("Modifica");
                	Button bottoneElimina = new Button("Elimina");
                	bottoneAvvia.setId(sessioni.get(i).getId() + "");
                	bottoneModifica.setId(sessioni.get(i).getId() + "");
                	bottoneElimina.setId(sessioni.get(i).getId()+ "");
                	bottoneModifica.setOnAction(modificaSessione);
                	sessioniHbox.getChildren().add(bottoneAvvia);
                	sessioniHbox.getChildren().add(bottoneModifica);
                	sessioniHbox.getChildren().add(bottoneElimina);
                	
                } else if (sessioni.get(i).getStatoSessione() == StatoSessione.IN_CORSO) {
                	Button bottoneAperta = new Button("Gestisci");
                	bottoneAperta.setId(sessioni.get(i).getId() + "");
                	sessioniHbox.getChildren().add(bottoneAperta);
                } else if (sessioni.get(i).getStatoSessione() == StatoSessione.CONCLUSA) {
                	Button bottoneScrutinio = new Button("Avvia Scrutinio");
                	bottoneScrutinio.setId(sessioni.get(i).getId() + "");
                	sessioniHbox.getChildren().add(bottoneScrutinio);
                } else {
                	Button bottoneRisultati = new Button("Visualizza Risultati");
                	bottoneRisultati.setId(sessioni.get(i).getId() + "");
                	sessioniHbox.getChildren().add(bottoneRisultati);
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
         init();
    }
  }


