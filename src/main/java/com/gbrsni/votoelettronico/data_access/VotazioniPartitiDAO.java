package com.gbrsni.votoelettronico.data_access;

import java.util.Map;

import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotazioniPartitiDAO {
	public Map<Partito, Integer> getVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto);
	public void addVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto, Partito partito, int valore);
}
