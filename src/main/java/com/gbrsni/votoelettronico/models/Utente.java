package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public abstract class Utente {

	private String username;
	private String nome;
	private String cognome; 
	private String codiceFiscale;

	public Utente(String username, String nome, String cognome,String codiceFiscale) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		Objects.requireNonNull(codiceFiscale);
		
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
	}

	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getUsername() {
		return username;
	}


	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public int hashCode() {
		return username.hashCode();
	}
	
	@Override
	public String toString() {
		return this.nome + " " + this.cognome;
	}
}
