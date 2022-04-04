package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotazioniReferendumDAO {
	public List<OpzioneReferendum> getAllVotazioniReferendumBySessione(SessioneDiVoto sessione);
	public void addVotazioniReferendumBySessione(SessioneDiVoto sessione, OpzioneReferendum opzione);
}
