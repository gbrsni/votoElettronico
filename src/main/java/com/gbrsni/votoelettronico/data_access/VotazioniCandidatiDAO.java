package com.gbrsni.votoelettronico.data_access;


import java.util.Map;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotazioniCandidatiDAO {
	public List<Pair<Candidato, Integer>> getVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto);
	public void setVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto, Candidato candidato, int voti);
}
