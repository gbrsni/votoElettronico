package com.gbrsni.votoelettronico.models;

public class Gestore extends Utente {

	public Gestore(String username, String nome, String cognome, String codiceFiscale) {
		super(username, nome, cognome, codiceFiscale);
	}
	

	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || obj.getClass()!= this.getClass())
			return false;
		Gestore g = (Gestore) obj;
		return (g.getUsername() == this.getUsername());
	}
}
