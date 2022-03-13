package com.gbrsni.votoelettronico.models;

import java.util.Calendar;

public class Gestore extends Utente {

	public Gestore(String username, String nome, String cognome, Calendar dataNascita, String luogoNascita,
			String codiceFiscale) {
		super(username, nome, cognome, dataNascita, luogoNascita, codiceFiscale);
	}
}
