package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public abstract class Utente {

	private static String username;
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

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Utente.username = username;
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

	
	@Override
	public String toString() {
		return this.nome + " " + this.cognome;
	}
}
