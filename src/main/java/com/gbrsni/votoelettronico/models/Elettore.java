package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Elettore extends Utente{
	
	private String tesseraElettorale;
	
	public Elettore(String username, String nome, String cognome,  String codiceFiscale, String tesseraElettorale) {
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
	

	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || obj.getClass()!= this.getClass())
			return false;
		Elettore e = (Elettore) obj;
		return (e.getUsername().equals(this.getUsername()));
	}
	
}
