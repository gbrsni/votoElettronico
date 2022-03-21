package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Elettore extends Utente{
	
	private String tesseraElettorale;
	private String codiceFiscale;
	
	public Elettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale) {
		super(username, nome, cognome, codiceFiscale);
		
		Objects.requireNonNull(tesseraElettorale);
  
		this.tesseraElettorale = tesseraElettorale;
		this.codiceFiscale = codiceFiscale;
	}

	public String getTesseraElettorale() {
		return tesseraElettorale;
	}

	public void setTesseraElettorale(String tesseraElettorale) {
		this.tesseraElettorale = tesseraElettorale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
}
