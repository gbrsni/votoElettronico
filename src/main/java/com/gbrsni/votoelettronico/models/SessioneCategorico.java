package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotazioniPartitiDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAO;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;

public class SessioneCategorico extends SessioneDiVoto{
	
	public SessioneCategorico(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria,
			StatoSessione statoSessione, int nvoti) {
		super(id, nome, descrizione, data, modVoto, modVittoria, statoSessione, nvoti);
	}
	
	
	public Map<Candidato, Integer> getVotazioniCandidati() {
		VotazioniCandidatiDAO votazioniCandidatiDAO = new VotazioniCandidatiDAOImpl();
		return votazioniCandidatiDAO.getVotazioniCandidatiBySessione(this);
	}
	
	
	public void setVotazioniCandidato(Candidato candidato, int voti) {
		Objects.requireNonNull(candidato);
		
		VotazioniCandidatiDAO votazioniCandidatiDAO = new VotazioniCandidatiDAOImpl();
		votazioniCandidatiDAO.setVotazioniCandidatiBySessione(this, candidato, voti);
	}
	
	public Map<Partito, Integer> getVotazioniPartiti() {
		VotazioniPartitiDAO votazioniPartitiDAO = new VotazioniPartitiDAOImpl();
		return votazioniPartitiDAO.getVotazioniPartitiBySessione(this);
	}
	
	public void setVotazioniPartito(Partito partito, int voti) {
		Objects.requireNonNull(partito);
		
		VotazioniPartitiDAO votazioniPartitiDAO = new VotazioniPartitiDAOImpl();
		votazioniPartitiDAO.setVotazioniPartitiBySessione(this, partito, voti);
	}
	
	public boolean elettoreHaPartecipato(Elettore elettore) {
		Objects.requireNonNull(elettore);

		VotiEspressiDAO votiEspressiDAO = new VotiEspressiDAOImpl();
		return votiEspressiDAO.existsVotoEspresso(this, elettore);
	}
	
	public Map<Candidato, Integer> getVotiCandidati() {
		VotiCandidatiDAOImpl votiCandidatiDAO = new VotiCandidatiDAOImpl();
		return votiCandidatiDAO.getVotiCandidatiBySessione(this);
	}
	
	public void setVotiCandidati(Candidato candidato) {
		VotiCandidatiDAOImpl votiCandidatiDAO = new VotiCandidatiDAOImpl();
		votiCandidatiDAO.addVotiCandidatiBySessione(this, candidato);
	}
	
	public Map<Partito,Integer> getVotiPartiti() {
		VotiPartitiDAOImpl votiPartitiDAO = new VotiPartitiDAOImpl();
		return votiPartitiDAO.getVotiPartitiBySessione(this);
	}
	
	public void setVotiPartiti(Partito partito) {
		VotiPartitiDAOImpl votiPartitiDAO = new VotiPartitiDAOImpl();
		votiPartitiDAO.addVotiPartitiBySessione(this, partito);
	}
	
	public Integer getVotiAstenuti() {
		VotiAstenutiDAOImpl votiAstenutiDAO = new VotiAstenutiDAOImpl();
		return votiAstenutiDAO.getVotiAstenutiBySessione(this);
	}
	
	public void setVotiAstenuti() {
		VotiAstenutiDAOImpl votiAstenutiDAO = new VotiAstenutiDAOImpl();
		votiAstenutiDAO.addVotiAstenutiBySessione(this);
	}
	
	public void updateVotiCandidati(Candidato candidato, Integer nvoti) {}
	public void updateVotiPartiti(Partito partito, Integer nvoti) {}
	public void updateVotiAstenuti(Integer nvoti) {}
}
