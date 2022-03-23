package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;

public interface CandidatoDAO {
	public List<Candidato> getAllCandidato();
	public Candidato getCandidatoById(int id);
	public void updateCandidato(Candidato c);
	public void deleteCandidato(Candidato c);
	public void addCandidato(Candidato c);
	public void addCandidato(int id, String nome, String cognome, Partito partito);
}
