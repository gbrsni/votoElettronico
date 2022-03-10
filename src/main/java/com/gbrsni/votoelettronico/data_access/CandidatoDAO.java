package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;

public interface CandidatoDAO {
	public List<Candidato> getAllCandidato();
	public void updateCandidato(Candidato e);
	public void deleteCandidato(Candidato e);
	public void addCandidato(Candidato e);
	public void addCandidato(int id, String nome, String cognome, Partito partito);
}
