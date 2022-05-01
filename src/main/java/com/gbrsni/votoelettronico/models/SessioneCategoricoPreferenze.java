package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;

public class SessioneCategoricoPreferenze extends SessioneDiVoto{
	
	public SessioneCategoricoPreferenze(int id, String nome, String descrizione, LocalDate data, ModVittoria modVittoria,
			StatoSessione statoSessione, int nvoti) {
		super(id, nome, descrizione, data, modVittoria, statoSessione, nvoti);
	}
	
	public ModVoto getModVoto() {
		return ModVoto.CATEGORICO_CON_PREFERENZE;
	}
	
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || obj.getClass()!= this.getClass())
			return false;
		SessioneCategoricoPreferenze s = (SessioneCategoricoPreferenze) obj;
		return (s.getId() == this.getId());
	}
}
