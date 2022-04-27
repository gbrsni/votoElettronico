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
	
	public void votaReferendum(Elettore elettore, OpzioneReferendum opzione) {
		if (!haVotato(elettore)) {
			VotazioniReferendumDAO votazioniReferendumDAO = new VotazioniReferendumDAOImpl();
			votazioniReferendumDAO.addVotazioniReferendumBySessione(this, opzione);
		}
	}
	
	public ModVoto getModVoto() {
		return ModVoto.REFERENDUM;
	}
	
}
