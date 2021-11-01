package votoElettronico;

/**Classe astratta Utente*/
public abstract class Utente {
	//variabili di istanza
	private String codiceFiscale;
	private String nome; 
	private String cognome; 
	private int Eta; 
	private String comune;
	private String provincia;
	
	//costruttore
	
	
	//metodi di istanza
	public Utente(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
