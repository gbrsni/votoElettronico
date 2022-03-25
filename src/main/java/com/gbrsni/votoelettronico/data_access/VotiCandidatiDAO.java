package com.gbrsni.votoelettronico.data_access;

import java.util.List;
import java.util.Map;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiCandidatiDAO {
	
	public Map<Candidato, Integer> getVotiCandidatiBySessione(SessioneDiVoto sessioneDiVoto);
	public void addVotiCandidatiBySessione(SessioneDiVoto sessione, Candidato candidato);
}