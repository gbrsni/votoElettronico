package com.gbrsni.votoelettronico;

/**classe concreta dello scrutatore*/
public class Scrutatore extends Utente{
	
	private int idScrutatore;

	public Scrutatore (String codiceFiscale, String nome, String cognome, String email, int idScrutatore){
		super(codiceFiscale, nome, cognome, email);
		this.idScrutatore = idScrutatore;
	}

	public int getIdScrutatore(){
		return this.idScrutatore;
	}
	
}	
