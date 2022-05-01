package com.gbrsni.votoelettronico.data_access;


import java.util.List;
import java.util.Map;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

import javafx.util.Pair;

public interface VotazioniCandidatiDAO {
	public List<Pair<Candidato, Integer>> getVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto);
	public void addVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto, Candidato candidato, int valore);
}
