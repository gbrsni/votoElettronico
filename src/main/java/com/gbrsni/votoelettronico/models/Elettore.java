package com.gbrsni.votoelettronico.models;

import java.util.Calendar;

public class Elettore extends Utente{
	
	private String tesseraElettorale;

	public Elettore(String username, String nome, String cognome, Calendar dataNascita, String luogoNascita,
			String codiceFiscale, String tesseraElettorale) {
		super(username, nome, cognome, dataNascita, luogoNascita, codiceFiscale);
		this.tesseraElettorale = tesseraElettorale;
	}

	public String getTesseraElettorale() {
		return tesseraElettorale;
	}

	public void setTesseraElettorale(String tesseraElettorale) {
		this.tesseraElettorale = tesseraElettorale;
	}
	
	
}
