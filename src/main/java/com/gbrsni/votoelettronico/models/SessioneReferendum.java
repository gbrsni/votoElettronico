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
		if (!haVotato(elettore)  && this.getStatoSessione() == StatoSessione.IN_CORSO) {
			VotazioniReferendumDAO votazioniReferendumDAO = new VotazioniReferendumDAOImpl();
			votazioniReferendumDAO.addVotazioniReferendumBySessione(this, opzione);
		}
	}
	
	public ModVoto getModVoto() {
		return ModVoto.REFERENDUM;
	}

	@Override
	public void votaCandidato(Candidato candidato) {
		throw new IllegalArgumentException("Impossibile votare un candidato in un referendum");
	}

	@Override
	public void votaPartito(Partito partito) {
		throw new IllegalArgumentException("Impossibile votare un partito in un referendum");
	}

	@Override
	public void votaCandidati(List<Candidato> candidati) {
		throw new IllegalArgumentException("Impossibile votare dei candidati in un referendum");
		
	}

	@Override
	public void votaPartiti(List<Partito> partiti) {
		throw new IllegalArgumentException("Impossibile votare dei partiti in un referendum");
	}
	
}
