package com.gbrsni.votoelettronico;

public abstract class Utente {

	private String username;
	private String nome;
	private String cognome; 
	

	public Utente(String username, String nome, String cognome) {
		this.username = username;
		this.nome = nome;
		this.nome = cognome;
	}
	
	
	public String getUsername() {
		return this.username;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
}
