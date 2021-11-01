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
	
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public String getEmail() {
		return this.email;
	}
}
