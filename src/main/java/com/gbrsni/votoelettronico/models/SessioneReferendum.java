package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.List;

import com.gbrsni.votoelettronico.data_access.VotazioniReferendumDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniReferendumDAOImpl;

public class SessioneReferendum extends SessioneDiVoto{
	
	public SessioneReferendum(int id, String nome, String descrizione, LocalDate data, ModVittoria modVittoria,
			StatoSessione statoSessione, int nvoti) {
		super(id, nome, descrizione, data, modVittoria, statoSessione, nvoti);
	}
	
	public ModVoto getModVoto() {
		return ModVoto.REFERENDUM;
	}
	
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || obj.getClass()!= this.getClass())
			return false;
		SessioneReferendum s = (SessioneReferendum) obj;
		return (s.getId() == this.getId());
	}
}
