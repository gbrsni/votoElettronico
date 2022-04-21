package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;

public class Referendum extends SessioneDiVoto{

	public Referendum(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria,
			StatoSessione statoSessione, int nvoti) {
		super(id, nome, descrizione, data, modVoto, modVittoria, statoSessione, nvoti);
	}
	
	
}
