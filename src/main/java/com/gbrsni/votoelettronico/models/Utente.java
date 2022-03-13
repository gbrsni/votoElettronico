package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public abstract class Utente {

	private String username;
	private String nome;
	private String cognome; 
//	private Calendar dataNascita;
//	private String luogoNascita;
	private String codiceFiscale;

	public Utente(String username, String nome, String cognome) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		
		this.username = username;
		this.nome = nome;
		this.nome = cognome;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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


//	public Calendar getDataNascita() {
//		return dataNascita;
//	}
//
//
//	public void setDataNascita(Calendar dataNascita) {
//		this.dataNascita = dataNascita;
//	}


//	public String getLuogoNascita() {
//		return luogoNascita;
//	}
//
//
//	public void setLuogoNascita(String luogoNascita) {
//		this.luogoNascita = luogoNascita;
//	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
		
	
}
