package com.gbrsni.votoelettronico;

/**Classe Concreta Elettore */
public class Elettore extends Utente{
		
	private String tesseraElettorale;

	public Elettore(String codiceFiscale, String nome, String cognome, String email, String tesseraElettorale){
		super(codiceFiscale, nome, cognome, email);
		this.tesseraElettorale = tesseraElettorale;
	}

	public String getTesseraElettorale(){
		return this.tesseraElettorale;
	}

}
