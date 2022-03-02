package com.gbrsni.votoelettronico;

public class Elettore extends Utente{
	
	private String tesseraElettorale;
	private String codiceFiscale;

	public Elettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale) {
		super(username, nome, cognome);
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
