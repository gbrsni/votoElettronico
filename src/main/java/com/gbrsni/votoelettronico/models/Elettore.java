package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Elettore extends Utente{
	
	private String tesseraElettorale;

	public Elettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale) {
		super(username, nome, cognome, codiceFiscale);
		
		Objects.requireNonNull(tesseraElettorale);
  
		this.tesseraElettorale = tesseraElettorale;
	}

	public String getTesseraElettorale() {
		return tesseraElettorale;
	}

	public void setTesseraElettorale(String tesseraElettorale) {
		this.tesseraElettorale = tesseraElettorale;
	}
	
	
}
