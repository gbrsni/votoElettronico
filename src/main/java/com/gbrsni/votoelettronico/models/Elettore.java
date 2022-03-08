package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Elettore extends Utente{
	
	private String tesseraElettorale;
	private String codiceFiscale;

	public Elettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale) {
		super(username, nome, cognome);
		
		Objects.requireNonNull(tesseraElettorale);
		Objects.requireNonNull(codiceFiscale);
		
		this.tesseraElettorale = tesseraElettorale;
		this.codiceFiscale = codiceFiscale;
	}

	public String getTesseraElettorale(){
		return this.tesseraElettorale;
	}

	public String getCodiceFiscale(){
		return this.codiceFiscale;
	}
}
