package votoElettronico;

/**Classe astratta Utente*/
public abstract class Utente {

	private String codiceFiscale;
	private String nome;
	private String cognome; 
	private String email;
	

	public Utente(String codiceFiscale, String nome, String cognome, String email) {
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.nome = cognome;
		this.email = email;
	}
	
	
	public String getcodiceFiscale() {
		return this.codiceFiscale;
	}
	
	public String getnome() {
		return this.nome;
	}
	
	public String getcognome() {
		return this.cognome;
	}
	
	public String email() {
		return this.email;
	}
}
