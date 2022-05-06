package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;

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
	
	@Override
	public boolean condizioneVoto(int nVotiTotali, int nVotoScelta) {
		switch(this.getModVittoria()) {
		case REFERENDUM_SENZA_QUORUM:
			return true;
		case REFERENDUM_CON_QUORUM:
			if(nVotoScelta > (nVotiTotali/2)) {
				return true;
			} else {
				return false;
			}
		default: throw new IllegalArgumentException();
		}
	}
}
